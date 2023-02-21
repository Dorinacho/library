package com.library.controller;

import com.library.dto.LoanDTO;
import com.library.model.Loan;
import com.library.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/library/loans")
@CrossOrigin(origins = "http://localhost:8080")
public class LoanController {

    @Autowired
    LoanService loanService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public List<Loan> getLoans() {
        return loanService.getLoans();
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasRole('USER')")
    public List<LoanDTO> getLoansForUser(@PathVariable String username) {
        return loanService.getLoansForUser(username);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MODERATOR')")
    public Loan getLoanByID(@PathVariable(name = "id") Long id) {
        return loanService.getLoanByID(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void addLoan(@RequestBody Loan loan) {
        loanService.addLoan(loan);
    }

    @PostMapping("/{username}")
    @PreAuthorize("hasRole('USER')")
    public void addLoanForUser(@PathVariable(name = "username") String username, @RequestBody String isbn) {
        System.out.println(isbn);
        loanService.addLoanForUser(username, isbn );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MODERATOR')")
    public void updateLoan(@RequestBody Loan loan, @PathVariable(name = "id") Long loanID) {
        loanService.updateLoan(loan, loanID);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public void deleteLoan(@PathVariable(name = "id") Long loanID) {
        loanService.deleteLoan(loanID);
    }
}