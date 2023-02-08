import Vue from 'vue';
import App from './App.vue';
import vuetify from './plugins/vuetify';
import router from './router/index';

import Vue from 'vue';
import VueRouter from 'vue-router';
import vuetify from './plugins/vuetify';

import { routes } from './router/index';

import App from './App';

// Debug mode
Vue.config.debug = true;

// Devtools enabled
Vue.config.devtools = true;

// Silence logs and warnings
Vue.config.silent = false;

// install router
Vue.use(VueRouter);

// create router
var router = new VueRouter({
	history: true,
	routes: routes,
});

new Vue({
	vuetify,
	router,
	render: (h) => h(App),
}).$mount('#app');
