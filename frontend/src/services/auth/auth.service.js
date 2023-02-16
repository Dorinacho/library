import axios from 'axios';

const AUTH_URL = 'http://localhost:8090/library/auth/';

class AuthService {
	async login(user) {
		return axios.post(AUTH_URL + 'signin', user).then((respone) => {
			console.log("This is the data")
			console.log(respone)
			if (respone.data.accessToken) {
				localStorage.setItem('username', JSON.stringify(respone.data.username));
				localStorage.setItem('token', JSON.stringify(respone.data.accessToken));
				localStorage.setItem('roles', JSON.stringify(respone.data.roles));
			}
			return respone.data;
		});
	}
	logout() {
		// axios
		// 	.post(AUTH_URL + 'signout')
		// 	.then(console.log('You have been logged out!'));
		localStorage.removeItem('user');
	}
	register(user) {
		return axios.post(AUTH_URL + 'signup', user);
	}
}

export default new AuthService();
