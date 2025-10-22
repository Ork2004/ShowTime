import { API_BASE } from './config.js';

document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("loginForm");
    const registerForm = document.getElementById("registerForm");

    if (loginForm) loginForm.addEventListener("submit", handleLogin);
    if (registerForm) registerForm.addEventListener("submit", handleRegister);
});

async function handleLogin(e) {
    e.preventDefault();
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    try {
        const res = await fetch(`${API_BASE}/auth/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });
        if (!res.ok) throw new Error("Login failed");
        const data = await res.json();
        localStorage.setItem("token", data.token);
        localStorage.setItem("userId", data.userId);
        alert("Login successful!");
        window.location.href = "index.html";
    } catch {
        alert("Invalid credentials");
    }
}

async function handleRegister(e) {
    e.preventDefault();
    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    try {
        const res = await fetch(`${API_BASE}/auth/register`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name, email, password })
        });
        if (!res.ok) throw new Error("Registration failed");
        alert("Registration successful! Please log in.");
        window.location.href = "login.html";
    } catch {
        alert("Failed to register");
    }
}

export function authFetch(url, options = {}) {
    const token = localStorage.getItem("token");
    const headers = {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json",
        ...options.headers
    };
    return fetch(url, { ...options, headers });
}

export async function requireLogin() {
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "login.html";
        throw new Error("Not logged in");
    }
    const res = await fetch(`${API_BASE}/auth/validate`, {
        headers: { "Authorization": `Bearer ${token}` }
    });
    if (!res.ok) {
        logout();
        throw new Error("Token invalid or expired");
    }
    return res.json(); // { id, email, role }
}

export async function requireRole(role) {
    const user = await requireLogin();
    if (user.role !== role.toUpperCase()) {
        alert("Access denied");
        throw new Error("Insufficient role");
    }
    return user;
}

export function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("userId");
    window.location.href = "login.html";
}
