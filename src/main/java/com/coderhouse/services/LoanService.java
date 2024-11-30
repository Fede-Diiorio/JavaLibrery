package com.coderhouse.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.dtos.LoanDTO;
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
	
	public List<LoanDTO> getAll() {
		List<Loan> loans = loanRepository.findAll();
		return loans.stream().map(this::convertToDTO).toList();
	}

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
	
	private LoanDTO convertToDTO(Loan loan) {
		LoanDTO loanDTO = new LoanDTO();
		
		Book book = bookService.getBookbyId(loan.getId());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedLoan = loan.getLoanDate().format(formatter);
		loanDTO.setLoanDate(formattedLoan);
		
		loanDTO.setBookName(book.getTitle());
		
		if(loan.getReturnDate() != null) {
			String fromattedReturn = loan.getReturnDate().format(formatter);
			loanDTO.setReturnDate(fromattedReturn);
		} else {
			loanDTO.setReturnDate("libro-sin-devolver");
		}
		
		return loanDTO;
	}

}
