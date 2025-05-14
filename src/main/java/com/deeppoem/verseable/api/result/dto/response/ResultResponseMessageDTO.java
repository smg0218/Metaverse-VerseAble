package com.deeppoem.verseable.api.result.dto.response;

public class ResultResponseMessageDTO {
    private String message;
    private ResultResponseDTO resultResponse;

    protected ResultResponseMessageDTO() {}

    public ResultResponseMessageDTO(ResultResponseDTO resultResponse) {
        this.resultResponse = resultResponse;
    }

    public ResultResponseMessageDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public ResultResponseDTO getResultResponse() {
        return resultResponse;
    }
}
