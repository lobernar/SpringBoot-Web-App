export const Calendar = { 
    props: ['weekDays', 'show', 'hours', 'events'],
    methods: {
        eventsForDayHour(day, hour) {
            return this.events.filter(ev => ev.day === day && ev.hour === hour);
        }
    },
    template: 
    `<div class="calendar">   
        <section class="weekly-scheduler">
            <h2>Weekly Events</h2>
            <button id="addEventBtn" @click="show = !show">Add Event</button>
                <div v-if="show" class="overlay">
                    <div class="eventForm">
                        <h2>Add an event to the calendar</h2>
                        <form @submit.prevent="addEvent">
                            <!-- Event Name -->
                            <label for="event-name">Event Name:</label>
                            <input type="text" id="event-name" name="event-name" placeholder="Enter event name" required>

                            <!-- Start Date -->
                            <label for="start-date">Start Date:</label>
                            <input type="date" id="start-date" name="start-date" required>

                            <!-- End Date -->
                            <label for="end-date">End Date:</label>
                            <input type="date" id="end-date" name="end-date" required>

                            <!-- Buttons -->
                            <div class="buttons">
                                <button type="submit" id="confirmBtn">Confirm</button>
                                <button type="button" id="cancelBtn" @click="show = !show">Cancel</button>
                            </div>
                        </form>
                    </div>
                </div>
            <table>
                <thead>
                    <tr>
                        <th>Time</th>
                        <th>Monday</th>
                        <th>Tuesday</th>
                        <th>Wednesday</th>
                        <th>Thursday</th>
                        <th>Friday</th>
                        <th>Saturday</th>
                        <th>Sunday</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="hour in hours" :key="hour">
                        <td>{{ hour }}:00</td>
                        <td v-for="day in weekDays" :key="day">
                            <div v-for="event in eventsForDayHour(day, hour)" :key="event.id">
                                {{ event.title }}
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </section>
    </div>`
};