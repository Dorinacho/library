package com.library.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue
    @Column(name = "loan_id")
    private long id;
    @Column
    private String isbn;
    @Column (name = "user_id")
    private long userID;
    @Column
    private Date loanDate;
    @Column
    private Date returnDate;

    public Loan(String isbn, long userID, Date loanDate, Date returnDate) {
        this.isbn = isbn;
        this.userID = userID;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public Loan() {

    }

    public String getIsbn() {
        return isbn;
    }

    public long getUserID() {
        return userID;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }
}
