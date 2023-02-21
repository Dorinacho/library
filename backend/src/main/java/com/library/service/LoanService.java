package com.library.service;

import com.library.dto.LoanDTO;
import com.library.mapper.LoanMapper;
import com.library.models.Book;
import com.library.models.Loan;
import com.library.models.User;
import com.library.repositories.BookRepository;
import com.library.repositories.LoanRepository;
import com.library.repositories.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LoanService {

    final int DAYS_UNTIL_RETURN = 21;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanMapper loanMapper;

    public List<Loan> getLoans() {
        return loanRepository.findAll();
    }

    public List<LoanDTO> getLoansForUser(String username) {
        long id = userRepository.findByUsername(username).get().getId();
        return loanRepository.findLoansByUserId(id).stream().map(loan -> loanMapper.toDto(loan)).toList();
    }

    public Loan getLoanByID(Long id) {
        return loanRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public void addLoan(@RequestBody @NotNull Loan loan) {
        Book book = bookRepository.findById(loan.getBook().getId()).orElseThrow(() -> new RuntimeException("Book was not found!"));
        book.setAvailability(book.getAvailability() - 1);
        bookRepository.save(book);
        loan.setReturnDate(LocalDate.parse(loan.getLoanDate().toString()).plusDays(DAYS_UNTIL_RETURN));
        loanRepository.save(loan);
    }

    public void addLoanForUser(String username, String isbn) {
        Book book = bookRepository.findByIsbn(isbn.replace('=',' ').trim()).orElseThrow(() -> new RuntimeException("Book was not found!"));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User was not found!"));
        book.setAvailability(book.getAvailability() - 1);
        bookRepository.save(book);
        Loan loan = new Loan(book, user, LocalDate.now());
        loan.setReturnDate(LocalDate.parse(loan.getLoanDate().toString()).plusDays(DAYS_UNTIL_RETURN));
        loanRepository.save(loan);
    }

    public void updateLoan(@NotNull Loan loanData, Long loanID) {
        Loan loan = loanRepository.findById(loanID).orElseThrow(() -> new RuntimeException("Loan was not found!"));
        loan.setBook(loanData.getBook());
        loan.setUser(loanData.getUser());
        loan.setLoanDate(loanData.getLoanDate());
        loan.setReturnDate(LocalDate.parse(loan.getLoanDate().toString()).plusDays(DAYS_UNTIL_RETURN));
        System.out.println(loan);
        loanRepository.save(loan);
    }

    public void deleteLoan(Long bookID) {
        loanRepository.deleteById(bookID);
    }
}
