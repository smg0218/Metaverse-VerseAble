package com.deeppoem.verseable.api.result.dto.response;

public class ResultResponseMessageDTO {
    private String message;
    private ResultResponseDTO resultResponse;
    private String imagePath;

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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
