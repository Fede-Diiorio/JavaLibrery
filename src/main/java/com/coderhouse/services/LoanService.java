package com.coderhouse.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.models.Book;
import com.coderhouse.models.Loan;
import com.coderhouse.models.User;
import com.coderhouse.repositories.LoanRepository;

@Service
public class LoanService {

	@Autowired
	private LoanRepository loanRepository;
	
	@Autowired
	private UserService userService;

	@Autowired
	private BookService bookService;

	public Loan createNewLoan(Long bookId, Long userId) {
		
		User user = userService.getUserById(userId);
		Book book = bookService.getBookbyId(bookId);
		
		Loan loan = new Loan();
		
		loan.setBook(book);
		loan.setUser(user);
		loan.setLoanDate(LocalDateTime.now());
		
		loanRepository.save(loan);
		
		return loan;
		
	}
	
	public Loan newReturn(Long bookId, Long userId) {
		Loan loan = loanRepository.findByUser_IdAndBook_Id(userId, bookId);
		
		loan.setReturnDate(LocalDateTime.now());
		
		loanRepository.save(loan);
		
		return loan;
	}

}
