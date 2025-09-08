package org.example.mock_authorization_service.controller;

import jakarta.validation.Valid;
import org.example.mock_authorization_service.model.LoginInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api")
public class AuthController {

    private static final List<byte[]> memoryLeak = new ArrayList<>();

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        randomDelay();
        return ResponseEntity.ok("{\"login\":\"Login1\",\"status\":\"ok\"}");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginInfo> login(@Valid @RequestBody LoginInfo loginInfo) {
        randomDelay();
        String date = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        loginInfo.setDate(date);
        return ResponseEntity.ok(loginInfo);
    }

    @GetMapping("/leak")
    public ResponseEntity<String> leakMemory() {
        memoryLeak.add(new byte[100 * 1024]);
        return ResponseEntity.ok("Утечка добавлена");
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
