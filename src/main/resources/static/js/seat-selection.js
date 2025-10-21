document.addEventListener("DOMContentLoaded", async () => {
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

    try {
        const showRes = await fetch(`${API_BASE}/movies/${movieId}/shows/${showId}`, {
            headers: {"Authorization": `Bearer ${token}`}
        });

        if (!showRes.ok) {
            if (showRes.status === 404) {
                container.innerHTML = `<p class="text-center text-danger">Selected show not found.</p>`;
                return;
            }
            throw new Error("Failed to load show info");
        }

        const show = await showRes.json();
        const hallId = show.hallId;
        const theaterId = show.theaterId ?? show.theaterId; // если есть theaterId in DTO

        const seatRes = await fetch(`${API_BASE}/theaters/${theaterId}/halls/${hallId}/seats`, {
            headers: {"Authorization": `Bearer ${token}`}
        });

        if (!seatRes.ok) {
            if (seatRes.status === 404) {
                container.innerHTML = `<p class="text-center text-danger">Hall or seats not found.</p>`;
                return;
            }
            throw new Error("Failed to load seats");
        }

        const seats = await seatRes.json();

        // render seats (пример)
        renderSeats(seats);

        const selectedSeats = new Set();

        function renderSeats(seats) {
            container.innerHTML = `
        <div class="col-md-8 d-flex flex-wrap justify-content-center">
          ${seats.map(seat => {
                const isBooked = seat.isBooked || seat.booked || seat.occupied; // адаптация по DTO
                return `
              <div class="seat m-2 p-3 border rounded text-center ${isBooked ? 'bg-secondary text-light' : 'bg-white'}" 
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
                        seatEl.classList.remove("bg-success", "text-white");
                    } else {
                        selectedSeats.add(id);
                        seatEl.classList.add("bg-success", "text-white");
                    }
                    bookBtn.disabled = selectedSeats.size === 0;
                });
            });
        }

        bookBtn.addEventListener("click", async () => {
            if (!userId) {
                alert("Please log in to book tickets");
                window.location.href = "login.html";
                return;
            }

            if (selectedSeats.size === 0) {
                alert("Please select at least one seat");
                return;
            }

            alert(`(Demo) Selected seats: ${Array.from(selectedSeats).join(", ")}\nBooking is disabled for now.`);
        });

    } catch (err) {
        console.error(err);
        container.innerHTML = `<p class="text-center text-danger">Failed to load seats.</p>`;
    }
});
