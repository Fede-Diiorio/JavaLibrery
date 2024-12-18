package com.coderhouse.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderhouse.dtos.UserBookLoanDTO;
import com.coderhouse.services.LoanService;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

	@Autowired
	private LoanService loanService;
	
	@GetMapping
	public ResponseEntity<List<UserBookLoanDTO>> getAllLoans() {
		try {
			List<UserBookLoanDTO> loans = loanService.getAll();
			return ResponseEntity.ok(loans);
			
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
	}
	
	@GetMapping("/no-return")
	public ResponseEntity<List<UserBookLoanDTO>> getAllLoansNoRetruned() {
		try {
			List<UserBookLoanDTO> loans = loanService.getAllNoReturn();
			return ResponseEntity.ok(loans);
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
	}
	
}
