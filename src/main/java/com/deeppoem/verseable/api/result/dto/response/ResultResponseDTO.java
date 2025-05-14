package com.deeppoem.verseable.api.result.dto.response;

import com.deeppoem.verseable.model.entity.Result;

public class ResultResponseDTO {
    private Long resultId;
    private String resultPath;

    protected ResultResponseDTO() {}

    public ResultResponseDTO(Result result) {
        this.resultId = result.getResultId();
        this.resultPath = result.getResultPath();
    }

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public String getResultPath() {
        return resultPath;
    }
    
    // 이전 메소드명 호환성 유지
    public String getResultText() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }
}