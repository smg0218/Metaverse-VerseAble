package com.deeppoem.verseable.api.user.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class LoginRequestDTO {
    @NotBlank(message = "ID는 필수로 입력해야 합니다!")
    private String id;
    @NotBlank(message = "PW는 필수로 입력해야 합니다!")
    private String pw;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LoginRequestDTO that = (LoginRequestDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(pw, that.pw);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pw);
    }

    @Override
    public String toString() {
        return "LoginRequestDTO{" +
                "id='" + id + '\'' +
                ", pw='" + pw + '\'' +
                '}';
    }
}
