import Vue from 'vue';
import App from './App.vue';
import vuetify from './plugins/vuetify';
import Vuetify from 'vuetify';
import router from './router/index.js';
import store from './store/index';
import VeeValidate from 'vee-validate';
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import setupInterceptors from './services/auth/setupInterceptors';
import Vuex from 'vuex';
import {
	faHome,
	faUser,
	faUserPlus,
	faSignInAlt,
	faSignOutAlt,
} from '@fortawesome/free-solid-svg-icons';

library.add(faHome, faUser, faUserPlus, faSignInAlt, faSignOutAlt);

Vue.config.productionTip = false;
// Debug mode
Vue.config.debug = true;

// Devtools enabled
Vue.config.devtools = true;

// Silence logs and warnings
Vue.config.silent = false;
Vue.use(VeeValidate);
Vue.use(Vuetify);
Vue.use(Vuex);
Vue.component('font-awesome-icon', FontAwesomeIcon);

setupInterceptors(store);

new Vue({
	vuetify,
	store,
	router,
	render: (h) => h(App),
}).$mount('#app');
