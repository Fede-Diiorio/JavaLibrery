package com.coderhouse.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBookLoanDTO {
	private String loanDate;
	private String bookName;
	private Long bookId;
	private String userName;
	private Long userId;
}
