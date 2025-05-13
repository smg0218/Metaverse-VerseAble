package com.deeppoem.verseable.api.user.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class RegistRequestDTO {
    @NotBlank(message = "ID는 필수로 입력해야 합니다!")
    private String id;
    @NotBlank(message = "PW는 필수로 입력해야 합니다!")
    private String pw;
    @NotBlank(message = "닉네임은 필수로 입력해야 합니다!")
    private String nickname;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RegistRequestDTO that = (RegistRequestDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(pw, that.pw) && Objects.equals(nickname, that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pw, nickname);
    }

    @Override
    public String toString() {
        return "RegistRespoinseDTO{" +
                "id='" + id + '\'' +
                ", pw='" + pw + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
