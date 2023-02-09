import axios from 'axios';

const LOAN_URL = 'http://localhost:8090/loans';

class LoanService {
	getLoans() {
		return axios.get(LOAN_URL);
	}
    updateLoan(loan, id) {
		return axios.put(LOAN_URL + `/${id}`, loan);
	}
    addLoan(loan) {
		return axios.post(LOAN_URL, loan);
	}
    deleteLoan(id) {
		return axios.delete(LOAN_URL + `/${id}`);
	}
}

export default new LoanService();