<template>
	<div>
		<Navbar />
		<div>
			<center>This is the HomePage</center>
		</div>
		<div class="container">
			<header class="jumbotron">
				<h3>
					<strong>{{ currentUser.username }}</strong> Profile
				</h3>
			</header>
			<p>
				<strong>Token:</strong>
				{{ currentUser.token}} ...
				<!-- {{
					currentUser.accessToken.substr(currentUser.accessToken.length - 20)
				}} -->
			</p>
			<p>
				<strong>Id:</strong>
				{{ currentUser.id }}
			</p>
			<p>
				<strong>Email:</strong>
				{{ currentUser.email }}
			</p>
			<strong>Authorities:</strong>
			<ul>
				<li v-for="(role, index) in currentUser.roles" :key="index">
					{{ role }}
				</li>
			</ul>
		</div>
	</div>
</template>

<script>
import Navbar from "../components/Navbar.vue";
export default {
	components: {
		Navbar,
	},
	name: "Home",
	computed: {
		currentUser() {
			return this.$store.state.auth.user;
		},
	},
	mounted() {
		console.log(this.currentUser)
		if (!this.currentUser) {
			this.$router.push("/login");
		}
	},
};
</script>
<style>
</style>
