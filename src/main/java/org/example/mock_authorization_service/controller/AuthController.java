package org.example.mock_authorization_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mock_authorization_service.model.User;
import org.example.mock_authorization_service.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/user/{login}")
    public ResponseEntity<User> getUser(@PathVariable String login) {
        randomDelay();
        return authService.getUser(login);
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@Valid @RequestBody User user) {
        randomDelay();
        return authService.addUser(user);
    }

    private void randomDelay() {
        long delay = ThreadLocalRandom.current().nextLong(1000, 2001); // 1000–2000 мс
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
