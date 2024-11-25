package com.coderhouse.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {

	private Long id;
	private String title;
	private String autor;
	private String isbn;
	private int stock;
}
