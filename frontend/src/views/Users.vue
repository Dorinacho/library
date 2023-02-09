<template>
	<div>
		<Navbar />
		<div class="wrapper">
			<v-card class="table">
				<v-card-title>
					Users
					<v-spacer></v-spacer>
					<v-text-field
						v-model="search"
						append-icon="mdi-magnify"
						label="Search"
						single-line
						hide-details
					></v-text-field>
				</v-card-title>
				<v-data-table
					:headers="headers"
					:items="users"
					:items-per-page="5"
					class="elevation-1"
                    :search="search"
				></v-data-table>
			</v-card>
		</div>
	</div>
</template>

<script>
import Navbar from "../components/Navbar.vue";
import axios from "axios";
export default {
	data() {
		return {
			search:'',
			headers: [
				{
					text: "ID",
					align: "start",
					sortable: false,
					value: "id",
				},
				{ text: "Name", value: "name" },
				{ text: "Email", value: "email" },
			],
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

<style scoped>
.wrapper {
	display: flex;
	justify-content: center;
}

.table {
	width: 80%;
	margin-top: 30px;
}
</style>