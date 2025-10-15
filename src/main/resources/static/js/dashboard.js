import { checkJWT } from "./utils.js";
import { EditProfile } from "./editProfile.js";
import { Profile } from "./profile.js";
import { Calendar } from "./calendar.js";
import { Home } from "./home.js";

const {createApp} = Vue;
const {createRouter, createWebHashHistory} = VueRouter;
const { createCalendar, viewWeek, viewDay, viewMonthGrid } = window.SXCalendar;
const { createDragAndDropPlugin } = window.SXDragAndDrop;
const {createEventsServicePlugin } = window.SXEventsService;

/*
 *------------------- VueRouter Setup -------------------
 */
const routes = [
    { path: "/", component: Home, props: true},
    { path: "/calendar", component: Calendar, props: true },
    { path: "/profile", component: Profile, props: true },
    { path: "/edit", component: EditProfile, props: true}
];

const router = createRouter({
    history: createWebHashHistory(),
    routes,
});

/*
 *------------------- Schedule-X Setup -------------------
 */

const eventsServicePlugin = createEventsServicePlugin();
const dragAndDropPlugin = createDragAndDropPlugin();

const plugins = [
    eventsServicePlugin,
    dragAndDropPlugin,
];

const config = {
    views: [viewWeek, viewDay, viewMonthGrid],
    plugins,
    dayBoundaries: {
        start: '06:00',
        end: '18:00',
    },
    weekOptions: {gridHeight: 450}
};

let calendarX = createCalendar(config);


/*
 *------------------- CreateApp Setup -------------------
 */
const app = createApp({
    data() {
        return {
            user: {},
            show: false,
            jwt: sessionStorage.getItem('jwt'),
            events: [], // to be fetched from backend
            calendar: calendarX,
        };
    },

    async mounted() {
        checkJWT(this.jwt);
        // Fetch user information
        try {
            await this.fetchUser();
        } catch (error) {
            console.error('Error fetching user:', error);
        }
        // Fetch events
        try {
            await this.fetchEvents();
        } catch (error) {
            console.error('Error fetching events:', error);
        }
        await this.renderCalendar();
    },

    methods: {
        async fetchUser(){
            const request = await fetch(`/api/users/me/`, {
                method: 'GET',
                headers: { 'Authorization': `Bearer ${this.jwt}`},
            });
            if (!request.ok) throw new Error("Failed to fetch user data");
            this.user = await request.json();
        },

        async fetchEvents(){
            const response = await fetch("/api/events/me", {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
				    'Authorization': `Bearer ${this.jwt}`
                },
            });
            if(!response.ok) throw new Error("Unable to fetch user events");
            const rawEvents= await response.json();
            
            // Transform API events into Schedule-X format
            this.events = rawEvents.map(e => ({
                id: e.id, 
                title: e.name,
                start: Temporal.ZonedDateTime.from(e.startDate + "[Europe/Berlin]"),
                end: Temporal.ZonedDateTime.from(e.endDate + "[Europe/Berlin]")
            }));
        },

        async renderCalendar(){
            try {;
                // Render the calendar first
                await this.calendar.render(document.querySelector(".schedulex"));
                // Use calendar.eventsService to add events
                this.calendar.eventsService.set(this.events);
                // Access it via calendar.eventsService
            } catch (error) {
                console.error('Error rendering calendar:', error);
            }

        },

        logout(){
            sessionStorage.clear();
            alert("Logging out");
            window.location.href = "/";
        },

        goToEdit() {
            this.$router.push("edit");
        },

        updateUser(newJwt){
            try{
                this.jwt=newJwt; // Update JWT                
                this.fetchUser();
            } catch (error) {console.error('Error fetching user:', error);}
        },

        toggleShow(){
            this.show = !this.show;
        },

        async addEvent(newEvent){
            await this.fetchEvents();
            this.calendar.eventsService.set(this.events)
        },
    }
});
app.use(router);
app.mount('#app');