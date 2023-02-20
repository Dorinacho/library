<template>
	<div class="wrapper">
		<div v-for="book in books" :key="book.isbn">
			<Book :bookData="book" />
		</div>
	</div>
</template>

<script>
import BookeService from "../../services/book.service";
import Book from "../../components/Book.vue";

export default {
	data() {
		return {
			books: [],
		};
	},
	components: {
		Book,
	},
	methods: {
		fetchBooks() {
			BookeService.getBooks().then((response) => {
				this.books = response.data;
			});
			console.log(this.books);
		},
	},
	created() {
		this.fetchBooks();
	},
};
</script>

<style lang="scss" scoped>
.wrapper {
	display: flex;
    overflow: inherit;
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: space-around;
}


</style>