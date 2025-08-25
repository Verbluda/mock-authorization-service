package org.example.mock_authorization_service.model.dto;

public class StatusResponseDto {
    private String login;
    private String status;

    public StatusResponseDto(String login, String status) {
        this.login = login;
        this.status = status;
    }

    public String getLogin() {
        return login;
    }

    public String getStatus() {
        return status;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
