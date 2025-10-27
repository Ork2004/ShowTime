import { authFetch, requireRole } from "./auth.js";
import { API_BASE } from "./config.js";

document.addEventListener("DOMContentLoaded", async () => {
    await requireRole("ADMIN");

    const urlParams = new URLSearchParams(window.location.search);
    const theaterId = urlParams.get("theaterId");

    if (!theaterId) {
        alert("Missing theaterId parameter");
        window.location.href = "admin-theaters.html";
        return;
    }

    const container = document.getElementById("hallsContainer");
    const modal = new bootstrap.Modal(document.getElementById("hallModal"));
    const form = document.getElementById("hallForm");
    const addBtn = document.getElementById("addHallBtn");

    async function loadHalls() {
        const res = await authFetch(`${API_BASE}/theaters/${theaterId}/halls`);
        const halls = await res.json();

        container.innerHTML = halls.map(h => `
            <div class="col-md-6 col-lg-4">
                <div class="card shadow-sm h-100">
                    <div class="card-body">
                        <h5 class="card-title">${h.name}</h5>
                        <div class="d-flex justify-content-between">
                            <button class="btn btn-primary btn-sm edit-btn" data-id="${h.id}">✏️ Edit</button>
                            <button class="btn btn-danger btn-sm delete-btn" data-id="${h.id}">🗑️ Delete</button>
                            <button class="btn btn-secondary btn-sm seats-btn" data-id="${h.id}">🎫 Seats</button>
                        </div>
                    </div>
                </div>
            </div>
        `).join("");

        document.querySelectorAll(".edit-btn").forEach(btn => btn.addEventListener("click", async () => {
            const id = btn.dataset.id;
            const res = await authFetch(`${API_BASE}/theaters/${theaterId}/halls/${id}`);
            const hall = await res.json();
            document.getElementById("hallModalLabel").textContent = "Edit Hall";
            document.getElementById("hallId").value = hall.id;
            document.getElementById("name").value = hall.name;
            modal.show();
        }));

        document.querySelectorAll(".delete-btn").forEach(btn => btn.addEventListener("click", async () => {
            if (!confirm("Delete this hall?")) return;
            await authFetch(`${API_BASE}/theaters/${theaterId}/halls/${btn.dataset.id}`, { method: "DELETE" });
            loadHalls();
        }));

        document.querySelectorAll(".seats-btn").forEach(btn =>
            btn.addEventListener("click", () => {
                window.location.href = `admin-seats.html?theaterId=${theaterId}&hallId=${btn.dataset.id}`;
            })
        );
    }

    addBtn.addEventListener("click", () => {
        form.reset();
        document.getElementById("hallId").value = "";
        document.getElementById("hallModalLabel").textContent = "Add Hall";
        modal.show();
    });

    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        const hall = { name: form.name.value };

        const id = form.hallId.value;
        const method = id ? "PUT" : "POST";
        const url = id
            ? `${API_BASE}/theaters/${theaterId}/halls/${id}`
            : `${API_BASE}/theaters/${theaterId}/halls`;

        await authFetch(url, {
            method,
            body: JSON.stringify(hall)
        });

        modal.hide();
        loadHalls();
    });

    await loadHalls();
});
