const { createApp } = Vue;

createApp({
    data() {
        return {
            firstName: '',
            lastName: '',
            email: '',
            username: '',
            password: '',
            message: ''
        };
    },
    methods: {
        async signup() {
            try {
                const response = await fetch("/api/auth/signup", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({
                        firstName: this.firstName,
                        lastName: this.lastName,
                        email: this.email,
                        username: this.username,
                        password: this.password
                    })
                });
                const data = await response.json(); // {token: jwt}
                sessionStorage.setItem("jwt", data.jwt); // Store token in local storage
                window.location.href = "/dashboard.html"; // Redirect
            } catch (err) {
                console.error(err);
                this.message = "Error during signup.";
            }
        }
    }
}).mount("#app");