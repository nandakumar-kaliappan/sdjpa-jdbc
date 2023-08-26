package com.knkweb.sdjpajdbc.repository;

import com.knkweb.sdjpajdbc.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;



public interface BookRepository extends JpaRepository<Book, Long> {
}
