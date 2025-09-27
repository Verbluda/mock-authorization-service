package org.example.mock_authorization_service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

    @NotBlank(message = "login must not be blank")
    private String login;
    @NotBlank(message = "password must not be blank")
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonSetter(nulls = Nulls.SKIP)
    private LocalDateTime date = LocalDateTime.now();
    @NotBlank(message = "email must not be blank")
    @Email(message = "wrong email format")
    private String email;
}
