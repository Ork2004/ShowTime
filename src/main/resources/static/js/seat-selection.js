import { API_BASE } from "./config.js";
import { requireRole } from "./auth.js";

document.addEventListener("DOMContentLoaded", async () => {
    try {
        await requireRole("USER");

        const params = new URLSearchParams(window.location.search);
        const showId = params.get("showId");
        const movieId = params.get("movieId");
        const container = document.getElementById("seatContainer");
        const bookBtn = document.getElementById("bookBtn");
        const token = localStorage.getItem("token");
        const userId = localStorage.getItem("userId");

        if (!showId || !movieId) {
            container.innerHTML = `<p class="text-center text-danger">Show ID or Movie ID not specified.</p>`;
            return;
        }

        const showRes = await fetch(`${API_BASE}/movies/${movieId}/shows/${showId}`, {
            headers: { "Authorization": `Bearer ${token}` }
        });

        if (!showRes.ok) throw new Error("Failed to load show info");
        const show = await showRes.json();

        const { hallId, theaterId } = show;
        if (!hallId || !theaterId) {
            container.innerHTML = `<p class="text-center text-danger">Invalid show data.</p>`;
            return;
        }

        const seatRes = await fetch(`${API_BASE}/theaters/${theaterId}/halls/${hallId}/seats`, {
            headers: { "Authorization": `Bearer ${token}` }
        });

        if (!seatRes.ok) throw new Error("Failed to load seats");
        const seats = await seatRes.json();

        const selectedSeats = new Set();
        renderSeats(seats);

        function renderSeats(seats) {
            container.innerHTML = `
        <div class="col-md-8 d-flex flex-wrap justify-content-center">
            ${seats.map(seat => {
                const isBooked = seat.isBooked || seat.booked || seat.occupied;
                return `
                    <div class="seat m-2 p-3 border rounded text-center
                        ${isBooked ? 'bg-secondary text-light' : 'bg-white'}"
                        style="width:60px; cursor:${isBooked ? 'not-allowed' : 'pointer'}"
                        data-id="${seat.id}">
                        ${seat.row ?? ''}${seat.number ?? seat.seatNumber ?? ''}
                    </div>
                `;
            }).join("")}
        </div>
    `;

            document.querySelectorAll(".seat").forEach(seatEl => {
                if (seatEl.classList.contains("bg-secondary")) return;
                seatEl.addEventListener("click", () => {
                    const id = seatEl.dataset.id;
                    if (selectedSeats.has(id)) {
                        selectedSeats.delete(id);
                        seatEl.classList.remove("bg-success-subtle", "border-success");
                        seatEl.classList.add("bg-white");
                    } else {
                        selectedSeats.add(id);
                        seatEl.classList.remove("bg-white");
                        seatEl.classList.add("bg-success-subtle", "border-success");
                    }
                    bookBtn.disabled = selectedSeats.size === 0;
                });
            });
        }

        bookBtn.addEventListener("click", () => {
            if (selectedSeats.size === 0) {
                alert("Please select at least one seat.");
                return;
            }

            alert(`(Demo) Selected seats: ${Array.from(selectedSeats).join(", ")}\nBooking feature coming soon.`);
        });

    } catch (err) {
        console.error(err);
        const container = document.getElementById("seatContainer");
        container.innerHTML = `<p class="text-center text-danger">Failed to load seat selection.</p>`;
    }
});
