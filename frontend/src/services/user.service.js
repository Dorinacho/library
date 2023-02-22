import library from './auth/library';


class UserService {
	getUsers() {
		return library.get('/users');
	}
	getUsernames() {
		return library.get('/users/usernames');
	}
	updateUser(user, id) {
		console.log(user);
		return library.put(`/users/${id}`, user);
	}
	addUser(user) {
		return library.post('/users', user);
	}
	deleteUser(id) {
		return library.delete(`/users/${id}`);
	}
}

export default new UserService();
