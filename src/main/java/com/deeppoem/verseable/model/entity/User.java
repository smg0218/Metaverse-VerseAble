package com.deeppoem.verseable.model.entity;

import com.deeppoem.verseable.api.user.dto.request.RegistResponseDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USER_PASSWORD")
    @Comment("유저의 패스워드")
    private String password;

    @Column(name = "USER_NAME")
    @Comment("유저의 닉네임")
    private String userName;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Comment("유저의 결과 목록")
    private List<Result> results;

    protected User() {}

    public User(RegistResponseDTO responseDTO, String pw) {
        this.userId = responseDTO.getId();
        this.password = pw;
        this.userName = responseDTO.getNickname();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(password, user.password) && Objects.equals(userName, user.userName) && Objects.equals(results, user.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, password, userName, results);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", results=" + results +
                '}';
    }
}
