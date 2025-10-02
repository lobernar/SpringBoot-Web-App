import { checkJWT } from "./utils.js";
import { EditProfile } from "./editProfile.js";
import { Profile } from "./profile.js";
import { Calendar } from "./calendar.js";
import { Home } from "./home.js";

const {createApp} = Vue;
const {createRouter, createWebHashHistory} = VueRouter;
const { createCalendar, viewWeek, viewDay, viewMonthGrid } = window.SXCalendar;
const { createDragAndDropPlugin } = window.SXDragAndDrop;

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
const plugins = [
    createDragAndDropPlugin(),
];

const calendarX = createCalendar({
    views: [viewWeek, viewDay, viewMonthGrid],
    events: [],
    plugins,
});


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
            const request = await fetch(`/api/users/me/`, {
                method: 'GET',
                headers: { 'Authorization': `Bearer ${this.jwt}`},
            });
            if (!request.ok) throw new Error("Failed to fetch user data");
            this.user = await request.json();
        } catch (err) {console.error('Error fetching user:', err);}
        console.log("Username: " + this.user.username);
        // Fetch events
        this.fetchEvents();
    },

    methods: {
        logout(){
            sessionStorage.clear();
            alert("Logging out");
            window.location.href = "/";
        },

        goToEdit() {
            this.$router.push("edit");
        },

        updateUser(newUser){
            this.user=newUser;
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
            this.events = await response.json();
            console.log("Fetched Events: " + this.events);
        },

        toggleShow(){
            this.show = !this.show;
        },

        addEvent(newEvent){
            alert("Dashboard: adding event");
            this.events.push(newEvent);
            this.fetchEvents();
            this.calendar.events=this.events;
        },
    }
});
app.use(router);
app.mount('#app');