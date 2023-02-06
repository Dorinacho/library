package com.library.repository;

import com.library.model.Loan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface LoanRepository extends CrudRepository<Loan, BigInteger> {
}
