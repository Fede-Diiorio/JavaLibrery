package com.coderhouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderhouse.models.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long>{

}
