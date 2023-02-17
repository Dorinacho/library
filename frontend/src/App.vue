<template>
	<v-app>
		<v-main>
			<Navbar />
			<router-view></router-view>
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
