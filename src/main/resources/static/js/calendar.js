export const Calendar = { 
    props: ['show', 'events', 'jwt'],
    data() {
        return {
            eventName: 'test',
            eventStart: '',
            eventEnd: '', 
        };
    },

    mounted(){
        this.$nextTick(async () => {
            console.log("Render calendar from calendar");
            await this.$emit('render_calendar');
        });
    },

    methods: {
        async addEvent(){
            const response = await fetch('/api/events/me/post', {
                method: 'POST',
                headers:{
                    'Content-Type': 'application/json',
				    'Authorization': `Bearer ${this.jwt}`
                },
                body: JSON.stringify({
                    eventName: this.eventName,
                    eventStart: this.eventStart,
                    eventEnd: this.eventEnd
                })
            });
            if (!response.ok) throw new Error("Failed to add new event");
            const newEvent = await response.json();
            // Add event to array
            this.$emit('add_event', newEvent);
            // Close popup and reload calendar
            this.$emit('toggle_show');
        }

    },
    template: 
    `<div class="calendar">   
        <section class="weekly-scheduler">
            <h2>Weekly Events</h2>
            <button id="addEventBtn" @click="$emit('toggle_show')">Add Event</button>
            <div v-if="show" class="overlay">
                <div class="eventForm">
                    <h2>Add an event to the calendar</h2>
                    <form @submit.prevent="addEvent">
                        <!-- Event Name -->
                        <label for="event-name">Event Name:</label>
                        <input type="text" id="event-name" name="event-name" v-model:="eventName" placeholder="Enter event name" required>

                        <!-- Start Date -->
                        <label for="start-date">Start Date:</label>
                        <input type="datetime-local" id="start-date" name="start-date" v-model="eventStart" required>

                        <!-- End Date -->
                        <label for="end-date">End Date:</label>
                        <input type="datetime-local" id="end-date" name="end-date" v-model="eventEnd" required>

                        <!-- Buttons -->
                        <div class="buttons">
                            <button type="submit" id="confirmBtn">Confirm</button>
                            <button type="button" id="cancelBtn" @click="$emit('toggle_show')">Cancel</button>
                        </div>
                    </form>
                </div>
            </div>
        </section>
        <div class="schedulex" ref="calendarContainer"></div>
    </div>
    `
};