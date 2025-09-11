package org.example.mock_authorization_service.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInfo {

    @NotBlank(message = "Login must not be blank")
    private String login;

    @NotBlank(message = "Password must not be blank")
    private String password;

    private String date;

    public LoginInfo(String password, String login) {
        this.password = password;
        this.login = login;
    }
}
