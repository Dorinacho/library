package com.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "loans")
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "loan_id")
    private long id;

    @Column
    private Date loanDate;
    @Column
    private Date returnDate;

    @ManyToOne(targetEntity = Book.class)
    @JoinColumn(name = "isbn")
    private Book bookISBN;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User userID;

    public Loan(Book bookISBN, User userID, Date loanDate, Date returnDate) {
        this.bookISBN = bookISBN;
        this.userID = userID;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public Book getIsbn() {
        return bookISBN;
    }

    public User getUserID() {
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
