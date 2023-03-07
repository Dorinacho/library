package com.library.controllers;

import com.library.dto.LoanDTO;
import com.library.exceptions.ResourceNotFoundException;
import com.library.models.Loan;
import com.library.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/library/loans")
@CrossOrigin(origins = "http://localhost:8080")
public class LoanController {
    private final LoanService loanService;
    Logger logger
            = LoggerFactory.getLogger(LoanController.class);

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

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
        Optional<Loan> loan = loanService.getLoanByID(id);
        if (loan.isPresent()) {
            return new ResponseEntity<>(loan.get(), HttpStatus.OK);
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
    public ResponseEntity<Loan> addLoanForUser(@PathVariable(name = "username") String username, @RequestBody String isbn) {
        try {
            loanService.addLoanForUser(username, isbn);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            logger.error("Error => " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error => " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MODERATOR')")
    public ResponseEntity<Loan> updateLoan(@RequestBody Loan loan, @PathVariable(name = "id") Long loanID) {
        try {
            return new ResponseEntity<>(loanService.updateLoan(loan, loanID), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error => " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<HttpStatus> deleteLoan(@PathVariable(name = "id") Long loanID) {
        try {
            loanService.deleteLoan(loanID);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error => " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}