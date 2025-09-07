document.addEventListener("DOMContentLoaded", () =>{

    const form = document.getElementById("loginForm");

    form.addEventListener("submit", async(e) => {
        e.preventDefault();

        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;

        try {
            const response = await fetch("/api/auth/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({username, password})
            });

            const text = await response.text();
            document.getElementById("result").innerText = text
        } catch (error) {
            console.log("Error: " + error);
        }

    });
})