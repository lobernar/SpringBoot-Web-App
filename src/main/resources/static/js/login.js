const { createApp } = Vue;
const { createRouter, createWebHistory } = VueRouter;

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
                    body: JSON.stringify({
                        username: this.username,
                        password: this.password})
                     });
                
                if(!response.ok){
                    this.result = "Login Failed";
                    return;
                }
                const data = await response.json(); // {token: jwt}
                console.log(data.jwt);
                sessionStorage.setItem("jwt", data.jwt); // Store token in local storage
                window.location.href = "/dashboard.html"; // Redirect
            } catch (err) {
                console.error("Could not login " + err);
            }
        },
        async signup() {
            window.location.href = '/signup.html'
        }
    }
}).mount('#app');