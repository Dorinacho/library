<template>
	<div class="wrapper">
		<div v-for="book in books" :key="book.isbn">
			<Book :bookData="book" @loanBook="loanTheBook" />
		</div>
	</div>
</template>

<script>
import BookService from "../../services/book.service";
import LoanService from "../../services/loan.service";
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
	emits: ["loanBook"],
	methods: {
		fetchBooks() {
			BookService.getBooks().then((response) => {
				this.books = response.data;
			});
			console.log(this.books);
		},
		loanTheBook(isbn) {
			console.log("parent");
			console.log(isbn);
			LoanService.addLoanForUser(this.$store.state.auth.user.username, isbn)
				.then(this.fetchBooks())
				.then(console.log("Book loaned with success!"))
				.catch((e) => {
					console.warn(e);
				})
				.then(this.fetchBooks());
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