package com.knkweb.sdjpajdbc;

import com.knkweb.sdjpajdbc.dao.AuthorDao;
import com.knkweb.sdjpajdbc.dao.AuthorDaoImpl;
import com.knkweb.sdjpajdbc.domain.Author;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ComponentScan({"com.knkweb.sdjpajdbc.dao"})  // Alternative way is using @Import
@Import(AuthorDaoImpl.class)
public class AuthorDaoIntegrationTest {

    @Autowired
    AuthorDao authorDao;

    @Test
    @Order(5)
    void testDeleteById() {
        Author author = Author.builder().firstName("Nandakumat").lastName("kaliappan").build();
        Author authorSaved = authorDao.saveNewAuthor(author);

//        Delete
        authorDao.deleteAuthorById(authorSaved);

        Author authorDeleted = authorDao.getById(authorSaved.getId());

        assertThat(authorDeleted).isNull();

    }

    @Test
    @Order(4)
    void testUpdateAuthor() {
        Author author = Author.builder().firstName("Nandakumat").lastName("kaliappan").build();
        Author authorSaved = authorDao.saveNewAuthor(author);

        authorSaved.setFirstName("Nandakumar");
//        Before Update
        assertThat(authorDao.getById(authorSaved.getId()).getFirstName()).isNotEqualTo(authorSaved.getFirstName());

//        Update
        Author authorupdated = authorDao.updateAuthor(authorSaved);

//        After Update
        assertThat(authorupdated).isNotNull();
        assertThat(authorupdated.getFirstName()).isEqualTo(authorSaved.getFirstName());
        assertThat(authorDao.getById(authorSaved.getId()).getFirstName()).isEqualTo(authorSaved.getFirstName());
    }

    @Test
    @Order(3)
    void testSaveNewAuthor() {
        Author author = Author.builder().firstName("Nandakumat").lastName("kaliappan").build();
        Author authorSaved = authorDao.saveNewAuthor(author);

        assertThat(authorSaved).isNotNull();
        assertThat(authorSaved.getId()).isNotNegative();

    }

    @Test
    @Order(2)
    void testGetAuthorByName() {
        Author author = authorDao.getById(1L);
        Author author1 = authorDao.getByName(author.getFirstName(),author.getLastName());
        assertThat(author1).isNotNull();
        assertThat(author1.getId()).isEqualTo(author.getId());


    }

    @Test
    @Order(1)
    void testGetAuthorById() {
        Author author = authorDao.getById(1L);
        assertThat(author).isNotNull();
        System.out.println(author);
    }
}

