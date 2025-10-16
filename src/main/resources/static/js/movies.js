document.addEventListener("DOMContentLoaded", async () => {
    const container = document.getElementById("moviesContainer");
    const token = localStorage.getItem("token");

    if (!token) {
        container.innerHTML = `<p class="text-center text-danger">Please log in to view movies.</p>`;
        return;
    }

    try {
        const res = await fetch(`${API_BASE}/movies`, {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        });

        if (!res.ok) throw new Error("Failed to fetch movies");

        const movies = await res.json();

        if (movies.length === 0) {
            container.innerHTML = `<p class="text-center text-muted">No movies available.</p>`;
            return;
        }

        container.innerHTML = movies.map(movie => `
            <div class="col-md-4 col-sm-6">
                <div class="card shadow-sm h-100">
                    <div class="card-body">
                        <h5 class="card-title">${movie.title}</h5>
                        <p class="card-text text-muted mb-1">
                            <strong>Duration:</strong> ${movie.duration}
                        </p>
                        <p class="card-text text-muted mb-1">
                            <strong>Release:</strong> ${movie.releaseDate}
                        </p>
                        <p class="card-text text-muted mb-3">
                            <strong>Rating:</strong> ⭐ ${movie.rating ?? "N/A"}
                        </p>
                        <a href="movie-details.html?id=${movie.id}" class="btn btn-primary w-100">View Details</a>
                    </div>
                </div>
            </div>
        `).join("");

    } catch (err) {
        console.error(err);
        container.innerHTML = `<p class="text-center text-danger">Failed to load movies.</p>`;
    }
});
