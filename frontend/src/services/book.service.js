import axios from 'axios';
import library from './auth/library';

const BOOK_URL = 'http://localhost:8090/library/books';

class BookService {
	getBooks() {
		return library.get('/books');
	}
	updateBook(book, id) {
		return axios.put(BOOK_URL + `/${id}`, book);
	}
	addBook(book) {
		return axios.post(BOOK_URL, book);
	}
	deleteBook(id) {
		return axios.delete(BOOK_URL + `/${id}`);
	}
}

export default new BookService();
