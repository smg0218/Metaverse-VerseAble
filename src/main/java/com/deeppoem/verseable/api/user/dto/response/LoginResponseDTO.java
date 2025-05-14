package com.deeppoem.verseable.api.user.dto.response;

import java.util.Objects;

public class LoginResponseDTO {
    private String message;
    private String id;

    public LoginResponseDTO() {}

    public LoginResponseDTO(String message) {
        this.message = message;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LoginResponseDTO that = (LoginResponseDTO) o;
        return Objects.equals(message, that.message) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, id);
    }

    @Override
    public String toString() {
        return "LoginResponseDTO{" +
                "message='" + message + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
