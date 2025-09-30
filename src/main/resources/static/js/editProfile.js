export const EditProfile = {
  props: ['user', 'jwt'],  // pass the user and jwt from root app
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
	async editProfile() {
		try {
		// PUT request to backend
		const request = await fetch('/api/users/me/edit', {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json',
				'Authorization': `Bearer ${this.jwt}`
			},
			body: JSON.stringify({
				firstName: this.firstName,
				lastName: this.lastName,
				email: this.email,
				username: this.username,
				password: this.password
			})
		});
		if (!request.ok) throw new Error("Failed to fetch user data");
		const updatedUser = await request.json();
		// Update user in root script
		this.$emit('update_user', updatedUser);
		// Redirect to calendar after successful update
		this.$router.push('/');
		
		} catch (err) {
		console.error('Failed to update user:', err);
		}
	}
  },
  template: `
    <div>
      <h1>Edit Information</h1>
      <form @submit.prevent="editProfile">
        <input type="text" v-model="firstName" placeholder="First Name" required>
        <input type="text" v-model="lastName" placeholder="Last Name" required>
        <input type="email" v-model="email" placeholder="Email" required>
        <input type="text" v-model="username" placeholder="Username" required>
        <input type="password" v-model="password" placeholder="Password" required>
        <button type="submit">Submit</button>
      </form>
    </div>
  `
};
