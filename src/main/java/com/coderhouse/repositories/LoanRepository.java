package com.coderhouse.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderhouse.models.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long>{

	List<Loan> findByUser_Id(Long userId);
	
	Loan findByUser_IdAndBook_IdAndReturnDateIsNull(Long userId, Long bookId);
	
	List<Loan> findByReturnDateIsNull();
	
}
