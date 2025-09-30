export const Profile = { 
    props: ['user', 'jwt'],
    data() {
        return {
        firstName: this.user.firstName || '',
        lastName: this.user.lastName || '',
        email: this.user.email || '',
        username: this.user.username || '',
        password: ''
        };
    },

    methods: {
        async delete_profile(){
            if (!confirm("Are you sure you want to delete your account? This cannot be undone.")) {
                return;
            }
            try {
                const response = await fetch("/api/users/me/delete",{
                    method: 'DELETE',
                    headers: {				
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${this.jwt}`
                    }
                });

                if(!response.ok){
                    const error = await response.text();
                    throw new Error(error || 'Failed to delete profile');
                }

                alert("Your account has been deleted.");
                sessionStorage.clear();
                window.location.href = '/'; // redirect to login page
            } catch (error) {
                console.error("Error deleting profile:", error);
                alert("Failed to delete account. Please try again.");
            }
        }
    },

    template: 
    `<div class="profile">
        <section class="profile-info">
            <h2>Your Profile</h2>
            <p><strong>First name: </strong>{{ user.firstName }}</p>
            <p><strong>Last name: </strong>{{ user.lastName }}</p>
            <p><strong>Username:</strong> {{ user.username }}</p>
            <p><strong>Email: </strong>{{ user.email }}</p>
        </section>

        <section class="actions">
            <button id="editBtn" @click="$emit('go_to_edit')">Edit Profile</button>
            <button id="deleteBtn" @click="delete_profile">Delete Profile</button>
        </section>
    </div>`};
