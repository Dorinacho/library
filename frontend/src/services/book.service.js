import library from './auth/library';


class BookService {
	getBooks() {
		return library.get('/books');
	}
	updateBook(book, id) {
		return library.put(`books/${id}`, book);
	}
	addBook(book) {
		return library.post('/books', book);
	}
	deleteBook(id) {
		return library.delete(`/books/${id}`);
	}
}

export default new BookService();
