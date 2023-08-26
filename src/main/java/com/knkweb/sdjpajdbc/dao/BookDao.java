package com.knkweb.sdjpajdbc.dao;

import com.knkweb.sdjpajdbc.domain.Book;

public interface BookDao {

    Book findById(long id);

    Book findByTitle(String title);

    Book saveNewBook(Book book);

    void deleteBookById(Long id);

    Book updateBook(Book book);
}
