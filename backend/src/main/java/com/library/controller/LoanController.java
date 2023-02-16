package com.library.controller;

import com.library.model.Loan;
import com.library.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
@CrossOrigin(origins = "http://localhost:8080")
public class LoanController {

    @Autowired
    LoanService loanService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MODERATOR')")
    public List<Loan> getLoans() {
        return loanService.getLoans();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MODERATOR')")
    public Loan getLoanByID(@PathVariable(name = "id") Long id) {
        return loanService.getLoanByID(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public void addLoan(@RequestBody Loan loan) {
        loanService.addLoan(loan);
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