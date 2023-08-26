package com.knkweb.sdjpajdbc.dao;

import com.knkweb.sdjpajdbc.domain.Author;

import java.sql.SQLException;

public interface AuthorDao {
    Author getByName(String firstName, String lastName);
    Author getById(Long l);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById(Author author);
}
