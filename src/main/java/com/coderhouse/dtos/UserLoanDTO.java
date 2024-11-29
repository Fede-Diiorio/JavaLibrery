package com.coderhouse.dtos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoanDTO {

	private Long id;
	private String name;
	private String email;
	private String phone;
	List<LoanDTO> loans;

}
