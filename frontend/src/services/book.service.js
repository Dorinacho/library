import axios from 'axios';
import authHeader from './auth/auth-header';

const BOOK_URL = 'http://localhost:8090/library/books';

class BookService {
	getBooks() {
		return axios.get(BOOK_URL, { headers: authHeader() });
	}
	updateBook(book, id) {
		return axios.put(BOOK_URL + `/${id}`, book, { headers: authHeader() });
	}
	addBook(book) {
		return axios.post(BOOK_URL, book, { headers: authHeader() });
	}
	deleteBook(id) {
		return axios.delete(BOOK_URL + `/${id}`, { headers: authHeader() });
	}
}

export default new BookService();
