package org.example.mock_authorization_service;

import org.example.mock_authorization_service.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDateTime;

@Component
public class DataBaseWorker {

    private final String url;
    private final String user;
    private final String password;

    public DataBaseWorker(
            @Value("${db.url}") String url,
            @Value("${db.user}") String user,
            @Value("${db.password}") String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }


    public User findUserByLogin(String login) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String sql = "SELECT p.login, p.password, p.date, e.email " +
                "FROM passwords p" +
                " JOIN emails e ON e.login = p.login " +
                "WHERE p.login = '" + login + "'";
        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                User user = new User();
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setDate(resultSet.getObject("date", LocalDateTime.class));
                user.setEmail(resultSet.getString("email"));
                return user;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("error during searching by login", e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException("some resources was not closed properly", e);
            }
        }
    }

    public int insertUser(User user) {
        String insertQueryForPasswords = "INSERT INTO passwords (login, password, date) VALUES (?, ?, ?)";
        String insertQueryForEmails = "INSERT INTO emails (login, email) VALUES (?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement1 = connection.prepareStatement(insertQueryForPasswords);
             PreparedStatement preparedStatement2 = connection.prepareStatement(insertQueryForEmails)) {

            connection.setAutoCommit(false);
            int rowsCount = 0;

            preparedStatement1.setString(1, user.getLogin());
            preparedStatement1.setString(2, user.getPassword());
            preparedStatement1.setTimestamp(3, Timestamp.valueOf(user.getDate()));
            rowsCount += preparedStatement1.executeUpdate();

            preparedStatement2.setString(1, user.getLogin());
            preparedStatement2.setString(2, user.getEmail());
            rowsCount += preparedStatement2.executeUpdate();

            connection.commit();
            return rowsCount;
        } catch (SQLException e) {
            throw new RuntimeException("inserting user failed", e);
        }
    }
}
