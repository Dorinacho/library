<template>
	<v-card id="book">
		<!-- <v-card-item> -->
		<v-card-title>{{ bookData.title }}</v-card-title>

		<v-card-subtitle>{{ bookData.author }}</v-card-subtitle>
		<!-- </v-card-item> -->

		<v-card-text> {{ bookData.description }} </v-card-text>
		<!-- <v-card-item> -->
		<v-card-subtitle>{{ bookData.isbn }}</v-card-subtitle>
		<v-card-subtitle>{{ bookData.availability }}</v-card-subtitle>
		<!-- </v-card-item> -->
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
};
</script>

<style lang="scss" scoped>
#book {
	width: 400px;
	margin: 15px;
}
</style>