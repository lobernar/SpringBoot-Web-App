import { createApp } from "https://unpkg.com/vue@3/dist/vue.esm-browser.js";
import { checkJWT } from "./utils.js";

createApp({
    data(){
        return{
            user: {},
            jwt: sessionStorage.getItem('jwt')
        }
    },

    async mounted() {
        checkJWT(this.jwt);
        // Fetch user information
        
        try {
            const request = await fetch(`/api/users/me/`, {
                method: 'GET',
                headers: { 'Authorization': `Bearer ${this.jwt}`},
            });
            if (!request.ok) throw new Error("Failed to fetch user data");
            this.user = await request.json();
        } catch (err) {console.error('Error fetching user:', err);}
    },
    methods: {
        logout(){
            sessionStorage.clear();
            alert("Logging out");
            window.location.href = "/";
        },
        edit() {
            window.location.href = "/editProfile.html";
        },
    }

}).mount("#app")
