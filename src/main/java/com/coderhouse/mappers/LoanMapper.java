package com.coderhouse.mappers;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coderhouse.dtos.LoanDTO;
import com.coderhouse.dtos.UserBookLoanDTO;
import com.coderhouse.dtos.UserLoanDTO;
import com.coderhouse.models.Book;
import com.coderhouse.models.Loan;
import com.coderhouse.models.User;
import com.coderhouse.services.BookService;

@Component
public class LoanMapper {

	@Autowired
	private BookService bookService;

	public LoanDTO ToDTO(Loan loan) {
		LoanDTO loanDTO = new LoanDTO();

		Book book = bookService.getBookbyId(loan.getBook().getId());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedLoan = loan.getLoanDate().format(formatter);
		loanDTO.setLoanDate(formattedLoan);

		loanDTO.setBookName(book.getTitle());
		loanDTO.setBookId(book.getId());

		if (loan.getReturnDate() != null) {
			String fromattedReturn = loan.getReturnDate().format(formatter);
			loanDTO.setReturnDate(fromattedReturn);
		} else {
			loanDTO.setReturnDate("libro-sin-devolver");
		}

		return loanDTO;
	}

	public UserLoanDTO ToUserLoanDTO(User user, List<LoanDTO> loans) {
		UserLoanDTO userLoanDTO = new UserLoanDTO();

		userLoanDTO.setEmail(user.getEmail());
		userLoanDTO.setId(user.getId());
		userLoanDTO.setName(user.getName());
		userLoanDTO.setPhone(user.getPhone());
		userLoanDTO.setLoans(loans);

		return userLoanDTO;

	}

	public UserBookLoanDTO toUserBookLoanDTO(Loan loan) {
		UserBookLoanDTO newLoan = new UserBookLoanDTO();

		newLoan.setBookId(loan.getBook().getId());
		newLoan.setBookName(loan.getBook().getTitle());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedLoan = loan.getLoanDate().format(formatter);
		newLoan.setLoanDate(formattedLoan);

		newLoan.setUserId(loan.getUser().getId());
		newLoan.setUserName(loan.getUser().getName());

		return newLoan;
	}
}
