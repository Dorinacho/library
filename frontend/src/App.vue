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
</template>

<script>
import Navbar from "./components/Navbar.vue";

export default {
	name: "App",
	components: {
		Navbar,
	},

	data: () => ({
		//
	}),
	created() {
		if (this.$store.state.auth.user != null)
			this.$store.dispatch("auth/refreshPage");
	},
};
</script>


<style lang="scss">
#parallax{
	height: 100% !important;
    width: 100% !important;
}
</style>