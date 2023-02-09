import Vue from 'vue';
import App from './App.vue';
import vuetify from './plugins/vuetify';
import router from './router/index.js';

// Debug mode
Vue.config.debug = true;

// Devtools enabled
Vue.config.devtools = true;

// Silence logs and warnings
Vue.config.silent = false;


new Vue({
	vuetify,
	router,
	render: (h) => h(App),
}).$mount('#app');
