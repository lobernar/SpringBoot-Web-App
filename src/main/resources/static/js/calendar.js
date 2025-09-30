export const Calendar = { 
    props: ['weekDays', 'show', 'hours', 'events', 'calendar'],
    mounted(){
        if(this.calendar){
            this.calendar.render(this.$refs.calendarContainer);
        }
    },
    methods: {
        eventsForDayHour(day, hour) {
            return this.events.filter(ev => ev.day === day && ev.hour === hour);
        },

        async addEvent(){
            alert("Adding event");
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
                        <input type="text" id="event-name" name="event-name" placeholder="Enter event name" required>

                        <!-- Start Date -->
                        <label for="start-date">Start Date:</label>
                        <input type="datetime-local" id="start-date" name="start-date" required>

                        <!-- End Date -->
                        <label for="end-date">End Date:</label>
                        <input type="datetime-local" id="end-date" name="end-date" required>

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