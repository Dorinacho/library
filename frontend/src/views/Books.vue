<template>
	<div>
		<Navbar />
		<div class="wrapper">
			<v-alert
				v-model="alert"
				dense
				elevation="5"
				:type="alertType.type"
				dismissible
				class="alert"
				>{{ this.alertType.text }}</v-alert
			>
			<v-card class="table">
				<v-card-title>
					Books
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
					:items="books"
					:items-per-page="5"
					class="elevation-1"
					:search="search"
				>
					<template v-slot:top>
						<v-toolbar flat>
							<v-spacer></v-spacer>
							<v-dialog v-model="dialog" max-width="500px">
								<template v-slot:activator="{ on, attrs }">
									<v-btn
										color="primary"
										dark
										class="mb-2"
										v-bind="attrs"
										v-on="on"
									>
										Add Book
									</v-btn>
								</template>
								<v-card>
									<v-card-title>
										<span class="text-h5">{{ formTitle }}</span>
									</v-card-title>

									<v-card-text>
										<v-container>
											<v-row>
												<v-col cols="12" sm="6" md="7">
													<v-text-field
														v-model="editedBook.title"
														label="Title"
													></v-text-field>
												</v-col>
												<v-col cols="12" sm="6" md="3">
													<v-text-field
														v-model="editedBook.availability"
														label="Available copies"
													></v-text-field>
												</v-col>
											</v-row>
											<v-row>
												<v-col cols="12" sm="6" md="5">
													<v-text-field
														v-model="editedBook.author"
														label="Author"
													></v-text-field>
												</v-col>
												<v-col cols="12" sm="6" md="5">
													<v-text-field
														v-model="editedBook.isbn"
														label="ISBN"
													></v-text-field>
												</v-col>
											</v-row>
										</v-container>
									</v-card-text>

									<v-card-actions>
										<v-spacer></v-spacer>
										<v-btn color="blue darken-1" text @click="close">
											Cancel
										</v-btn>
										<v-btn color="blue darken-1" text @click="save">
											Save
										</v-btn>
									</v-card-actions>
								</v-card>
							</v-dialog>
							<v-dialog v-model="dialogDelete" max-width="500px">
								<v-card>
									<v-card-title class="text-h5"
										>Are you sure you want to delete this item?</v-card-title
									>
									<v-card-actions>
										<v-spacer></v-spacer>
										<v-btn color="blue darken-1" text @click="closeDelete"
											>Cancel</v-btn
										>
										<v-btn color="blue darken-1" text @click="deleteItemConfirm"
											>OK</v-btn
										>
										<v-spacer></v-spacer>
									</v-card-actions>
								</v-card>
							</v-dialog>
						</v-toolbar>
					</template>
					<template v-slot:[`item.actions`]="{ item }">
						<v-icon small class="mr-2" @click="editItem(item)">
							mdi-pencil
						</v-icon>
						<v-icon small @click="deleteItem(item)"> mdi-delete </v-icon>
					</template>
				</v-data-table>
			</v-card>
		</div>
	</div>
</template>

<script>
import Navbar from "../components/Navbar.vue";
import BookeService from "../services/book.service";
// import errorMsg from "../helpers/errorMsg";

export default {
	data() {
		return {
			alertType: "",
			alert: false,
			dialog: false,
			dialogDelete: false,
			search: "",
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
				{ text: "Actions", value: "actions", sortable: false },
			],
			books: [],
			editedIndex: -1,
			editedBook: {
				id: 0,
				author: "",
				title: "",
				isbn: "",
				availability: 0,
			},
			defaultBook: {
				author: "",
				title: "",
				isbn: "",
				availability: 0,
			},
		};
	},
	computed: {
		formTitle() {
			return this.editedIndex === -1 ? "Add Book" : "Edit Book";
		},
	},
	watch: {
		dialog(val) {
			val || this.close();
		},
		dialogDelete(val) {
			val || this.closeDelete();
		},
	},
	components: {
		Navbar,
	},
	methods: {
		fetchBooks() {
			BookeService.getBooks().then((response) => {
				this.books = response.data;
			});
		},
		editItem(book) {
			this.editedIndex = this.books.indexOf(book);
			this.editedBook = Object.assign({}, book);
			this.dialog = true;
		},

		deleteItem(book) {
			this.editedIndex = this.books.indexOf(book);
			this.editedBook = Object.assign({}, book);
			this.dialogDelete = true;
		},

		deleteItemConfirm() {
			BookeService.deleteBook(this.editedBook.id).then(() => {
				this.fetchBooks();
			});
			this.closeDelete();
		},

		close() {
			this.dialog = false;
			this.$nextTick(() => {
				this.editedBook = Object.assign({}, this.defaultBook);
				this.editedIndex = -1;
			});
		},

		closeDelete() {
			this.dialogDelete = false;
			this.$nextTick(() => {
				this.editedBook = Object.assign({}, this.defaultBook);
				this.editedIndex = -1;
			});
		},

		save() {
			if (this.editedIndex > -1) {
				BookeService.updateBook(this.editedBook, this.editedBook.id)
					.then(() => {
						this.fetchBooks();
					})
					.catch((error) => {
						console.log(error);
						console.log(error.response.status);
						this.alertType = {
							value: "update",
							text: "Could not update",
							type: "error",
						};
						this.alert = true;
					});
			} else {
				BookeService.addBook(this.editedBook).then(() => {
					this.fetchBooks();
				});
			}
			this.close();
		},
	},
	created() {
		this.fetchBooks();
	},
};
</script>

<style scoped>
.wrapper {
	display: flex;
	justify-content: center;
	flex-direction: column;
	align-items: center;
}

.table {
	width: 80%;
	margin-top: 30px;
}

.alert {
	position: absolute;

	top: 10%;
	z-index: 100;
}
</style>
