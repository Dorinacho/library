<template>
	<v-card id="book">
		<!-- <v-card-item> -->
		<v-card-title>{{ bookData.title }}</v-card-title>

		<v-row class="text">
			<h4>{{ bookData.author }}</h4>
		</v-row>
		<!-- </v-card-item> -->

		<v-card-text> {{ bookData.description }} </v-card-text>
		<!-- <v-card-item> -->
		<v-card-subtitle>{{ bookData.isbn }}</v-card-subtitle>
		<v-card-subtitle>{{ bookData.availability }}</v-card-subtitle>
		<!-- </v-card-item> -->
		<v-row id="actions">
			<v-card-actions>
				<v-btn v-ripple @click="submit(bookData.isbn)"> Loan </v-btn>
			</v-card-actions>
		</v-row>
	</v-card>
</template>

<script>
export default {
	data() {
		return {};
	},
	props: {
		bookData: Object,
	},
	methods: {
		submit(isbn) {
			console.log(isbn)
			this.$emit('loanBook', isbn);
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