package com.coderhouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.coderhouse.dtos.BookDTO;
import com.coderhouse.interfaces.DAOInterface;
import com.coderhouse.models.Book;
import com.coderhouse.repositories.BookRepository;

public class BookService implements DAOInterface<Book, BookDTO> {

	@Autowired
	private BookRepository bookRepository;

	@Override
	public List<BookDTO> getAll() {
		List<Book> books = bookRepository.findAll();
		return books.stream().map(this::convertToDTO).toList();
	}

	@Override
	public BookDTO getById(Long id) {
		Book book = getBookbyId(id);
		return convertToDTO(book);
	}

	@Override
	public BookDTO save(Book object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookDTO update(Long id, Book object) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	public Book getBookbyId(Long id) {
		return bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("El libo no existe."));
	}

	private BookDTO convertToDTO(Book book) {
		BookDTO bookDTO = new BookDTO(book.getId(), book.getTitle(), book.getAutor(), book.getIsbn(), book.getStock());
		return bookDTO;

	}

}
