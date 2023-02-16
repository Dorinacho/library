<template>
	<nav id="navbar">
		<router-link to="/home" class="link">Home</router-link>
		<router-link v-if="loggedIn" to="/books" class="link">Books</router-link>
		<router-link v-if="loggedIn" to="/loans" class="link">Loans</router-link>
		<router-link v-if="loggedIn" to="/users" class="link">Users</router-link>
		<router-link v-if="!loggedIn" to="/login" class="link">Login</router-link>
		<v-btn v-if="loggedIn" class="link" @click="logout()"
			>Logout</v-btn
		>
	</nav>
</template>

<script>
// import AuthService from "../services/auth/auth.service";

export default {
	methods: {
		logout() {
			this.$store.dispatch("auth/logout").then(() => {
				this.$router.push("/home");
			});
		},
	},
	computed: {
		loggedIn() {
			return this.$store.state.auth.status.loggedIn;
		},
	},
};
</script>
<style>
#navbar {
	height: 60px;
	width: 100%;
	display: flex;
	background-color: bisque;
	align-items: center;
	justify-content: flex-end;
}
.link {
	margin: 0px 10px;
	color: black !important;
	text-decoration: none;
}

.link:active {
	color: cornflowerblue !important;
}
</style>
