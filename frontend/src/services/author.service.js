import library from './auth/library';

class AuthorService {
	getAuthors() {
		return library.get('/authors');
	}
	updateAuthor(author, id) {
		return library.put(`authors/${id}`, author);
	}
	addAuthor(author) {
		return library.post('/authors', author);
	}
	deleteAuthor(id) {
		return library.delete(`/authors/${id}`);
	}
}

export default new AuthorService();
