<template>
  <div class="login-container">
    <h2>Login</h2>
    <form @submit.prevent="login">
      <input type="text" v-model="username" placeholder="Username" required />
      <input type="password" v-model="password" placeholder="Password" required />
      <div class="buttons">
        <button type="submit">Login</button>
        <button type="button" @click="signup">Sign Up</button>
      </div>
    </form>
    <div id="result">{{ result }}</div>
  </div>
</template>

<script>
export default {
  name: "LoginPage",
  data() {
    return {
      username: "",
      password: "",
      result: ""
    };
  },
  methods: {
    async login() {
      try {
        const response = await fetch("/api/auth/login", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            username: this.username,
            password: this.password
          })
        });

        if (!response.ok) {
          this.result = "Login Failed";
          return;
        }

        const data = await response.json(); // { jwt: x }
        console.log(data.jwt);
        localStorage.setItem("jwt", data.jwt); // Store token
        window.location.href = "/dashboard.html"; // Redirect
      } catch (err) {
        console.error("Could not login: " + err);
      }
    },
    signup() {
      window.location.href = "/signup.html";
    }
  }
};
</script>

<style scoped>
.login-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
}

.login-container input {
  display: block;
  width: 100%;
  margin: 10px 0;
  padding: 8px;
}

.buttons {
  display: flex;
  gap: 10px;
}
</style>
