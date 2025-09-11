import {createApp} from "https://unpkg.com/vue@3/dist/vue.esm-browser.js";

createApp({
    data(){
        return{
            user: {}
        }
    },

    async mounted() {
        const username = localStorage.getItem('username');
        // Fetch user information
        try {
            const request = await fetch(`/api/users/me/${username}`);
            if (!request.ok) throw new Error("Failed to fetch user data");
            this.user = await request.json();
        } catch (err) {console.error('Error fetching user:', err);}
    },
    methods: {
        edit() {
            window.location.href = "/editProfile.html";
        },

        logout() {
            localStorage.clear();
            window.location.href = '/index.html';
        }
    }

}).mount("#app")
