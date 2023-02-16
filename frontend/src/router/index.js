// import { createRouter, createWebHistory } from 'vue-router';
import Vue from 'vue';
import VueRouter from 'vue-router';

import HomePage from '@/views/Home.vue';
import Books from '@/views/Books.vue';
import Users from '@/views/Users.vue';
import Loans from '@/views/Loans.vue';
import Login from '@/views/Login.vue';

Vue.use(VueRouter);

const routes = [
	{ path: '/home', component: HomePage},
	{ path: '/books', component: Books },
	{ path: '/users', component: Users },
	{ path: '/loans', component: Loans },
	{ path: '/login', component: Login },
];

// const router = createRouter({
//     history: createWebHistory(process.env.BASE_URL),
//     routes,
// });

const router = new VueRouter({
	routes,
});
export default router;
