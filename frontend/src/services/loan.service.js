import library from './auth/library';


class LoanService {
	getLoans() {
		return library.get('/loans');
	}
	getLoansForUser(username) {
		return library.get(`/loans/user/${username}`);
	}
	updateLoan(loan, id) {
		return library.put(`/loans/${id}`, loan);
	}
	addLoan(loan) {
		return library.post('/loans', loan);
	}
	addLoanForUser(username, isbn) {
		console.log(isbn);
		return library.post(`/loans/${username}`, isbn);
	}
	deleteLoan(id) {
		return library.delete(`/loans/${id}`);
	}
}

export default new LoanService();
