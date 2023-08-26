package com.knkweb.sdjpajdbc;

import com.knkweb.sdjpajdbc.domain.Author;
import com.knkweb.sdjpajdbc.domain.Book;
import com.knkweb.sdjpajdbc.repository.AuthorRepository;
import com.knkweb.sdjpajdbc.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MySQLIntegrationTest {
    public static final long ID = 1L;
    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void testBookRepository() {
        Book bookSaved = bookRepository.save(Book.builder().id(ID).build());
        assertThat(bookSaved.getId()).isEqualTo(ID);

        Book bookFetched = bookRepository.findById(ID).get();
        assertThat(bookFetched.getId()).isEqualTo(ID);
    }

    @Test
    void testAuthorRepository() {
        Author authorSaved = authorRepository.save(Author.builder().id(ID).build());
        assertThat(authorSaved.getId()).isEqualTo(ID);

        Author authorFetched = authorRepository.findById(ID).get();
        assertThat(authorFetched.getId()).isEqualTo(ID);
    }
}
