package com.knkweb.sdjpajdbc.dao;

import com.knkweb.sdjpajdbc.domain.Author;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class AuthorDaoImpl implements AuthorDao {
    private final DataSource dataSource;

    public AuthorDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Author getByName(String firstName, String lastName)  {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;


        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM author WHERE " +
                    "first_name = ? AND last_name = ?");
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                return getAuthorFromResultSet(resultSet);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(resultSet, null, preparedStatement, connection);
        }

        return null;
    }


    @Override
    public Author getById(Long id) {
        Connection connection = null;
//        Statement statement = null;
        PreparedStatement preparedStatement =null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
//            statement = connection.createStatement();
//            resultSet = statement.executeQuery("SELECT * FROM bookdb2.author WHERE id = " + id);
            preparedStatement = connection.prepareStatement("SELECT * FROM author WHERE id = ?");
            preparedStatement.setLong(1,id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getAuthorFromResultSet(resultSet);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeAll(resultSet, null, preparedStatement, connection);
        }
        return null;
    }

    @Override
    public Author saveNewAuthor(Author author) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement =null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement("INSERT INTO author (first_name, " +
                    "last_name) VALUES (?,?)");
            preparedStatement.setString(1,author.getFirstName());
            preparedStatement.setString(2,author.getLastName());
            preparedStatement.execute();

            resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");
            if (resultSet.next()) {
                Long id = resultSet.getLong(1);
                return this.getById(id);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeAll(resultSet, statement, preparedStatement, connection);
        }
        return null;
    }

    @Override
    public Author updateAuthor(Author author) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement =null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement("UPDATE author SET first_name =?, " +
                    "last_name = ? WHERE id = ?");
            preparedStatement.setString(1,author.getFirstName());
            preparedStatement.setString(2,author.getLastName());
            preparedStatement.setLong(3, author.getId());
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeAll(resultSet, statement, preparedStatement, connection);
        }
        return this.getById(author.getId());
    }

    @Override
    public void deleteAuthorById(Author author) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement =null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement("DELETE FROM author WHERE id = ?");
            preparedStatement.setLong(1, author.getId());
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeAll(resultSet, statement, preparedStatement, connection);
        }
    }

    private Author getAuthorFromResultSet(ResultSet resultSet) throws SQLException {
        return Author.builder()
                .id(resultSet.getLong("id"))
                .lastName(resultSet.getString("last_name"))
                .firstName(resultSet.getString("first_name"))
                .build();
    }

    private void closeAll(ResultSet resultSet, Statement statement, PreparedStatement preparedStatement, Connection connection) {
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
}
