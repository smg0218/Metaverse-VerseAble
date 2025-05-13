package com.deeppoem.verseable.api.user.dto.response;

import java.util.Objects;

public class LoginResponseDTO {
    private String message;
    private String id;
    private String nickname;

    protected LoginResponseDTO() {}

    public LoginResponseDTO(String message) {
        this.message = message;
    }

    public LoginResponseDTO(String id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LoginResponseDTO that = (LoginResponseDTO) o;
        return Objects.equals(message, that.message) && Objects.equals(id, that.id) && Objects.equals(nickname, that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, id, nickname);
    }

    @Override
    public String toString() {
        return "LoginResponseDTO{" +
                "message='" + message + '\'' +
                ", id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
