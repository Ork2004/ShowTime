import { requireRole } from "./auth.js";

document.addEventListener("DOMContentLoaded", async () => {
    try {
        await requireRole("ADMIN");
    } catch (err) {
        console.error(err);
    }
});
