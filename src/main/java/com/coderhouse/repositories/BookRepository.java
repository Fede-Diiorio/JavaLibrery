package com.coderhouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderhouse.models.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

}
