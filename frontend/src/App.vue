<template>
	<v-app id="app">
		<v-main>
			<Navbar />
			<!-- <v-parallax src="https://cdn.vuetifyjs.com/images/parallax/material.jpg" id="parallax"> -->
			<v-parallax src="./assets/books.jpg" id="parallax">
				<router-view></router-view>
			</v-parallax>
		</v-main>
	</v-app>
	<!-- TODO : FIX THE SERVICES FOR THE REFRESH TOKEN -->
</template>

<script>
import Navbar from "./components/Navbar.vue";
import EventBus from "./helpers/EventBus";

export default {
	name: "App",
	components: {
		Navbar,
	},

	data: () => ({
		//
	}),
	// created() {
	// 	if (this.$store.state.auth.user != null)
	// 		this.$store.dispatch("auth/refreshPage");
	// },
	methods: {
		logOut() {
			this.$store.dispatch("auth/logout");
			this.$router.push("/login");
		},
	},
	mounted() {
		EventBus.on("logout", () => {
			this.logOut();
		});
	},
	beforeDestroy() {
		EventBus.remove("logout");
	},
};
</script>


<style lang="scss">
#parallax {
	height: 100% !important;
	width: 100% !important;
}
</style>