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

let calendarX = createCalendar({
    views: [viewWeek, viewDay, viewMonthGrid],
    events: [
        {
          // end: "2025-10-04 10:10"
          // id: "1"
          // start: "2025-10-03 10:10"
          // title: "test3"
          id: "2",
          title: "Event 1",
          start: "2025-10-03 09:00", 
          end: "2025-10-03 10:00"
        },
    ],
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
            await this.fetchUser();
        } catch (error) {
            console.error('Error fetching user:', error);
        }
        console.log("Dashboard Username: " + this.user.username);
        // Fetch events
        try {
            await this.fetchEvents();  
        } catch (error) {
            console.error('Error fetching events:', error);
        }        
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
                id: e.id.toString(),              // must be a string
                title: e.name,                    // Schedule-X uses "title"
                start: e.startDate.replace('T', ' ').slice(0,16), // 'YYYY-MM-DD HH:mm'
                end: e.endDate.replace('T', ' ').slice(0,16)
            }));
        },

        async renderCalendar(){
            try {
                console.log("render calendar called");
                if(this.calendar){
                    const container = document.querySelector(".schedulex");
                    if (!container) {
                        console.warn("Calendar container not ready yet");
                        return;
                    }
                    this.calendar = createCalendar({
                        views: [viewWeek, viewDay, viewMonthGrid],
                        events: this.events,
                        plugins,
                    });
                    this.calendar.render(container);
                }    
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
                console.log("New JWT: ", this.jwt);
                
                this.fetchUser();
                console.log("Username: " + this.user.username);
            } catch (error) {console.error('Error fetching user:', error);}
        },

        toggleShow(){
            this.show = !this.show;
        },

        async addEvent(newEvent){
            this.events.push(newEvent);
            await this.fetchEvents();
            await this.calendar.render(document.querySelector(".schedulex"));
        },
    }
});
app.use(router);
app.mount('#app');