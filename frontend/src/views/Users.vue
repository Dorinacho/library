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
					Users
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
					:items="users"
					:items-per-page="5"
					class="elevation-1"
					:search="search"
				>
					<template v-slot:top>
						<v-toolbar flat>
							<v-dialog v-model="dialog" max-width="500px">
								<v-card>
									<v-card-title>
										<span class="text-h5">{{ formTitle }}</span>
									</v-card-title>
									<v-card-text>
										<v-container>
											<v-row>
												<!-- <v-col cols="12" sm="6" md="5"> -->
												<v-text-field
													v-model="editedUser.name"
													label="Name"
												></v-text-field>
											</v-row>
											<v-row>
												<!-- <v-col cols="12" sm="6" md="5"> -->
												<v-text-field
													v-model="editedUser.username"
													label="Username"
												></v-text-field>
											</v-row>
											<v-row>
												<!-- </v-col> -->
												<!-- <v-col cols="12" sm="6" md="7"> -->
												<v-text-field
													v-model="editedUser.email"
													label="Email"
												></v-text-field>
												<!-- </v-col> -->
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
										>Are you sure you want to delete this user?</v-card-title
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
					</template></v-data-table
				>
			</v-card>
		</div>
	</div>
</template>

<script>
import Navbar from "../components/Navbar.vue";
import UserService from "../services/user.service";

export default {
	data() {
		return {
			alertType: "",
			alert: false,
			dialog: false,
			dialogDelete: false,
			search: "",
			headers: [
				// {
				// 	text: "ID",
				// 	align: "start",
				// 	sortable: false,
				// 	value: "id",
				// },
				{ text: "Name", value: "name" },
				{ text: "Username", value: "username" },
				{ text: "Email", value: "email" },
				{ text: "Actions", value: "actions", sortable: false },
			],
			users: [],
			editedIndex: -1,
			editedUser: {
				id:"",
				name: "",
				username: "",
				email: "",
			},
			defaultUser: {
				id:"",
				name: "",
				username: "",
				email: "",
			},
		};
	},
	computed: {
		formTitle() {
			return this.editedIndex === -1 ? "Add User" : "Edit User";
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
		fetchUsers() {
			UserService.getUsers().then((response) => {
				this.users = response.data;
				console.log(this.users);
			});
		},
		editItem(user) {
			this.editedIndex = this.users.indexOf(user);
			this.editedUser = Object.assign({}, user);
			this.dialog = true;
		},

		deleteItem(user) {
			this.editedIndex = this.users.indexOf(user);
			this.editedUser = Object.assign({}, user);
			this.dialogDelete = true;
		},

		deleteItemConfirm() {
			UserService.deleteUser(this.editedUser.id).then(() => {
				this.fetchUsers();
			});
			this.closeDelete();
		},

		close() {
			this.dialog = false;
			this.$nextTick(() => {
				this.editedUser = Object.assign({}, this.defaultUser);
				this.editedIndex = -1;
			});
		},

		closeDelete() {
			this.dialogDelete = false;
			this.$nextTick(() => {
				this.editedUser = Object.assign({}, this.defaultUser);
				this.editedIndex = -1;
			});
		},

		save() {
			if (this.editedIndex > -1) {
				// Object.assign(this.Users[this.editedIndex], this.editedUser);
				UserService.updateUser(this.editedUser, this.editedUser.id)
					.then(() => {
						this.fetchUsers();
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
				UserService.addUser(this.editedUser).then(() => {
					this.fetchUsers();
				});
			}
			this.close();
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