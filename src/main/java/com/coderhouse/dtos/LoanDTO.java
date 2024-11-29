package com.coderhouse.dtos;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanDTO {

	private LocalDateTime loanDate;
	private LocalDateTime returnDate;
	private String bookName;
}


