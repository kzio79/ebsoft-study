package com.study.backend.utils;

import lombok.Data;

@Data
public class CustomResponse {

    private int resultCode;
    private String resultMessage;
    private Object data;

    public CustomResponse(ResultCode resultCode, Object data) {
        this.resultCode = resultCode.getCode();
        this.resultMessage = resultCode.getMessage();
        this.data = data;
    }
}
