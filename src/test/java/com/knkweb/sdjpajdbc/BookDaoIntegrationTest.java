package com.knkweb.sdjpajdbc;

import com.knkweb.sdjpajdbc.dao.BookDao;
import com.knkweb.sdjpajdbc.dao.BookDaoImpl;
import com.knkweb.sdjpajdbc.domain.Book;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(BookDaoImpl.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookDaoIntegrationTest {

    @Autowired
    BookDao bookDao;

    @Test
    void updateBook() {
//        given
        Book book =Book.builder()
                .title("panjali sabatham 2")
                .isbn("no isbn")
                .publisher("TN Govt")
                .authorId(1L)
                .build();
        Book bookSaved = bookDao.saveNewBook(book);

//        when : updating
        bookSaved.setIsbn("56734765287");
        Book bookUpdated = bookDao.updateBook(bookSaved);

//        then
        assertThat(bookUpdated).isNotNull();
        assertThat(bookSaved).isEqualTo(bookUpdated);

        Book bookFound = bookDao.findById(bookUpdated.getId());

        assertThat(bookFound).isNotNull();
        assertThat(bookFound).isEqualTo(bookUpdated);

    }

    @Test
    @Order(4)
    void deleteBookById() {

//        given
        Book book =Book.builder()
                .title("panjali sabatham 2")
                .isbn("no isbn")
                .publisher("TN Govt")
                .authorId(1L)
                .build();
        Book bookSaved = bookDao.saveNewBook(book);

//        when: Deleting
        bookDao.deleteBookById(bookSaved.getId());

//        then
        Book bookFound = bookDao.findById(bookSaved.getId());
        assertThat(bookFound).isNull();



    }

    @Test
    @Order(3)
    void saveNewBook() {
        Book book =Book.builder()
                    .title("panjali sabatham")
                    .isbn("no isbn")
                    .publisher("TN Govt")
                    .authorId(1L)
                    .build();

        Book bookSaved = bookDao.saveNewBook(book);

        assertThat(bookSaved).isNotNull();
        assertThat(bookSaved.getId()).isNotNegative();

        Book bookFound = bookDao.findById(bookSaved.getId());

        assertThat(bookFound).isNotNull();
        assertThat(bookFound).isEqualTo(bookSaved);

    }

    @Test
    @Order(2)
    void findByTitle() {
        Book bookFound = bookDao.findById(1L);
        assertThat(bookFound).isNotNull();

        Book bookFoundByTitle = bookDao.findByTitle(bookFound.getTitle());

        assertThat(bookFoundByTitle).isNotNull();
        assertThat(bookFoundByTitle.getId()).isEqualTo(1L);
        assertThat(bookFoundByTitle.getTitle()).isEqualTo(bookFound.getTitle());

    }

    @Test
    @Order(1)
    void findByid() {
        Book bookFound = bookDao.findById(1L);

        assertThat(bookFound).isNotNull();
        assertThat(bookFound.getId()).isEqualTo(1L);
    }
}
