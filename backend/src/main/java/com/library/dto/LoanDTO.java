package com.library.dto;

import java.time.LocalDate;

public class LoanDTO {
    private String bookTitle;

    private LocalDate loanDate;

    private LocalDate returnDate;

    public LoanDTO(String bookTitle, LocalDate loanDate, LocalDate returnDate) {
        this.bookTitle = bookTitle;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
}
