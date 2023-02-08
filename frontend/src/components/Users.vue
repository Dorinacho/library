<template>
	<div>
		<Navbar />
	</div>
</template>

<script>
import Navbar from "./Navbar.vue";
import axios from "axios";
export default {
	data() {
		return {
			users: [],
		};
	},
	components: {
		Navbar,
	},
	methods: {
		fetchUsers() {
			axios.get("http://localhost:8090/users").then((response) => {
				this.users = response.data;
				console.log(this.users);
			});
		},
	},
	created() {
		this.fetchUsers();
	},
};
</script>
