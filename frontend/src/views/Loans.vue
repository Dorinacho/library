<template>
	<div>
		<Navbar />
		<div class="wrapper">
			<v-card class="table">
				<v-card-title>
					Book loans
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
					:items="loans"
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
            search: '',
			headers: [
				{
					text: "ID",
					align: "start",
					sortable: false,
					value: "id",
				},
				{ text: "Book", value: "book.title" },
				{ text: "User", value: "user.name" },
				{ text: "Loan date", value: "loanDate" },
				{ text: "Return date", value: "returnDate" },
				{ text: "Actions", value: "returnDate" },
			],
			loans: [],
		};
	},
	components: {
		Navbar,
	},
	methods: {
		fetchUsers() {
			axios.get("http://localhost:8090/loans").then((response) => {
				this.loans = response.data;
				console.log(this.loans);
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