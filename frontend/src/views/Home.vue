<template>
	<!-- <v-parallax src="https://cdn.vuetifyjs.com/images/parallax/material.jpg" id="parallax"> -->
	<!-- <v-parallax src="../assets/home-background.jpg"> -->
		<!-- <div>
			<center>This is the HomePage</center>
		</div> -->
		<div class="wrapper">
			<div id="rectangle"></div>
			<p id="text">It's time to read a book. Grab yours now</p>
			<!-- <header class="jumbotron">
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
			</ul> -->
		</div>
	<!-- </v-parallax> -->
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
		console.log(this.currentUser);
		// if (!this.currentUser) {
		// 	this.$router.push("/login");
		// }
	},
};
</script>
<style>
/* .wrapper {
	background: url("../assets/home-background.jpg");
	background-size: auto;
	background-position: center center;
	background-attachment: fixed;
	background-repeat: no-repeat;
	overflow: scroll;
} */


#rectangle {
	box-sizing: border-box;
	position: absolute;
	width: 514px;
	height: 176px;
	left: 137px;
	top: 131px;

	background: #000000;
	opacity: 0.5;
	box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
	border-radius: 25px;
}

/* It’s time to read a book. Get yours now */
#text {
	position: absolute;
	width: 492px;
	height: 129px;
	left: 159px;
	top: 155px;

	font-family: "Kiwi Maru";
	font-style: normal;
	font-weight: 400;
	font-size: 40px;
	line-height: 58px;

	color: #ffffff;
}
</style>
