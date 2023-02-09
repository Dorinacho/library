package com.library.service;

import com.library.model.Loan;
import com.library.repository.LoanRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    public List<Loan> getLoans() {
        return loanRepository.findAll();
    }

    public Loan getLoanByID(Long id) {
        return loanRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public void addLoan(@RequestBody @NotNull Loan loan) {
        loanRepository.save(loan);
    }

    public void updateLoan(@NotNull Loan loanData, Long loanID) {
        Loan loan = loanRepository.findById(loanID).orElseThrow(() -> new RuntimeException("Loan was not found!"));
        loan.setBook(loanData.getBook());
        loan.setUser(loanData.getUser());
        loan.setLoanDate(loanData.getLoanDate());
        final int DAYS_UNTIL_RETURN = 21;
        loan.setReturnDate(LocalDate.parse(loan.getLoanDate().toString()).plusDays(DAYS_UNTIL_RETURN));
        System.out.println(loan);
        loanRepository.save(loan);
    }

    public void deleteLoan(Long bookID) {
        loanRepository.deleteById(bookID);
    }
}
