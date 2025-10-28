import { authFetch, requireRole } from "./auth.js";
import { API_BASE } from "./config.js";

document.addEventListener("DOMContentLoaded", async () => {
    await requireRole("ADMIN");

    const urlParams = new URLSearchParams(window.location.search);
    const theaterId = urlParams.get("theaterId");
    const hallId = urlParams.get("hallId");

    if (!theaterId || !hallId) {
        alert("Missing theaterId or hallId parameter");
        window.location.href = "admin-theaters.html";
        return;
    }

    const container = document.getElementById("seatsContainer");
    const modal = new bootstrap.Modal(document.getElementById("seatModal"));
    const form = document.getElementById("seatForm");
    const addBtn = document.getElementById("addSeatBtn");

    async function loadSeats() {
        const res = await authFetch(`${API_BASE}/theaters/${theaterId}/halls/${hallId}/seats`);
        const seats = await res.json();

        if (seats.length === 0) {
            container.innerHTML = `<p class="text-center text-muted">No seats found. Add one!</p>`;
            return;
        }

        container.innerHTML = seats.map(s => `
            <div class="col-md-4 col-lg-3">
                <div class="card shadow-sm h-100">
                    <div class="card-body">
                        <h6>Row ${s.rowNumber}, Seat ${s.seatNumber}</h6>
                       
                        <div class="d-flex justify-content-between">
                            <button class="btn btn-primary btn-sm edit-btn" data-id="${s.id}">✏️ Edit</button>
                            <button class="btn btn-danger btn-sm delete-btn" data-id="${s.id}">🗑️ Delete</button>
                        </div>
                    </div>
                </div>
            </div>
        `).join("");

        document.querySelectorAll(".edit-btn").forEach(btn =>
            btn.addEventListener("click", async () => {
                const id = btn.dataset.id;
                const res = await authFetch(`${API_BASE}/theaters/${theaterId}/halls/${hallId}/seats/${id}`);
                const seat = await res.json();
                document.getElementById("seatModalLabel").textContent = "Edit Seat";
                document.getElementById("seatId").value = seat.id;
                document.getElementById("rowNumber").value = seat.rowNumber;
                document.getElementById("seatNumber").value = seat.seatNumber;
                modal.show();
            })
        );

        document.querySelectorAll(".delete-btn").forEach(btn =>
            btn.addEventListener("click", async () => {
                if (!confirm("Delete this seat?")) return;
                await authFetch(`${API_BASE}/theaters/${theaterId}/halls/${hallId}/seats/${btn.dataset.id}`, {
                    method: "DELETE"
                });
                loadSeats();
            })
        );
    }

    addBtn.addEventListener("click", () => {
        form.reset();
        document.getElementById("seatId").value = "";
        document.getElementById("seatModalLabel").textContent = "Add Seat";
        modal.show();
    });

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const seat = {
            rowNumber: parseInt(form.rowNumber.value),
            seatNumber: parseInt(form.seatNumber.value)
        };

        const id = form.seatId.value;
        const method = id ? "PUT" : "POST";
        const url = id
            ? `${API_BASE}/theaters/${theaterId}/halls/${hallId}/seats/${id}`
            : `${API_BASE}/theaters/${theaterId}/halls/${hallId}/seats`;

        await authFetch(url, {
            method,
            body: JSON.stringify(seat)
        });

        modal.hide();
        loadSeats();
    });

    await loadSeats();
});
