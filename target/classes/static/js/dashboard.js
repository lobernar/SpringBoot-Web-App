import { createApp } from 'https://unpkg.com/vue@3/dist/vue.esm-browser.js';

createApp({
    data() {
        return {
            user: {}
        }
    },
    async created() {
        try {
            const username = localStorage.getItem('username');
            if (!username) throw new Error("No logged-in user found");

            const response = await fetch(`/api/users/me/${username}`);
            if (!response.ok) throw new Error("Failed to fetch user data");

            this.user = await response.json();
        } catch (err) {
            console.error('Error fetching user:', err);
            alert('Could not load user data');
        }
    },
    methods: {
        logout() {
            localStorage.clear();
            window.location.href = '/index.html'; // redirect to login
        }
    }
}).mount('#app');