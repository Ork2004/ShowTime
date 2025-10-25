import { logout, requireLogin } from './auth.js';

document.addEventListener("DOMContentLoaded", async () => {
    const extraLinks = document.getElementById("extraLinks");
    const navButtons = document.getElementById("navButtons");
    const token = localStorage.getItem("token");

    if (!token) {
        navButtons.innerHTML = `
            <a href="login.html" class="btn btn-warning btn-sm">Login</a>
        `;
        return;
    }

    try {
        const user = await requireLogin();

        if (extraLinks) {
            if (user.role === "USER") {
                extraLinks.innerHTML = `
                <a href="my-tickets.html" class="btn btn-outline-light btn-sm me-2">My Tickets</a>
            `;
            } else if (user.role === "ADMIN") {
                extraLinks.innerHTML = `
                <a href="admin-dashboard.html" class="btn btn-outline-light btn-sm me-2">Admin Panel</a>
            `;
            }
        }

        navButtons.innerHTML = `
            <button id="logoutBtn" class="btn btn-danger btn-sm">Logout</button>
        `;
        document.getElementById("logoutBtn").addEventListener("click", logout);

    } catch (err) {
        console.warn("Auth check failed:", err);
        localStorage.removeItem("token");
        navButtons.innerHTML = `
            <a href="login.html" class="btn btn-warning btn-sm">Login</a>
        `;
    }
});
