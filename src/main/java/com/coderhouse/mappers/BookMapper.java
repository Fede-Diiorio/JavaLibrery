package com.coderhouse.mappers;

import org.springframework.stereotype.Component;

import com.coderhouse.dtos.BookDTO;
import com.coderhouse.models.Book;

@Component
public class BookMapper {

	public BookDTO toDTO(Book book) {
		return new BookDTO(book.getId(), book.getTitle(), book.getAutor(), book.getIsbn(), book.getStock());
	}
}
