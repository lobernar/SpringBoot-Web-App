export const Home = {
    props: ["user"],
    template: `
    <div class="homeContainer">
      <h1>Welcome back {{ user.firstName }}!</h1>
      <p>
        Use your dashboard to manage your profile, 
        schedule events, and stay organized throughout the week. 
        Explore your calendar, add new events, and keep everything on track.
      </p>
    </div>
    `
}