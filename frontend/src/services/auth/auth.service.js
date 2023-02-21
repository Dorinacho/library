import library from './library';
import TokenService from './token.service';

class AuthService {
	async login(user) {
		return library
		.post('auth/signin', user)
		.then((response) => {
			console.log('This is the data');
			console.log(response);
			if (response.data.accessToken) {
				// localStorage.setItem('username', JSON.stringify(respone.data.username));
				// localStorage.setItem('token', JSON.stringify(respone.data.accessToken));
				// localStorage.setItem('roles', JSON.stringify(respone.data.roles));
				// localStorage.setItem('user', JSON.stringify(respone.data));
				TokenService.setUser(response.data);
			}
			return response.data;
		});
	}
	logout() {
		// axios
		// 	.post(AUTH_URL + 'signout')
		// 	.then(console.log('You have been logged out!'));
		// localStorage.removeItem('username');
		// localStorage.removeItem('token');
		// localStorage.removeItem('roles');
		// localStorage.removeItem('user');
		TokenService.removeUser();
	}
	register(user) {
		return library.post('/auth/signup', user);
	}
}

export default new AuthService();
