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

                const success = await response.json();
                if (success) {
                    localStorage.setItem("username", this.username);
                    this.message = "Signup successful! Redirecting...";
                    window.location.href = "/dashboard.html";
                } else {
                    this.message = "Username already taken!";
                }
            } catch (err) {
                console.error(err);
                this.message = "Error during signup.";
            }
        }
    }
}).mount("#app");