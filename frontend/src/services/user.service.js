import axios from 'axios';
import authHeader from './auth/auth-header';

const USER_URL = 'http://localhost:8090/library/users';

class UserService {
	getUsers() {
		return axios.get(USER_URL, { headers: authHeader() });
	}
	updateUser(user, id) {
		return axios.put(USER_URL + `/${id}`, user, { headers: authHeader() });
	}
	addUser(user) {
		return axios.post(USER_URL, user, { headers: authHeader() });
	}
	deleteUser(id) {
		return axios.delete(USER_URL + `/${id}`, { headers: authHeader() });
	}
}

export default new UserService();
