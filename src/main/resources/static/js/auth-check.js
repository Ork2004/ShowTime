document.addEventListener("DOMContentLoaded", async () => {
    const token = localStorage.getItem("token");

    if (!token) {
        redirectToLogin();
        return;
    }

    try {
        const res = await fetch(`${API_BASE}/auth/validate`, {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`
            }
        });

        if (!res.ok) throw new Error("Token invalid");

        const data = await res.json();
        console.log("Token valid:", data);

        if (data.role === "ADMIN") {
            document.body.classList.add("admin");
        }
    } catch (err) {
        console.warn("Invalid or expired token");
        logoutAndRedirect();
    }
});

function redirectToLogin() {
    window.location.href = "login.html";
}

function logoutAndRedirect() {
    localStorage.removeItem("token");
    redirectToLogin();
}
