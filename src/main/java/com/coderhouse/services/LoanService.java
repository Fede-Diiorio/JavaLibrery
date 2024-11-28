package com.coderhouse.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.dtos.LoanDTO;
import com.coderhouse.models.Book;
import com.coderhouse.models.Loan;
import com.coderhouse.repositories.LoanRepository;

@Service
public class LoanService {

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private BookService bookService;

	public List<Loan> getAll() {
		return loanRepository.findAll();
	}

	public LoanDTO getById(Long id) {
		Loan loan = getLoanById(id);
		
		return convertToDTO(loan);
	}

	public Loan getLoanById(Long id) {
		return loanRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("El pedido no se encuentra registrado"));
	}

	public LoanDTO save(Loan object) {
		Loan savedLoan = loanRepository.save(object);
		return convertToDTO(savedLoan);
	}

	public Loan newLoan(Long bookId) {

		Book book = bookService.getBookbyId(bookId);
		Loan newLoan = new Loan();
		
		newLoan.setBook(book);
		newLoan.setLoanDate(LocalDateTime.now());
		
		return newLoan;
		
	}
	
	public Loan newReturn(Long loanId) {
		Loan loan = getLoanById(loanId);
		loan.setReturnDate(LocalDateTime.now());
		return loan;
	}
	
	private LoanDTO convertToDTO(Loan loan) {
		LoanDTO loanDTO = new LoanDTO();
		
		loanDTO.setBookName(loan.getBook().getTitle());
		loanDTO.setLoanDate(loan.getLoanDate());
		if(loan.getReturnDate() != null) {
			loanDTO.setReturnDate(loan.getReturnDate());
		} 
		
		return loanDTO;
			
	}

}
