import { checkJWT } from "./utils.js";
import { EditProfile } from "./editProfile.js";
import { Profile } from "./profile.js";
import { Calendar } from "./calendar.js";
import { Home } from "./home.js";

const {createApp} = Vue;
const {createRouter, createWebHashHistory} = VueRouter;

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

const app = createApp({
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

        eventsForDayHour(day, hour) {

        },

        addEvent() {
            alert("Added event");
        }
    }
});
app.use(router);
app.mount('#app');