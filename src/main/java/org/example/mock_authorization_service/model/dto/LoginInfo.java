package org.example.mock_authorization_service.model.dto;

public class LoginInfo {

    private String login;
    private String password;
    private String date;

    public LoginInfo(String password, String login) {
        this.password = password;
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
