document.addEventListener("DOMContentLoaded", async () => {
    const params = new URLSearchParams(window.location.search);
    const movieId = params.get("id");
    const container = document.getElementById("movieContainer");
    const token = localStorage.getItem("token");

    if (!movieId) {
        container.innerHTML = `<p class="text-center text-danger">Movie ID not specified.</p>`;
        return;
    }

    try {
        const res = await fetch(`${API_BASE}/movies/${movieId}`, {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        });

        if (!res.ok) throw new Error("Failed to fetch movie");

        const movie = await res.json();

        container.innerHTML = `
            <div class="col-md-8 mx-auto">
                <div class="card shadow-sm">
                    <div class="card-body">
                        <h2 class="card-title">${movie.title}</h2>
                        <p class="text-muted mb-2"><strong>Duration:</strong> ${movie.duration}</p>
                        <p class="text-muted mb-2"><strong>Release:</strong> ${movie.releaseDate}</p>
                        <p class="text-muted mb-3"><strong>Rating:</strong> ⭐ ${movie.rating ?? "N/A"}</p>
                        <p class="mb-3"><strong>Genre ID:</strong> ${movie.genreId}</p>
                        <button id="bookBtn" class="btn btn-success w-100">Book Ticket</button>
                    </div>
                </div>
            </div>
        `;

        document.getElementById("bookBtn").addEventListener("click", () => {
            if (!token) {
                alert("Please log in to book tickets");
                window.location.href = "login.html";
                return;
            }
            window.location.href = `show-selection.html?movieId=${movieId}`;
        });

    } catch (err) {
        console.error(err);
        container.innerHTML = `<p class="text-center text-danger">Failed to load movie details.</p>`;
    }
});