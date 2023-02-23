// import { createRouter, createWebHistory } from 'vue-router';
import Vue from 'vue';
import VueRouter from 'vue-router';

import HomePage from '@/views/Home.vue';
import Login from '@/views/Login.vue';
import SignUp from '@/views/SignUp.vue';

Vue.use(VueRouter);

const routes = [
	{ path: '/home', name: 'home', component: HomePage },
	{ path: '/', component: HomePage },
	{
		path: '/books',
		name: 'books',
		component: () => import('@/views/admin/Books.vue'),
	},
	{
		path: '/user/books',
		name: 'userBooks',
		component: () => import('@/views/user/UserBooks.vue'),
	},
	{
		path: '/users',
		name: 'users',
		component: () => import('@/views/admin/Users.vue'),
	},
	{
		path: '/loans',
		name: 'loans',
		component: () => import('@/views/admin/Loans.vue'),
	},
	{ path: '/login', name: 'login', component: Login },
	{ path: '/signup', name: 'signup', component: SignUp },
	// { path: '*', redirect: '/' },
];

const router = new VueRouter({
	routes,
});

router.beforeEach((to, from, next) => {
	const publicPages = ['/login', '/register', '/home'];
	const authRequired = !publicPages.includes(to.path);
	const loggedIn = localStorage.getItem('user');

	// trying to access a restricted page + not logged in
	// redirect to login page
	if (authRequired && !loggedIn) {
		next('/login');
	} else {
		next();
	}
});

export default router;
