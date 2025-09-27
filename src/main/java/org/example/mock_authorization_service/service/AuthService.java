package org.example.mock_authorization_service.service;

import org.example.mock_authorization_service.model.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<User> getUser(String login);
    ResponseEntity<String> addUser(User user);
}
