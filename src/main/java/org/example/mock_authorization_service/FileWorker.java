package org.example.mock_authorization_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.mock_authorization_service.model.User;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Random;

@Component
public class FileWorker {

    public void writeUserToFile(User user) {
        String filePath = "list-of-users.txt";
        String userJson = String.format("{\"login\":\"%s\",\"password\":\"%s\",\"date\":\"%s\",\"email\":\"%s\"}",
                user.getLogin(), user.getPassword(), user.getDate(), user.getEmail());
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {
            bufferedWriter.write(userJson);
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException("ошибка при записи в файл пользователя с логином " + user.getLogin() + ". " + e.getMessage());
        }
    }

    public User readRandomUser() {
        String filePath = "random-users.txt";
        int randomNumber = new Random().nextInt(1, 11);
        String randomUserInfo = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            int lineNumber = 0;
            while ((currentLine = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber == randomNumber) {
                    randomUserInfo = currentLine;
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("ошибка при чтении файла: " + e.getMessage());
        }

        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            return mapper.readValue(randomUserInfo, User.class);
        } catch (IOException e) {
            throw new RuntimeException("не удалось распарсить JSON: " + randomUserInfo, e);
        }
    }
}
