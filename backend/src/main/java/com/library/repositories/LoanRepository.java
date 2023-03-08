package com.library.repositories;

import com.library.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query(value = "SELECT * from loans WHERE user_id = :userID", nativeQuery = true)
    List<Loan> findLoansByUserId(@Param("userID") Long userId);

    @Query(value = "SELECT * from loans WHERE user_id = :userID AND book_id = :bookID AND loan_date = :loanDate " +
            "AND return_date = :returnDate", nativeQuery = true)
    Loan findLoanByLoanDTO(@Param("userID") Long userID, @Param("bookID") Long bookID,
                           @Param("loanDate") LocalDate loanDate, @Param("returnDate") LocalDate returnDate);
}
