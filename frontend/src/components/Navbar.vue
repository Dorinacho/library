<template>
	<nav id="navbar">
		<router-link to="/home" class="link">Home</router-link>
		<router-link v-if="isAdmin" to="/books" class="link">Books</router-link>
		<router-link v-if="!isAdmin && loggedIn" to="/user/books" class="link"
			>Books</router-link
		>
		<router-link v-if="isAdmin" to="/loans" class="link">Loans</router-link>
		<router-link v-if="!isAdmin && loggedIn" to="/user/loans" class="link"
			>My Books</router-link
		>
		<router-link v-if="isAdmin" to="/users" class="link">Users</router-link>
		<router-link v-if="isAdmin" to="/authors" class="link">Authors</router-link>
		<router-link v-if="!loggedIn" to="/signup" class="link"
			>Sign up</router-link
		>
		<router-link v-if="!loggedIn" to="/login" class="link">Login</router-link>
		<v-btn v-if="loggedIn" v-ripple class="link" id="btn" @click="logout()"
			>Logout</v-btn
		>
	</nav>
</template>

<script>
	// import AuthService from "../services/auth/auth.service";

	export default {
		data() {
			return {};
		},
		methods: {
			logout() {
				this.$store.dispatch('auth/logout').then(() => {
					if (this.$route.name !== 'home') {
						this.$router.push('/home');
					}
				});
			},
		},
		computed: {
			loggedIn() {
				return this.$store.state.auth.status.loggedIn;
			},
			isAdmin() {
				// console.log(this.$store.state.auth.user.admin);
				// console.log(this.$store.state.auth.status.loggedIn)
				if (this.$store.state.auth.user == null) return false;
				else return this.$store.state.auth.user.admin;
			},
		},
	};
</script>
<style lang="scss">
	@import '../scss/_variables.scss';

	#navbar {
		height: 60px;
		width: 100%;
		display: flex;
		background-color: $pain-gray;
		align-items: center;
		justify-content: flex-end;
	}
	.link {
		margin: 0px 10px;
		color: $ghost_white !important;
		text-decoration: none;
	}

	#btn {
		color: $pain-gray !important;
	}
	// .link:active {
	// 	color: cornflowerblue !important;
	// }
</style>
