package com.deeppoem.verseable.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "RESULTS")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESULT_ID")
    private Long resultId;

    @Column(name = "RESULT_TEXT", columnDefinition = "TEXT")
    private String resultText;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Result{" +
                "resultId=" + resultId +
                ", resultText='" + resultText +
                '}';
    }
}
