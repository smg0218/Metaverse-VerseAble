package com.deeppoem.verseable.api.result.dto.response;

public class ResultResponseMessageDTO {
    private String message;
    private ResultResponseDTO resultResponse;
    private String resultShare;

    protected ResultResponseMessageDTO() {}

    public ResultResponseMessageDTO(ResultResponseDTO resultResponse) {
        this.resultResponse = resultResponse;
    }

    public ResultResponseMessageDTO(String message) {
        this.message = message;
    }

    public void setResultShare(String resultShare) {
        this.resultShare = resultShare;
    }

    public String getResultShare() {
        return resultShare;
    }

    public String getMessage() {
        return message;
    }

    public ResultResponseDTO getResultResponse() {
        return resultResponse;
    }
}
