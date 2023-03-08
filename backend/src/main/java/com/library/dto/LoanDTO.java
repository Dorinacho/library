package com.library.dto;

import java.time.LocalDate;

// a record, object with private fields, getters for each field and a public constructor with all the members
public class LoanDTO {
    private final String bookTitle;

    private final LocalDate loanDate;

    private final LocalDate returnDate;

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
