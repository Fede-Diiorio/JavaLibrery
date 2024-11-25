package com.coderhouse.dtos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

	private Long id;
	private String name;
	private String email;
	private String phone;
	List<LoanDTO> loans;

}
