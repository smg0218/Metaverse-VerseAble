package com.deeppoem.verseable.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "RESULTS")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESULT_ID")
    private Long resultId;

    @Column(name = "RESULT_PATH")
    private String resultPath;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultText) {
        this.resultPath = resultText;
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
                ", resultPath='" + resultPath +
                '}';
    }
}
