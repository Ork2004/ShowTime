document.addEventListener("DOMContentLoaded", () => {
    const navButtons = document.getElementById("navButtons");
    const token = localStorage.getItem("token");

    if (token) {
        navButtons.innerHTML = `
            <button id="logoutBtn" class="btn btn-danger btn-sm">Logout</button>
        `;

        document.getElementById("logoutBtn").addEventListener("click", () => {
            localStorage.removeItem("token");
            window.location.href = "login.html";
        });
    } else {
        navButtons.innerHTML = `
            <a href="login.html" class="btn btn-warning btn-sm">Login</a>
        `;
    }
});
