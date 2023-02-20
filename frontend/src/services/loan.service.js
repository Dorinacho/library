import axios from 'axios';
import authHeader from './auth/auth-header';

const LOAN_URL = 'http://localhost:8090/library/loans';

class LoanService {
	getLoans() {
		return axios.get(LOAN_URL, { headers: authHeader() });
	}
	getLoansForUser(username) {
		return axios.get(LOAN_URL + `/user/${username}`, { headers: authHeader() });
	}
	updateLoan(loan, id) {
		return axios.put(LOAN_URL + `/${id}`, loan, { headers: authHeader() });
	}
	addLoan(loan) {
		return axios.post(LOAN_URL, loan, { headers: authHeader() });
	}
	deleteLoan(id) {
		return axios.delete(LOAN_URL + `/${id}`, { headers: authHeader() });
	}
}

export default new LoanService();
