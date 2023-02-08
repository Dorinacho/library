import { createRouter, createWebHistory } from 'vue-router';

import HomePage from '@/components/Home.vue';
import Books from '@/components/Books.vue';
import Users from '@/components/Users.vue';

export const routes = [
    { path: '/', component: HomePage },
    { path: '/books', component: Books },
    { path: '/users', component: Users },
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
});
export default router;