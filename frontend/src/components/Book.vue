<template>
	<div>
		<v-card id="book">
			<!-- <v-card-item> -->
			<v-card-title>{{ bookData.title }}</v-card-title>

			<v-row class="text">
				<h4>{{ bookData.author.name }}</h4>
			</v-row>
			<!-- </v-card-item> -->

			<v-card-text> {{ bookData.description }} </v-card-text>
			<!-- <v-card-item> -->
			<v-card-subtitle>{{ bookData.isbn }}</v-card-subtitle>
			<v-card-subtitle>{{ bookData.availability }}</v-card-subtitle>
			<!-- </v-card-item> -->
			<v-row id="actions">
				<v-card-actions>
					<v-btn v-ripple @click="dialog = true"> Loan </v-btn>
				</v-card-actions>
			</v-row>
		</v-card>
		<v-dialog v-model="dialog" width="auto">
			<!-- <template v-slot:activator="{ props }">
				<v-btn color="primary" v-bind="props"> Open Dialog </v-btn>
			</template> -->
			<v-card>
				<v-card-title>
					<span class="text-h5">Are you sure you want to loan this book?</span>
				</v-card-title>

				<v-card-actions>
					<v-spacer></v-spacer>
					<v-btn color="blue darken-1" text @click="dialog = false"> No </v-btn>
					<v-btn color="blue darken-1" text @click="submit(bookData.isbn)">
						Yes
					</v-btn>
				</v-card-actions>
			</v-card>
		</v-dialog>
	</div>
</template>

<script>
export default {
	data() {
		return {
			dialog: false,
		};
	},
	props: {
		bookData: Object,
	},
	methods: {
		submit(isbn) {
			this.dialog = false;
			// console.log(isbn);
			this.$emit("loanBook", isbn);
		},
	},
};
</script>

<style lang="scss" scoped>
#book {
	width: 400px;
	margin: 15px;
}

.row {
	margin: 0 !important;
}

#actions {
	justify-content: flex-end;
}
.text {
	margin-left: 25px !important;
}
</style>