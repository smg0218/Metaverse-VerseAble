package com.deeppoem.verseable.api.result.dto.response;

import java.util.List;

public class ResultResponseListDTO {
    private String message;
    private List<ResultResponseDTO> resultList;

    protected ResultResponseListDTO() {}

    public ResultResponseListDTO(List<ResultResponseDTO> resultList) {
        this.resultList = resultList;
    }

    public ResultResponseListDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public List<ResultResponseDTO> getResultList() {
        return resultList;
    }
}
