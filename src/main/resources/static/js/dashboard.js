import { createApp } from "https://unpkg.com/vue@3/dist/vue.esm-browser.js";

createApp({
    data() {
        return {
            user: {},
            jwt: localStorage.getItem('jwt'),
            weekDays: ['Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday'],
            hours: Array.from({ length: 13 }, (_, i) => i+8),
            events: [], // to be fetched from backend
            show: false
        };
    },
    async mounted() {
        console.log("JWT: " + this.jwt);
        if(this.jwt == null) {
            alert("You must log in first!");
            window.location.href = "/index.html";
        }
        // Fetch events
        // try {
        //     const request = await fetch(`/api/events/${username}`);
        //     if(!request.ok) throw new Error("Failed to fetch user events");
        //     this.events = await request.json();
        // } catch (err) {console.error('Error fetching events:', err);}
    },
    methods: {
        logout() {
            localStorage.clear();
            window.location.href = '/index.html'; // redirect to login
        },

        edit() {
            alert("Edit profile clicked");
        },

        eventsForDayHour(day, hour) {

        },

        addEvent(){
            console.log("Adding event");
            this.show = !this.show;
        }
    }
}).mount('#app');