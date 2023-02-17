<template>
	<div>
		<div>
			<center>This is the HomePage</center>
		</div>
		<div class="container" v-if="loggedIn">
			<header class="jumbotron">
				<h3>
					<strong>{{ currentUser.username }}</strong> Profile
				</h3>
			</header>
			<p>
				<strong>Token:</strong>
				{{ currentUser.accessToken}} ...
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
export default {
	name: "Home",
	computed: {
		currentUser() {
			return this.$store.state.auth.user;
		},
		loggedIn() {
			return this.$store.state.auth.status.loggedIn;
		},
	},
	mounted() {
		console.log(this.currentUser)
		// if (!this.currentUser) {
		// 	this.$router.push("/login");
		// }
	},
};
</script>
<style>
</style>
