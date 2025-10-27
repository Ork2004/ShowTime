import { authFetch, requireRole } from "./auth.js";
import { API_BASE } from "./config.js";

document.addEventListener("DOMContentLoaded", async () => {
    await requireRole("ADMIN");

    const container = document.getElementById("theatersContainer");
    const modal = new bootstrap.Modal(document.getElementById("theaterModal"));
    const form = document.getElementById("theaterForm");
    const addBtn = document.getElementById("addTheaterBtn");

    async function loadTheaters() {
        const res = await authFetch(`${API_BASE}/theaters`);
        const theaters = await res.json();

        container.innerHTML = theaters.map(t => `
            <div class="col-md-6 col-lg-4">
                <div class="card shadow-sm h-100">
                    <div class="card-body">
                        <h5 class="card-title">${t.name}</h5>
                        <p class="card-text text-muted">📍 ${t.location}</p>
                        <div class="d-flex justify-content-between">
                            <button class="btn btn-primary btn-sm edit-btn" data-id="${t.id}">✏️ Edit</button>
                            <button class="btn btn-danger btn-sm delete-btn" data-id="${t.id}">🗑️ Delete</button>
                            <button class="btn btn-secondary btn-sm halls-btn" data-id="${t.id}">🏟️ Halls</button>
                        </div>
                    </div>
                </div>
            </div>
        `).join("");

        document.querySelectorAll(".edit-btn").forEach(btn => btn.addEventListener("click", async () => {
            const id = btn.dataset.id;
            const res = await authFetch(`${API_BASE}/theaters/${id}`);
            const theater = await res.json();
            document.getElementById("theaterModalLabel").textContent = "Edit Theater";
            document.getElementById("theaterId").value = theater.id;
            document.getElementById("name").value = theater.name;
            document.getElementById("location").value = theater.location;
            modal.show();
        }));

        document.querySelectorAll(".delete-btn").forEach(btn => btn.addEventListener("click", async () => {
            if (!confirm("Delete this theater?")) return;
            await authFetch(`${API_BASE}/theaters/${btn.dataset.id}`, { method: "DELETE" });
            loadTheaters();
        }));

        document.querySelectorAll(".halls-btn").forEach(btn =>
            btn.addEventListener("click", () => {
                window.location.href = `admin-halls.html?theaterId=${btn.dataset.id}`;
            })
        );
    }

    addBtn.addEventListener("click", () => {
        form.reset();
        document.getElementById("theaterId").value = "";
        document.getElementById("theaterModalLabel").textContent = "Add Theater";
        modal.show();
    });

    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        const theater = {
            name: form.name.value,
            location: form.location.value
        };
        const id = form.theaterId.value;
        const method = id ? "PUT" : "POST";
        const url = id ? `${API_BASE}/theaters/${id}` : `${API_BASE}/theaters`;

        await authFetch(url, {
            method,
            body: JSON.stringify(theater)
        });

        modal.hide();
        loadTheaters();
    });

    await loadTheaters();
});
