const { createApp } = Vue;

createApp({
    data() {
        return {
            username: '',
            password: '',
            firstName: '',
            lastName: '',
            email: '',
            result: ''
        }
    },
    methods: {
        async login() {
            try {
                const response = await fetch('/api/auth/login', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ username: this.username, password: this.password, firstName: this.firstName,
                        lastName: this.lastName, email: this.email})
                     });

                const success = await response.json(); // Long
                if (success != -1) {
                    // Store username in local storage
                    localStorage.setItem("username", this.username);
                    window.location.href = '/dashboard.html';
                } else {
                    this.result = "Login failed!";
                }
            } catch (err) {
                console.error(err);
            }
        },
        async signup() {
            window.location.href = '/signup.html'
        }
    }
}).mount('#app');