package com.deeppoem.verseable.api.result.dto.response;

import com.deeppoem.verseable.model.entity.Result;

public class ResultResponseDTO {
    private Long resultId;
    private String resultShare;

    protected ResultResponseDTO() {}

    public ResultResponseDTO(Result result) {
        this.resultId = result.getResultId();
        this.resultShare = result.getResultPath();
    }

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public String getResultShare() {
        return resultShare;
    }

    public void setResultShare(String resultShare) {
        this.resultShare = resultShare;
    }
}
