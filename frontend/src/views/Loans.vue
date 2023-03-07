<template>
	<div>
		<div class="wrapper">
			<v-card class="table">
				<v-card-title>
					Loans
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
					v-if="isAdmin"
					:headers="headers"
					:items="loans"
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
										Add Loan
									</v-btn>
								</template>
								<v-card>
									<v-card-title>
										<span class="text-h5">{{ formTitle }}</span>
									</v-card-title>

									<v-card-text>
										<v-container>
											<v-row>
												<v-col class="d-flex" cols="12" sm="6">
													<v-select
														v-model="editedLoan.book"
														:items="books"
														label="Book"
														item-text="title"
														item-value="book"
														return-object
													></v-select>
												</v-col>
												<v-col class="d-flex" cols="12" sm="6" v-if="isAdmin">
													<v-select
														v-model="editedLoan.user"
														:items="users"
														label="User"
														item-text="name"
														item-value="user"
														return-object
													></v-select>
												</v-col>
												<v-col cols="12" sm="6" md="4">
													<v-menu
														ref="date1"
														v-model="date1"
														:close-on-content-click="false"
														:return-value.sync="editedLoan.loanDate"
														transition="scale-transition"
														offset-y
														min-width="auto"
													>
														<template v-slot:activator="{ on, attrs }">
															<v-text-field
																v-model="editedLoan.loanDate"
																label="Loan date"
																prepend-icon="mdi-calendar"
																v-bind="attrs"
																v-on="on"
															></v-text-field>
														</template>
														<v-date-picker
															v-model="editedLoan.loanDate"
															no-title
															scrollable
														>
															<v-spacer></v-spacer>
															<v-btn
																text
																color="primary"
																@click="date1 = false"
															>
																Cancel
															</v-btn>
															<v-btn
																text
																color="primary"
																@click="$refs.date1.save(editedLoan.loanDate)"
															>
																OK
															</v-btn>
														</v-date-picker>
													</v-menu>
												</v-col>
												<!-- <v-col cols="12" sm="6" md="4">
													<v-text-field
														v-model="editedLoan.returnDate"
														label="Return date"
														readonly
													></v-text-field>
												</v-col> -->
											</v-row>
										</v-container>
									</v-card-text>

									<v-card-actions v-if="isAdmin">
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
					<template v-slot:[`item.actions`]="{ item }" v-if="isAdmin">
						<v-icon small class="mr-2" @click="editItem(item)">
							mdi-pencil
						</v-icon>
						<v-icon small @click="deleteItem(item)"> mdi-delete </v-icon>
					</template>
					<template v-slot:[`item.actions`]="{ item }" v-else-if="!isAdmin">
						<!-- <v-btn color="blue darken-1" text @click="returnBook(item)"
							>Return Book</v-btn
						> -->
						<v-icon small @click="deleteItem(item)"> mdi-delete </v-icon>
					</template></v-data-table
				>
				<v-data-table
					v-if="!isAdmin"
					:headers="headersUser"
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
import LoanService from "../services/loan.service";
import BookService from "../services/book.service";
import UserService from "../services/user.service";

export default {
	data() {
		return {
			date1: false,
			// date2: false,
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
				{ text: "Book", value: "book.title" },
				{ text: "User", value: "user.name" },
				{ text: "Loan date", value: "loanDate" },
				{ text: "Return date", value: "returnDate" },
				{ text: "Actions", value: "actions", sortable: false },
			],
			headersUser: [
				{ text: "Book", value: "bookTitle" },
				{ text: "Loan date", value: "loanDate" },
				{ text: "Return date", value: "returnDate" },
				{ text: "Actions", value: "actions", sortable: false },
			],
			loans: [],
			books: [],
			users: [],
			editedIndex: -1,
			editedLoan: {
				id: 0,
				book: "",
				user: "",
				loanDate: new Date(Date.now() - new Date().getTimezoneOffset() * 60000)
					.toISOString()
					.substr(0, 10),
				returnDate: "",
			},
			defaultLoan: {
				book: "",
				user: "",
				loanDate: new Date(Date.now() - new Date().getTimezoneOffset() * 60000)
					.toISOString()
					.substr(0, 10),
				returnDate: "",
			},
		};
	},
	computed: {
		formTitle() {
			return this.editedIndex === -1 ? "Add Loan" : "Edit Loan";
		},
		isAdmin() {
			return this.$store.state.auth.user.admin;
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
	methods: {
		fetchLoans() {
			if (this.isAdmin) {
				LoanService.getLoans().then((response) => {
					this.loans = response.data;
					console.log(this.loans);
				});
			} else {
				LoanService.getLoansForUser(this.$store.state.auth.user.username).then(
					(response) => {
						this.loans = response.data;
						console.log(this.loans);
					}
				);
			}
		},
		fetchData() {
			this.fetchLoans();
			BookService.getBooks().then((response) => {
				this.books = response.data;
			});
			UserService.getUsers().then((response) => {
				this.users = response.data;
			});
		},
		editItem(loan) {
			this.editedIndex = this.loans.indexOf(loan);
			this.editedLoan = Object.assign({}, loan);
			this.dialog = true;
		},

		deleteItem(loan) {
			this.editedIndex = this.loans.indexOf(loan);
			this.editedLoan = Object.assign({}, loan);
			this.dialogDelete = true;
		},

		deleteItemConfirm() {
			LoanService.deleteLoan(this.editedLoan.id).then(() => {
				this.fetchLoans();
			});
			this.closeDelete();
		},

		close() {
			this.dialog = false;
			this.$nextTick(() => {
				this.editedLoan = Object.assign({}, this.defaultLoan);
				this.editedIndex = -1;
			});
		},

		closeDelete() {
			this.dialogDelete = false;
			this.$nextTick(() => {
				this.editedLoan = Object.assign({}, this.defaultLoan);
				this.editedIndex = -1;
			});
		},

		save() {
			if (this.editedIndex > -1) {
				// this.editedLoan.returnDate = this.editedLoan.loanDate + 14;
				LoanService.updateLoan(this.editedLoan, this.editedLoan.id).then(() => {
					this.fetchLoans();
				});
			} else {
				LoanService.addLoan(this.editedLoan).then(() => {
					this.fetchLoans();
				});
			}
			this.close();
		},
		returnBook(loan) {
			LoanService.deleteLoan(loan).then(() => {
				this.fetchLoans();
			});
		},
	},
	created() {
		this.fetchData();
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

/* .alert {
	position: absolute;
	top: 10%;
	z-index: 100;
} */
</style>