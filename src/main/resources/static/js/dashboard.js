import { createApp } from "https://unpkg.com/vue@3/dist/vue.esm-browser.js";
import { checkJWT } from "./utils.js";

createApp({
    data() {
        return {
            user: {},
            show: false,
            jwt: sessionStorage.getItem('jwt'),
            weekDays: ['Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday'],
            hours: Array.from({ length: 13 }, (_, i) => i+8),
            events: [] // to be fetched from backend
        };
    },
    async mounted() {
        checkJWT(this.jwt);
        // Fetch events
    },
    methods: {
        logout(){
            sessionStorage.clear();
            alert("Logging out");
            window.location.href = "/";
        },

        edit() {
            alert("Edit profile clicked");
        },

        eventsForDayHour(day, hour) {

        },

        addEvent() {
            alert("Added event");
        }
    }
}).mount('#app');