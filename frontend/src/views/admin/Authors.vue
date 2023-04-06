<template>
	<div>
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
					Authors
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
					:items="authors"
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
										Add Author
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
														v-model="editedAuthor.name"
														label="Name"
													></v-text-field>
												</v-col>
												<v-col cols="12" sm="6" md="3">
													<v-text-field
														v-model="editedAuthor.birthdate"
														label="Birthdate"
													></v-text-field>
												</v-col>
											</v-row>
											<v-row>
												<v-col cols="12" sm="6" md="5">
													<v-text-field
														v-model="editedAuthor.biography"
														label="Biography"
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
import AuthorService from "../../services/author.service";
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
				{ text: "Name", value: "name" },
				{ text: "Biography", value: "biography" },
				{ text: "Birthdate", value: "birthdate" },
				{ text: "Actions", value: "actions", sortable: false },
			],
			authors: [],
			editedIndex: -1,
			editedAuthor: {
				id: 0,
				name: "",
				biography: "",
				birthdate: "",
			},
			defaultAuthor: {
				id: 0,
				name: "",
				biography: "",
				birthdate: "",
			},
		};
	},
	computed: {
		formTitle() {
			return this.editedIndex === -1 ? "Add Author" : "Edit Author";
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
		fetchAuthors() {
			AuthorService.getAuthors().then((response) => {
				this.authors = response.data;
			});
			console.log(this.authors)
		},
		editItem(Author) {
			this.editedIndex = this.authors.indexOf(Author);
			this.editedAuthor = Object.assign({}, Author);
			this.dialog = true;
		},

		deleteItem(Author) {
			this.editedIndex = this.authors.indexOf(Author);
			this.editedAuthor = Object.assign({}, Author);
			this.dialogDelete = true;
		},

		deleteItemConfirm() {
			AuthorService.deleteAuthor(this.editedAuthor.id).then(() => {
				this.fetchAuthors();
			});
			this.closeDelete();
		},

		close() {
			this.dialog = false;
			this.$nextTick(() => {
				this.editedAuthor = Object.assign({}, this.defaultAuthor);
				this.editedIndex = -1;
			});
		},

		closeDelete() {
			this.dialogDelete = false;
			this.$nextTick(() => {
				this.editedAuthor = Object.assign({}, this.defaultAuthor);
				this.editedIndex = -1;
			});
		},

		save() {
			if (this.editedIndex > -1) {
				AuthorService.updateAuthor(this.editedAuthor, this.editedAuthor.id)
					.then(() => {
						this.fetchAuthors();
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
				AuthorService.addAuthor(this.editedAuthor).then(() => {
					this.fetchAuthors();
				});
			}
			this.close();
		},
	},
	created() {
		this.fetchAuthors();
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
