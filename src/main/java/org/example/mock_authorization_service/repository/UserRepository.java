package org.example.mock_authorization_service.repository;

import org.example.mock_authorization_service.model.User;

public interface UserRepository {
    User findUserByLogin(String login);
    int insertUser(User user);
}
