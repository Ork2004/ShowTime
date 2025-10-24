import { API_BASE } from "./config.js";
import { requireRole } from "./auth.js";

document.addEventListener("DOMContentLoaded", async () => {
    const params = new URLSearchParams(window.location.search);
    const movieId = params.get("movieId");
    const container = document.getElementById("showContainer");
    const theaterSelect = document.getElementById("theaterSelect");

    if (!movieId) {
        container.innerHTML = `<p class="text-center text-danger">Movie ID not specified.</p>`;
        return;
    }

    try {
        const user = await requireRole("USER");

        const res = await fetch(`${API_BASE}/movies/${movieId}/shows`, {
            headers: { "Authorization": `Bearer ${localStorage.getItem("token")}` }
        });

        if (!res.ok) throw new Error("Failed to load shows");
        const shows = await res.json();

        if (!shows.length) {
            container.innerHTML = `<p class="text-center text-muted">No shows available for this movie.</p>`;
            return;
        }

        const theaters = [...new Set(shows.map(s => s.theaterName))];
        theaters.forEach(name => {
            const option = document.createElement("option");
            option.value = name;
            option.textContent = name;
            theaterSelect.appendChild(option);
        });

        theaterSelect.addEventListener("change", () => {
            const selected = theaterSelect.value;
            const filtered = selected ? shows.filter(s => s.theaterName === selected) : shows;
            renderShows(filtered);
        });

        function renderShows(list) {
            container.innerHTML = list.map(show => {
                const dateTime = new Date(show.showTime);
                const formattedDate = dateTime.toLocaleDateString();
                const formattedTime = dateTime.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });

                return `
          <div class="col-md-6 col-lg-4 mb-3">
            <div class="card shadow-sm h-100">
              <div class="card-body text-center">
                <h5 class="card-title">${show.hallName}</h5>
                <p class="card-text text-muted">
                  <strong>Theater:</strong> ${show.theaterName}<br>
                  <strong>Date:</strong> ${formattedDate}<br>
                  <strong>Time:</strong> ${formattedTime}<br>
                  <strong>Price:</strong> ${show.price} ₸
                </p>
                <button class="btn btn-primary w-100" data-id="${show.id}">Select</button>
              </div>
            </div>
          </div>
        `;
            }).join("");

            container.querySelectorAll("button[data-id]").forEach(btn => {
                btn.addEventListener("click", () => handleSelect(btn.dataset.id));
            });
        }

        function handleSelect(showId) {
            window.location.href = `seat-selection.html?movieId=${movieId}&showId=${showId}`;
        }

        renderShows(shows);

    } catch (err) {
        console.error(err);
        container.innerHTML = `<p class="text-center text-danger">Access denied or failed to load shows.</p>`;
    }
});
