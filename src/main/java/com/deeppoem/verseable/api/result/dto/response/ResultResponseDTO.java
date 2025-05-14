package com.deeppoem.verseable.api.result.dto.response;

import com.deeppoem.verseable.model.entity.Result;

public class ResultResponseDTO {
    private Long resultId;
    private String resultText;

    protected ResultResponseDTO() {}

    public ResultResponseDTO(Result result) {
        this.resultId = result.getResultId();
        this.resultText = result.getResultText();
    }

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
}
