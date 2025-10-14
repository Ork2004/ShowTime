const API_BASE = "http://localhost:8080/auth";

document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("loginForm");
    const registerForm = document.getElementById("registerForm");

    if (loginForm) {
        loginForm.addEventListener("submit", async (e) => {
            e.preventDefault();
            const email = document.getElementById("email").value;
            const password = document.getElementById("password").value;

            try {
                const res = await fetch(`${API_BASE}/login`, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ email, password })
                });

                if (!res.ok) throw new Error("Login failed");

                const data = await res.json();
                localStorage.setItem("token", data.token);
                alert("Login successful!");
                window.location.href = "index.html";
            } catch (err) {
                alert("Invalid credentials");
            }
        });
    }

    if (registerForm) {
        registerForm.addEventListener("submit", async (e) => {
            e.preventDefault();
            const name = document.getElementById("name").value;
            const email = document.getElementById("email").value;
            const password = document.getElementById("password").value;

            try {
                const res = await fetch(`${API_BASE}/register`, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ name, email, password })
                });

                if (!res.ok) throw new Error("Registration failed");

                alert("Registration successful! Please log in.");
                window.location.href = "login.html";
            } catch (err) {
                alert("Failed to register");
            }
        });
    }
});

function authFetch(url, options = {}) {
    const token = localStorage.getItem("token");
    const headers = {
        ...options.headers,
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
    };
    return fetch(url, { ...options, headers});
}