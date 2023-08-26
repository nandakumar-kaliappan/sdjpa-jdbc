package com.knkweb.sdjpajdbc.dao;

import com.knkweb.sdjpajdbc.domain.Book;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class BookDaoImpl implements BookDao {

    private final DataSource dataSource;

    public BookDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Book findById(long id) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement("SELECT * FROM book WHERE id = ?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return getBookFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(connection, statement, preparedStatement, resultSet);
        }

        return null;
    }

    @Override
    public Book findByTitle(String title) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement("SELECT * FROM book WHERE title = ?");
            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return getBookFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(connection, statement, preparedStatement, resultSet);
        }

        return null;
    }

    @Override
    public Book saveNewBook(Book book) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement("INSERT INTO book (isbn, " +
                    "publisher, title, author_id) VALUES (?,?,?,?)");
            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.setString(2, book.getPublisher());
            preparedStatement.setString(3, book.getTitle());
            preparedStatement.setLong(4, book.getAuthorId());
            preparedStatement.execute();

            resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");
            if(resultSet.next()){
                return findById(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(connection, statement, preparedStatement, resultSet);
        }

        return null;
    }

    @Override
    public void deleteBookById(Long id) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement("DELETE FROM book WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(connection, statement, preparedStatement, resultSet);
        }


    }

    @Override
    public Book updateBook(Book book) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement("UPDATE book SET isbn =?, publisher =" +
                    " ?, title = ?, author_id = ? where id = ?");
            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.setString(2, book.getPublisher());
            preparedStatement.setString(3, book.getTitle());
            preparedStatement.setLong(4, book.getAuthorId());
            preparedStatement.setLong(5, book.getId());
            preparedStatement.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(connection, statement, preparedStatement, resultSet);
        }

        return this.findById(book.getId());
    }

    private void closeAll(Connection connection, Statement statement, PreparedStatement preparedStatement, ResultSet resultSet) {
        try{
            if(resultSet != null){
                resultSet.close();
            }
            if(statement != null){
                statement.close();
            }
            if(preparedStatement != null){
                preparedStatement.close();
            }
            if(connection != null){
                connection.close();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        return Book.builder()
                .id(resultSet.getLong("id"))
                .title(resultSet.getString("title"))
                .isbn(resultSet.getString("isbn"))
                .publisher(resultSet.getString("publisher"))
                .authorId(resultSet.getLong("author_id"))
                .build();
    }
}
