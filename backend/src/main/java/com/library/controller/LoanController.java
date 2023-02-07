package com.library.controller;

import com.library.model.Loan;
import com.library.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    LoanService loanService;

    @GetMapping
    public List<Loan> getLoans() {
        return loanService.getLoans();
    }

    @GetMapping("/{id}")
    public Loan getLoanByID(@PathVariable(name = "id") Long id) {
        return loanService.getLoanByID(id);
    }

    @PostMapping
    public void addLoan(@RequestBody Loan loan) {
        loanService.addLoan(loan);
    }

    @PutMapping("/{id}")
    public void updateLoan(@RequestBody Loan loan, @PathVariable(name = "id") Long loanID) {
        loanService.updateLoan(loan, loanID);
    }

    @DeleteMapping("/{id}")
    public void deleteLoan(@PathVariable(name = "id") Long loanID) {
        loanService.deleteLoan(loanID);
    }
}