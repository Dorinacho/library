import axios from 'axios';

const BOOK_URL = 'http://localhost:8090/books';

class BookService {
	getBooks() {
		return axios.get(BOOK_URL);
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