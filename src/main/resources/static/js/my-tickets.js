document.addEventListener("DOMContentLoaded", async () => {
    const token = localStorage.getItem("token");
    const userId = localStorage.getItem("userId");
    const container = document.getElementById("ticketsContainer");

    if (!token || !userId) {
        window.location.href = "login.html";
        return;
    }

    try {
        // Проверяем токен
        const validateRes = await fetch(`${API_BASE}/auth/validate`, {
            headers: { "Authorization": `Bearer ${token}` }
        });

        if (!validateRes.ok) throw new Error("Token invalid");
        const userData = await validateRes.json();

        if (userData.role !== "USER") {
            container.innerHTML = `<p class="text-center text-danger">Access denied. Only users can view tickets.</p>`;
            return;
        }

        // Загружаем билеты пользователя
        const res = await fetch(`${API_BASE}/users/${userId}/tickets`, {
            headers: { "Authorization": `Bearer ${token}` }
        });

        if (!res.ok) throw new Error("Failed to load tickets");
        const tickets = await res.json();

        if (tickets.length === 0) {
            container.innerHTML = `<p class="text-center text-muted">You have no tickets yet.</p>`;
            return;
        }

        container.innerHTML = tickets.map(ticket => {
            const dateTime = new Date(ticket.showTime);
            const formattedDate = dateTime.toLocaleDateString();
            const formattedTime = dateTime.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });

            return `
                <div class="col-md-6 col-lg-4 mb-3">
                    <div class="card shadow-sm h-100">
                        <div class="card-body text-center">
                            <h5 class="card-title">${ticket.movieTitle}</h5>
                            <p class="card-text text-muted">
                                <strong>Theater:</strong> ${ticket.theaterName}<br>
                                <strong>Hall:</strong> ${ticket.hallName}<br>
                                <strong>Seat:</strong> ${ticket.seatNumber}<br>
                                <strong>Date:</strong> ${formattedDate}<br>
                                <strong>Time:</strong> ${formattedTime}<br>
                                <strong>Price:</strong> ${ticket.price} ₸
                            </p>
                        </div>
                    </div>
                </div>
            `;
        }).join("");

    } catch (err) {
        console.error(err);
        container.innerHTML = `<p class="text-center text-danger">Failed to load your tickets.</p>`;
    }
});
