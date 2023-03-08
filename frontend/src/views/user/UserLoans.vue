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
					v-if="!isAdmin"
					:headers="headers"
					:items="loans"
					:items-per-page="5"
					class="elevation-1"
					:search="search"
				>
					<template v-slot:top>
						<v-toolbar flat>
							<v-dialog v-model="dialogDelete" max-width="500px">
								<v-card>
									<v-card-title class="text-h5"
										>Are you sure you want to return this book?</v-card-title
									>
									<v-card-actions>
										<v-spacer></v-spacer>
										<v-btn color="blue darken-1" text @click="closeDelete"
											>Cancel</v-btn
										>
										<v-btn color="blue darken-1" text @click="returnBookConfirm"
											>Yes</v-btn
										>
										<v-spacer></v-spacer>
									</v-card-actions>
								</v-card>
							</v-dialog>
						</v-toolbar>
					</template>
					<template v-slot:[`item.actions`]="{ item }">
						<v-btn color="blue darken-1" small text @click="returnBook(item)"
							>Return Book</v-btn
						>
					</template>
				</v-data-table>
			</v-card>
		</div>
	</div>
</template>

<script>
	import LoanService from '../../services/loan.service';

	export default {
		data() {
			return {
				dialogDelete: false,
				search: '',
				headers: [
					{ text: 'Book', value: 'bookTitle' },
					{ text: 'Loan date', value: 'loanDate' },
					{ text: 'Return date', value: 'returnDate' },
					{ text: 'Actions', value: 'actions', sortable: false },
				],
				loans: [],
				loanToDelete: {},
				books: [],
				users: [],
			};
		},
		computed: {
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
				LoanService.getLoansForUser(this.$store.state.auth.user.username).then(
					(response) => {
                        console.log("it works")
						this.loans = response.data;
						console.log(this.loans);
                        
					}
				);
			},
			returnBook(loan) {
				console.log('in delete');
				this.loanToDelete = Object.assign({}, loan);
                
				this.dialogDelete = true;
			},

			returnBookConfirm() {
				LoanService.returnBook(this.$store.state.auth.user.username, this.loanToDelete).then(() => {
					this.fetchLoans();
				});
				this.closeDelete();
			},

			closeDelete() {
				this.dialogDelete = false;
				this.$nextTick(() => {
					this.editedLoan = Object.assign({}, this.defaultLoan);
					this.editedIndex = -1;
				});
			},
		},
		created() {
			// this.fetchData();
			this.fetchLoans();
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
