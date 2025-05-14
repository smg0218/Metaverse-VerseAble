package com.deeppoem.verseable.api.result.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class ResultRequestDTO {
    @NotBlank(message = "유저의 아이디는 필수값입니다!")
    private String id;
    @NotBlank(message = "결과 데이터는 필수값입니다!")
    private String resultData;

    protected ResultRequestDTO() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultData() {
        return resultData;
    }

    public void setResultData(String resultData) {
        this.resultData = resultData;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ResultRequestDTO that = (ResultRequestDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(resultData, that.resultData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, resultData);
    }

    @Override
    public String toString() {
        return "ResultRequestDTO{" +
                "id='" + id + '\'' +
                ", resultData='" + resultData + '\'' +
                '}';
    }
}
