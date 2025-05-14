package com.deeppoem.verseable.api.result.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class ResultRequestDTO {
    @NotBlank(message = "유저의 아이디는 필수값입니다!")
    private String id;
    private MultipartFile multipartFile;

    protected ResultRequestDTO() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    @Override
    public String toString() {
        return "ResultRequestDTO{" +
                "id='" + id + '\'' +
                ", multipartFile=" + multipartFile +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ResultRequestDTO that = (ResultRequestDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(multipartFile, that.multipartFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, multipartFile);
    }
}
