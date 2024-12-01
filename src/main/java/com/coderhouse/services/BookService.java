package com.coderhouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.dtos.BookDTO;
import com.coderhouse.interfaces.DAOInterface;
import com.coderhouse.mappers.BookMapper;
import com.coderhouse.models.Book;
import com.coderhouse.repositories.BookRepository;

import jakarta.transaction.Transactional;

@Service
public class BookService implements DAOInterface<Book, BookDTO> {

	private static final String AUTHOR_REQUIRED = "El autor del libro es obligatorio.";
	private static final String ISBN_REQUIRED = "El ISBN del libro es obligatorio.";
	private static final String STOCK_POSITIVE = "El stock no puede ser menor a cero.";
	private static final String TITLE_REQUIRED = "El título del libro es obligatorio.";

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired 
	private BookMapper bookMapper;

	@Override
	public List<BookDTO> getAll() {
		List<Book> books = bookRepository.findAll();
		return books.stream().map(bookMapper::toDTO).toList();
	}

	@Override
	public BookDTO getById(Long id) {
		Book book = getBookbyId(id);
		return bookMapper.toDTO(book);
	}

	@Override
	@Transactional
	public BookDTO save(Book object) {
		Book savedBook = bookRepository.save(object);
		validateMandatoryFields(savedBook);
		return bookMapper.toDTO(savedBook);
	}

	@Override
	@Transactional
	public BookDTO update(Long id, Book object) throws Exception {

		Book book = getBookbyId(id);

		if (object.getAutor() != null && !object.getAutor().isEmpty()) {
			book.setAutor(object.getAutor());
		}
		if (object.getIsbn() != null && !object.getIsbn().isEmpty()) {
			book.setIsbn(object.getIsbn());
		}
		if (object.getStock() <= 0) {
			book.setStock(object.getStock());
		}
		if (object.getTitle() != null && !object.getTitle().isEmpty()) {
			book.setTitle(object.getTitle());
		}

		Book updatedBook = bookRepository.save(book);
		return bookMapper.toDTO(updatedBook);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if (!bookRepository.existsById(id)) {
			throw new IllegalArgumentException("Libro no encontrado.");

		}

		bookRepository.deleteById(id);

	}

	public Book getBookbyId(Long id) {
		return bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("El libo no existe."));
	}

	private void validateMandatoryFields(Book book) {
		if (book.getAutor() == null || book.getAutor().isEmpty()) {
			throw new IllegalArgumentException(AUTHOR_REQUIRED);
		}
		if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
			throw new IllegalArgumentException(ISBN_REQUIRED);
		}
		if (book.getStock() < 0) {
			throw new IllegalArgumentException(STOCK_POSITIVE);
		}
		if (book.getTitle() == null || book.getTitle().isEmpty()) {
			throw new IllegalArgumentException(TITLE_REQUIRED);
		}
	}

}
