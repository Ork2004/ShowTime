import { authFetch, requireRole } from "./auth.js";
import { API_BASE } from "./config.js";

document.addEventListener("DOMContentLoaded", async () => {
    await requireRole("ADMIN");

    const params = new URLSearchParams(window.location.search);
    const movieId = params.get("movieId");

    if (!movieId) {
        alert("Movie ID not specified!");
        window.location.href = "admin-movies.html";
        return;
    }

    const container = document.getElementById("showsContainer");
    const modal = new bootstrap.Modal(document.getElementById("showModal"));
    const form = document.getElementById("showForm");
    const addBtn = document.getElementById("addShowBtn");
    const theaterSelect = document.getElementById("theaterId");
    const hallSelect = document.getElementById("hallId");

    async function loadTheaters() {
        const res = await authFetch(`${API_BASE}/theaters`);
        const theaters = await res.json();
        theaterSelect.innerHTML = theaters.map(t => `<option value="${t.id}">${t.name}</option>`).join("");
        if (theaters.length > 0) await loadHalls(theaters[0].id);
    }

    async function loadHalls(theaterId) {
        const res = await authFetch(`${API_BASE}/theaters/${theaterId}/halls`);
        const halls = await res.json();
        hallSelect.innerHTML = halls.map(h => `<option value="${h.id}">${h.name}</option>`).join("");
    }

    theaterSelect.addEventListener("change", e => loadHalls(e.target.value));

    async function loadShows() {
        const res = await authFetch(`${API_BASE}/movies/${movieId}/shows`);
        const shows = await res.json();

        container.innerHTML = shows.map(s => `
            <div class="col-md-6 col-lg-4">
                <div class="card shadow-sm h-100">
                    <div class="card-body">
                        <h5 class="card-title">${s.theaterName} — ${s.hallName}</h5>
                        <p class="card-text text-muted">
                            🕓 ${new Date(s.showTime).toLocaleString()}<br>
                            💰 ${s.price} ₸
                        </p>
                        <div class="d-flex justify-content-between">
                            <button class="btn btn-primary btn-sm edit-btn" data-id="${s.id}">✏️ Edit</button>
                            <button class="btn btn-danger btn-sm delete-btn" data-id="${s.id}">🗑️ Delete</button>
                        </div>
                    </div>
                </div>
            </div>
        `).join("");

        document.querySelectorAll(".edit-btn").forEach(btn => btn.addEventListener("click", async () => {
            const id = btn.dataset.id;
            const res = await authFetch(`${API_BASE}/movies/${movieId}/shows/${id}`);
            const show = await res.json();

            document.getElementById("showModalLabel").textContent = "Edit Show";
            document.getElementById("showId").value = show.id;
            document.getElementById("showTime").value = show.showTime.slice(0, 16);
            document.getElementById("price").value = show.price;
            theaterSelect.value = show.theaterId;
            await loadHalls(show.theaterId);
            hallSelect.value = show.hallId;

            modal.show();
        }));

        document.querySelectorAll(".delete-btn").forEach(btn => btn.addEventListener("click", async () => {
            if (!confirm("Delete this show?")) return;
            await authFetch(`${API_BASE}/movies/${movieId}/shows/${btn.dataset.id}`, { method: "DELETE" });
            loadShows();
        }));
    }

    addBtn.addEventListener("click", () => {
        form.reset();
        document.getElementById("showId").value = "";
        document.getElementById("showModalLabel").textContent = "Add Show";
        modal.show();
    });

    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        const show = {
            showTime: form.showTime.value,
            price: parseFloat(form.price.value),
            hallId: parseInt(form.hallId.value)
        };
        const id = form.showId.value;
        const method = id ? "PUT" : "POST";
        const url = id
            ? `${API_BASE}/movies/${movieId}/shows/${id}`
            : `${API_BASE}/movies/${movieId}/shows`;

        await authFetch(url, {
            method,
            body: JSON.stringify(show)
        });

        modal.hide();
        loadShows();
    });

    await loadTheaters();
    await loadShows();
});
