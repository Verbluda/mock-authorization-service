package org.example.mock_authorization_service.controller;

import org.example.mock_authorization_service.model.dto.LoginRequestDto;
import org.example.mock_authorization_service.model.dto.LoginResponseDto;
import org.example.mock_authorization_service.model.dto.StatusResponseDto;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api")
public class AuthController {

    @GetMapping("/status")
    public StatusResponseDto getStatus() {
        randomDelay();
        return new StatusResponseDto("Login1", "ok");
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequest) {
        randomDelay();
        String date = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return new LoginResponseDto(loginRequest.getLogin(), loginRequest.getPassword(), date);
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
