import { errorMsg } from '@/helpers/errorMsg';
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
		return library.post(`/loans/${username}`, toString(isbn));
	}
	deleteLoan(id) {
		return library.delete(`/loans/${id}`);
	}
	returnBook(username, loan) {
		return library.delete(`/loans/user/${username}`, { data: loan });
	}
}

export default new LoanService();
