<template>
	<div class="wrapper">
		<div>
			<v-btn v-if="this.page > 1" @click="previousPage">Prev</v-btn>
			<v-btn>{{ this.page }}</v-btn>
			<v-btn @click="nextPage">Next</v-btn>
			<div>
				<v-select
					class="select"
					:items="itemsPerPage"
					item-text="name"
					item-value="value"
					label="Select items per page"
					v-model="selectedValue"
					return-object
					background-color="white"
					color="white"
				></v-select>
			</div>
		</div>
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
		<div id="books-wrapper">
			<div v-for="book in books" :key="book.isbn">
				<Book :bookData="book" @loanBook="loanTheBook" />
			</div>
		</div>
	</div>
</template>

<script>
	import BookService from '../../services/book.service';
	import LoanService from '../../services/loan.service';
	import Book from '../../components/Book.vue';

	export default {
		data() {
			return {
				page: 1,
				itemsPerPage: [
					{ name: '6', value: 6 },
					{ name: '12', value: 12 },
					{ name: '24', value: 24 },
				],
				selectedValue: { name: '6', value: 6 },
				selected: 6,
				alert: false,
				books: [],
				alertType: {
					value: 'update',
					text: 'You already borrowed this book!',
					type: 'error',
				},
			};
		},
		components: {
			Book,
		},
		emits: ['loanBook'],
		methods: {
			nextPage() {
				this.page++;
				this.fetchBooks();
			},
			previousPage() {
				this.page--;
				this.fetchBooks();
			},
			fetchBooks(selectedValue) {
				console.log(selectedValue);
				if (selectedValue != null) this.selected = selectedValue.value;
				BookService.getBooksPage(this.page - 1, this.selected).then(
					(response) => {
						this.books = response.data;
					}
				);
				// BookService.getBooks().then(
				// 	(response) => {
				// 		this.books = response.data;
				// 	}
				// );
				console.log(this.books);
			},
			loanTheBook(isbn) {
				console.log(
					typeof isbn + ' -> ' + typeof this.$store.state.auth.user.username
				);
				console.log(isbn);
				LoanService.addLoanForUser(this.$store.state.auth.user.username, isbn)
					.catch((e) => {
						if (e.response.status == 500) {
							this.alert = true;
						}
						console.warn(e);
					})
					.then(this.fetchBooks());
			},
		},
		created() {
			this.fetchBooks();
		},
		watch: {
			selectedValue: function (newValue) {
				this.fetchBooks(newValue);
			},
		},
	};
</script>

<style lang="scss" scoped>
	.wrapper {
		display: flex;
		overflow: inherit;
		flex-direction: column;
		justify-content: space-around;
		align-items: center;
	}

	.dialog {
		width: auto;
		height: 40px;
		overflow: initial;
	}
	#books-wrapper {
		display: flex;
		flex-wrap: wrap;
		justify-content: center;
	}
</style>
