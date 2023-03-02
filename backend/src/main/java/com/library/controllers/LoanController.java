package com.library.controllers;

import com.library.dto.LoanDTO;
import com.library.models.Loan;
import com.library.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/loans")
@CrossOrigin(origins = "http://localhost:8080")
public class LoanController {
    Logger logger
            = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private LoanService loanService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<List<Loan>> getLoans() {
        List<Loan> loans = loanService.getLoans();
        if (loans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<LoanDTO>> getLoansForUser(@PathVariable String username) {
        List<LoanDTO> loans = loanService.getLoansForUser(username);
        if (loans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MODERATOR')")
    public ResponseEntity<Loan> getLoanByID(@PathVariable(name = "id") Long id) {
        if (loanService.getLoanByID(id).isPresent()) {
            return new ResponseEntity<>(loanService.getLoanByID(id).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Loan> addLoan(@RequestBody Loan loan) {
        try {
            loanService.addLoan(loan);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error => " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{username}")
    @PreAuthorize("hasRole('USER')")
    public void addLoanForUser(@PathVariable(name = "username") String username, @RequestBody String isbn) {
        System.out.println(isbn);
        loanService.addLoanForUser(username, isbn);
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