package org.example.mock_authorization_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mock_authorization_service.DataBaseWorker;
import org.example.mock_authorization_service.exception.UserNotFoundException;
import org.example.mock_authorization_service.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final DataBaseWorker dataBaseWorker;

    @GetMapping("/user/{login}")
    public ResponseEntity<User> getUser(@PathVariable String login) {
        randomDelay();
        User user = dataBaseWorker.findUserByLogin(login);
        if (user == null) {throw new UserNotFoundException(login);}
        return ResponseEntity.ok(user);
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@Valid @RequestBody User user) {
        randomDelay();
        int rowsCount = dataBaseWorker.insertUser(user);
        return ResponseEntity.ok("User " + user + " was successfully added to Database. " + rowsCount + " lines were updated");
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
