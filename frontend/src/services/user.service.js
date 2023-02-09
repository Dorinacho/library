import axios from 'axios';

const USER_URL = 'http://localhost:8090/users';

class UserService {
	getUsers() {
		return axios.get(USER_URL);
	}
    updateUser(user, id) {
		return axios.put(USER_URL + `/${id}`, user);
	}
    addUser(user) {
		return axios.post(USER_URL, user);
	}
    deleteUser(id) {
		return axios.delete(USER_URL + `/${id}`);
	}
}

export default new UserService();