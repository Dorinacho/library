<template>
	<div>
		<Navbar />
		<v-data-table
			:headers="headers"
			:items="books"
			:items-per-page="5"
			class="elevation-1"
		></v-data-table>
	</div>
</template>

<script>
import Navbar from "./Navbar.vue";
import axios from "axios";
export default {
	data() {
		return {
			headers: [
				{
					text: "ID",
					align: "start",
					sortable: false,
					value: "id",
				},
				{ text: "Author", value: "author" },
				{ text: "Title", value: "title" },
				{ text: "ISBN", value: "isbn" },
				{ text: "Available copies", value: "availability" },
			],
			books: [],
		};
	},
	components: {
		Navbar,
	},
	methods: {
		fetchBooks() {
			axios.get("http://localhost:8090/books").then((response) => {
				this.books = response.data;
				console.log(this.books);
			});
		},
	},
	created() {
		this.fetchBooks();
	},
};
</script>
