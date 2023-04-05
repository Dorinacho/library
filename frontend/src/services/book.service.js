import library from './auth/library';

class BookService {
	getBooks() {
		return library.get('/books');
	}
	getBooksPage(page, itemNumber) {
		console.log(page);
		console.log(itemNumber);
		return library.get('/books/page', {
			params: { page: page, itemNumber: itemNumber },
		});
	}
	getBooksByTitle(title, limit, offset) {
		return library.get('/books/title', title);
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
