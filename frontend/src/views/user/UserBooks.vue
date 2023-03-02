<template>
	<div class="wrapper">
		<v-dialog v-model="alert" class="dialog" width="auto">
			<v-alert
				v-model="alert"
				dense
				elevation="5"
				:type="alertType.type"
				dismissible
				class="alert"
				>{{ this.alertType.text }}</v-alert
			>
		</v-dialog>
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
			alert: false,
			books: [],
			alertType: {
				value: "update",
				text: "You already borrowed this book!",
				type: "error",
			},
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
			console.log(
				typeof isbn + " -> " + typeof this.$store.state.auth.user.username
			);
			LoanService.addLoanForUser(this.$store.state.auth.user.username, isbn)
				.catch((e) => {
					if (e.response.status == 500) {
						this.alert = true;
					}
					console.warn(e);
				})
				.then(this.fetchBooks())
				.then(console.log("Book loaned with success!"));
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

.dialog{
	width: auto;
	height: 40px;
    overflow: initial;
}
</style>