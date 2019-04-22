package com.xly.mall.common.base.result;

public class ServiceResult<String,RESULT> {

    public ServiceResult(){}

    public ServiceResult(String errorCode,RESULT result){
        this.errorCode = errorCode;
        this.result = result;
    }

    private String errorCode;

    private RESULT result;
    private Object[] formatArgs;

    public Object[] getFormatArgs() {
        return formatArgs;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode,Object... objects) {
        this.errorCode = errorCode;
        this.formatArgs = objects;
    }

    public RESULT getResult() {
        return result;
    }

    public void setResult(RESULT result) {
        this.result = result;
    }
}
