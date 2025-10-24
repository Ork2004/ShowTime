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
    <div class="text-center mb-3">
        <span class="badge bg-white border text-dark me-2">⬜ Available</span>
        <span class="badge bg-success-subtle border-success text-success me-2">🟩 Selected</span>
        <span class="badge bg-danger-subtle border-danger text-danger me-2">🟥 Booked</span>
    </div>
    <div class="col-md-8 d-flex flex-wrap justify-content-center">
        ${seats.map(seat => {
                const isBooked = seat.booked;
                const seatColorClass = isBooked ? 'bg-danger-subtle text-danger border-danger' : 'bg-white';
                const cursorStyle = isBooked ? 'not-allowed' : 'pointer';
                return `
                <div class="seat m-2 p-3 border rounded text-center ${seatColorClass}"
                    style="width:60px; cursor:${cursorStyle}"
                    data-id="${seat.id}">
                    ${seat.row ?? ''}${seat.number ?? seat.seatNumber ?? ''}
                </div>
            `;
            }).join("")}
    </div>
    `;

            document.querySelectorAll(".seat").forEach(seatEl => {
                if (seatEl.classList.contains("bg-danger-subtle")) return;

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


        bookBtn.addEventListener("click", async () => {
            if (selectedSeats.size === 0) {
                alert("Please select at least one seat.");
                return;
            }

            try {
                bookBtn.disabled = true;
                bookBtn.textContent = "Booking...";

                const bookings = [];

                for (const seatId of selectedSeats) {
                    const res = await fetch(`${API_BASE}/users/${userId}/tickets`, {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                            "Authorization": `Bearer ${token}`
                        },
                        body: JSON.stringify({
                            showId: showId,
                            seatId: seatId
                        })
                    });

                    if (!res.ok) {
                        const errText = await res.text();
                        throw new Error(`Booking failed: ${errText}`);
                    }

                    const ticket = await res.json();
                    bookings.push(ticket);
                }

                alert(`Booking successful!\nTickets: ${bookings.map(t => t.id).join(", ")}`);
                window.location.href = "my-tickets.html";

            } catch (err) {
                console.error(err);
                alert("Booking failed. Some seats may already be booked.");
            } finally {
                bookBtn.disabled = false;
                bookBtn.textContent = "Book Selected Seats";
            }
        });

    } catch (err) {
        console.error(err);
        const container = document.getElementById("seatContainer");
        container.innerHTML = `<p class="text-center text-danger">Failed to load seat selection.</p>`;
    }
});
