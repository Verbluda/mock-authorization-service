package org.example.mock_authorization_service.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String login) { super("No user: " + login); }
}
