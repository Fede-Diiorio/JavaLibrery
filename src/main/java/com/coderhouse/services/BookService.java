package com.coderhouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.dtos.BookDTO;
import com.coderhouse.interfaces.DAOInterface;
import com.coderhouse.models.Book;
import com.coderhouse.repositories.BookRepository;

import jakarta.transaction.Transactional;

@Service
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
	@Transactional
	public BookDTO save(Book object) {
		Book saverdook = bookRepository.save(object);
		validateMandatoryFields(saverdook);
		return convertToDTO(saverdook);
	}

	@Override
	@Transactional
	public BookDTO update(Long id, Book object) throws Exception {
		Book book = getBookbyId(id);
		bookRepository.save(validateBookToUpdate(book));
		return convertToDTO(book);
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

	private BookDTO convertToDTO(Book book) {
		BookDTO bookDTO = new BookDTO(book.getId(), book.getTitle(), book.getAutor(), book.getIsbn(), book.getStock());
		return bookDTO;

	}

	private void validateMandatoryFields(Book book) {
		if (book.getAutor() == null || book.getAutor().isEmpty()) {
			throw new IllegalArgumentException("El autor del libro es obligatorio");
		}
		if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
			throw new IllegalArgumentException("El isbn del libro es obligatorio.");
		}
		if (book.getStock() < 0) {
			throw new IllegalArgumentException("El strocl no puede ser menor a cero.");
		}
		if (book.getTitle() == null || book.getIsbn().isEmpty()) {
			throw new IllegalArgumentException("El título del libro es obligatorio.");
		}
	}

	private Book validateBookToUpdate(Book book) {
		if (book.getAutor() != null && !book.getAutor().isEmpty()) {
			book.setAutor(book.getAutor());
		}
		if (book.getIsbn() != null && !book.getIsbn().isEmpty()) {
			book.setIsbn(book.getIsbn());
		}
		if (book.getStock() < 0) {
			book.setStock(book.getStock());
		}
		if (book.getTitle() != null && !book.getTitle().isEmpty()) {
			book.setTitle(book.getTitle());
		}

		return book;
	}

}
