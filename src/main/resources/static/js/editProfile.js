import {createApp} from "https://unpkg.com/vue@3/dist/vue.esm-browser.js";

createApp({
    data() {
        return {
            user: {},
            firstName: '',
            lastName: '',
            email: '',
            username: '',
            password: '',
        };
    },

    async mounted(){
        const username = localStorage.getItem("username");
        try {
            // fetch user information
            const response = await fetch(`/api/users/me/${username}`)
            if (!response.ok) throw new Error("Failed to fetch user data");
            this.user = await response.json();
            // pre-fill form fields
            this.firstName = this.user.firstName;
            this.lastName = this.user.lastName;
            this.email = this.user.email;
            this.username = this.user.username;
            this.password = this.user.password;
        } catch (err) {
            alert(err);
        }
    },

    methods: {
        async edit() {
            try {
                const response = await fetch(`/api/auth/edit/${this.user.username}`, {
                    method: "PUT",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({
                        firstName: this.firstName,
                        lastName: this.lastName,
                        email: this.email,
                        username: this.username,
                        password: this.password
                    })
                });
                this.user = await response.json();
                if (response.ok) {
                    localStorage.setItem("username", this.username);
                    window.location.href = "/dashboard.html";
                } else {console.error("Error updating user");}
            } catch (err) {console.error(err);}
        }
    }
}).mount("#app");