import { createApp } from "https://unpkg.com/vue@3/dist/vue.esm-browser.js";

createApp({
    data() {
        return {
            user: {},
            weekDays: ['Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday'],
            hours: Array.from({ length: 13 }, (_, i) => i+8),
            events: [] // to be fetched from backend
        };
    },
    async mounted() {
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

        }
    }
}).mount('#app');