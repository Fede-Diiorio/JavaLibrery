package com.coderhouse.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.dtos.LoanDTO;
import com.coderhouse.dtos.UserBookLoanDTO;
import com.coderhouse.dtos.UserLoanDTO;
import com.coderhouse.mappers.LoanMapper;
import com.coderhouse.models.Book;
import com.coderhouse.models.Loan;
import com.coderhouse.models.User;
import com.coderhouse.repositories.LoanRepository;

import jakarta.transaction.Transactional;

@Service
public class LoanService {

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private BookService bookService;
	
	@Autowired
	private LoanMapper loanMapper;

	public List<UserBookLoanDTO> getAll() {
		List<Loan> loans = loanRepository.findAll();
		return loans.stream().map(loanMapper::toUserBookLoanDTO).toList();
	}

	private List<LoanDTO> getAllByUser(Long id) {
		List<Loan> loans = loanRepository.findByUser_Id(id);
		return loans.stream().map(loanMapper::ToDTO).toList();
	}

	public UserLoanDTO getUserById(Long id) {
		User user = userService.getUserById(id);
		List<LoanDTO> loans = getAllByUser(id);

		return loanMapper.ToUserLoanDTO(user, loans);
	}
	
	public List<UserBookLoanDTO> getAllNoReturn() {
		List<Loan> loans = loanRepository.findByReturnDateIsNull();
		return loans.stream().map(loanMapper::toUserBookLoanDTO).toList();
	}

	@Transactional
	public UserBookLoanDTO createNewLoan(Long userId, Long bookId) {

	    User user = userService.getUserById(userId); 
	    Book book = bookService.getBookbyId(bookId); 

	    Loan activeLoan = loanRepository.findByUser_IdAndBook_IdAndReturnDateIsNull(userId, bookId);

	    if (activeLoan != null) {
	        throw new IllegalArgumentException("Ya tiene este libro en préstamo.");
	    }
	    
	    int stock = (book.getStock() - 1);
	    if (stock >= 0) {
	    	book.setStock(stock);
	    	bookService.save(book);
	    } else {
	    	throw new IllegalArgumentException("El libro no está disponible en este momento.");
	    }

	    Loan newLoan = new Loan();
	    newLoan.setBook(book);
	    newLoan.setUser(user);
	    newLoan.setLoanDate(LocalDateTime.now());

	    loanRepository.save(newLoan);

	    return loanMapper.toUserBookLoanDTO(newLoan);
	}

	@Transactional
	public LoanDTO newReturn(Long bookId, Long userId) {
		Loan loan = loanRepository.findByUser_IdAndBook_IdAndReturnDateIsNull(userId, bookId);
		
		if (loan == null) {
	        throw new IllegalArgumentException("No existe un préstamo activo para este libro y usuario.");
	    }

		Book book = bookService.getBookbyId(bookId);
		
		book.setStock(book.getStock() + 1);
		
		bookService.save(book);
		
		loan.setReturnDate(LocalDateTime.now());
		loanRepository.save(loan);

		return loanMapper.ToDTO(loan);
	}


	
}
