import { authFetch, requireRole } from "./auth.js";
import { API_BASE } from "./config.js";

document.addEventListener("DOMContentLoaded", async () => {
    await requireRole("ADMIN");

    const container = document.getElementById("moviesContainer");
    const modal = new bootstrap.Modal(document.getElementById("movieModal"));
    const form = document.getElementById("movieForm");
    const addBtn = document.getElementById("addMovieBtn");
    const genreSelect = document.getElementById("genreId");

    async function loadGenres() {
        const res = await authFetch(`${API_BASE}/genres`);
        const genres = await res.json();
        genreSelect.innerHTML = genres.map(g => `<option value="${g.id}">${g.name}</option>`).join("");
    }

    async function loadMovies() {
        const res = await authFetch(`${API_BASE}/movies`);
        const movies = await res.json();
        container.innerHTML = movies.map(m => `
            <div class="col-md-6 col-lg-4">
                <div class="card shadow-sm h-100">
                    <div class="card-body">
                        <h5 class="card-title">${m.title}</h5>
                        <p class="card-text text-muted">
                            Genre: ${m.genreName ?? "—"}<br>
                            Duration: ${m.duration}<br>
                            Release: ${m.releaseDate}<br>
                            Rating: ${m.rating ?? "—"}
                        </p>
                        <div class="d-flex justify-content-between">
                            <button class="btn btn-primary btn-sm edit-btn" data-id="${m.id}">✏️ Edit</button>
                            <button class="btn btn-danger btn-sm delete-btn" data-id="${m.id}">🗑️ Delete</button>
                        </div>
                    </div>
                </div>
            </div>
        `).join("");

        document.querySelectorAll(".edit-btn").forEach(btn => btn.addEventListener("click", async () => {
            const id = btn.dataset.id;
            const res = await authFetch(`${API_BASE}/movies/${id}`);
            const movie = await res.json();
            document.getElementById("movieModalLabel").textContent = "Edit Movie";
            document.getElementById("movieId").value = movie.id;
            document.getElementById("title").value = movie.title;
            document.getElementById("duration").value = movie.duration;
            document.getElementById("releaseDate").value = movie.releaseDate;
            document.getElementById("rating").value = movie.rating ?? "";
            document.getElementById("genreId").value = movie.genreId;
            modal.show();
        }));

        document.querySelectorAll(".delete-btn").forEach(btn => btn.addEventListener("click", async () => {
            if (!confirm("Delete this movie?")) return;
            await authFetch(`${API_BASE}/movies/${btn.dataset.id}`, { method: "DELETE" });
            loadMovies();
        }));
    }

    addBtn.addEventListener("click", () => {
        form.reset();
        document.getElementById("movieId").value = "";
        document.getElementById("movieModalLabel").textContent = "Add Movie";
        modal.show();
    });

    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        const movie = {
            title: form.title.value,
            duration: form.duration.value,
            releaseDate: form.releaseDate.value,
            rating: parseFloat(form.rating.value) || 0,
            genreId: parseInt(form.genreId.value)
        };
        const id = form.movieId.value;
        const method = id ? "PUT" : "POST";
        const url = id ? `${API_BASE}/movies/${id}` : `${API_BASE}/movies`;
        await authFetch(url, {
            method,
            body: JSON.stringify(movie)
        });
        modal.hide();
        loadMovies();
    });

    await loadGenres();
    await loadMovies();
});
