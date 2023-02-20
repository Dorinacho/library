package com.library.mapper;

import com.library.dto.LoanDTO;
import com.library.model.Loan;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LoanMapper {
    public LoanDTO toDto(Loan loan) {
        String bookTitle = loan.getBook().getTitle();
        LocalDate loanDate = loan.getLoanDate();
        LocalDate returnDate = loan.getReturnDate();

        return new LoanDTO(bookTitle, loanDate, returnDate);
    }
}
