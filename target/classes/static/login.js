const { createApp } = Vue;

createApp({
    data() {
        return {
            username: '',
            password: '',
            result: ''
        }
    },
    methods: {
        async login() {
            try {
                const response = await fetch('/api/auth/login', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ username: this.username, password: this.password })
                });

                const success = await response.json(); // boolean
                if (success) {
                    window.location.href = '/home.html';
                } else {
                    this.result = "Login failed!";
                    alert("Login failed!");
                }
            } catch (err) {
                console.error(err);
                alert('Error: ' + err);
            }
        },
        async signup() {
            try {
                const response = await fetch('/api/auth/signup', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ username: this.username, password: this.password })
                });

                const success = await response.json(); // boolean
                if (success) {
                    window.location.href = '/home.html';
                } else {
                    this.result = "Signup failed!";
                    alert("Signup failed!");
                }
            } catch (err) {
                console.error(err);
                alert('Error: ' + err);
            }
        }
    }
}).mount('#app');