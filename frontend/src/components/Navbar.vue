<template>
	<nav id="navbar">
		<router-link to="/" class="link">Home</router-link>
		<router-link to="/books" class="link">Books</router-link>
		<router-link to="/loans" class="link">Loans</router-link>
		<router-link to="/users" class="link">Users</router-link>
	</nav>
</template>

<script>
export default {

};
</script>
<style>
#navbar {
	height: 60px;
	width: 100%;
	display: flex;
	background-color: bisque;
	align-items: center;
	justify-content: flex-end;
}
.link {
	margin: 0px 10px;
	color:black !important;
	text-decoration: none;
}

.link:active{
	color: cornflowerblue !important;
}

</style>
