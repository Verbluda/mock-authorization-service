package org.example.mock_authorization_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.mock_authorization_service.exception.UserNotFoundException;
import org.example.mock_authorization_service.exception.WrongJsonException;
import org.example.mock_authorization_service.model.User;
import org.example.mock_authorization_service.repository.UserRepository;
import org.example.mock_authorization_service.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<User> getUser(String login) {
        User user = userRepository.findUserByLogin(login);
        if (user == null) {throw new UserNotFoundException(login);}
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<String> addUser(User user) {
        int rowsCount = userRepository.insertUser(user);
        return ResponseEntity.ok("User " + user + " was successfully added to Database. " + rowsCount + " lines were updated");
    }
}
