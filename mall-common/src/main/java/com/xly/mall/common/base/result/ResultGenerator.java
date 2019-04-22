package com.xly.mall.common.base.result;

import com.github.pagehelper.util.StringUtil;
import com.xly.mall.common.constant.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ResultGenerator {
    private static final String SUCCESS_CODE = ErrorCode.SUCCESS;
    private static final String BUSINESS_EXCEPTION = ErrorCode.BUSINESS_EXCEPTION;
    private static final String CLEAR = ErrorCode.CLEAR;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public Result generate(String code,Object object){
        Result result = null;
        if(SUCCESS_CODE.equals(code)){
            result =  new Result(code,ErrorCode.getMessage(code),true);
            result.setProperty("data",object);
        }else if(StringUtil.isEmpty(ErrorCode.getMessage(code))){
            result = new Result(BUSINESS_EXCEPTION,code,false);
        }else {
            result = new Result(code,ErrorCode.getMessage(code),false);
        }

        if(CLEAR.equals(code)){
            ErrorCode.clear(code);
        }
        return result;
    }

    public Result generateError(String code,String message){return new Result(code,message,false);}
    public Result generate (String code){return generate(code,"");}

    public Result generate(ServiceResult serviceResult) {
        String code = (String)serviceResult.getErrorCode();
        Object data = serviceResult.getResult();
        Object[] formatArgs = serviceResult.getFormatArgs();

        Result result = null;
        if(SUCCESS_CODE.equals(code)){
            result = new Result(SUCCESS_CODE,ErrorCode.getMessage(SUCCESS_CODE),true);
            result.setProperty("data",data);
        }else if(StringUtil.isEmpty(ErrorCode.getMessage(code))){
            if(null != data){
                result = new Result(BUSINESS_EXCEPTION,data.toString(),false);
            }else {
                result = new Result(BUSINESS_EXCEPTION,ErrorCode.getMessage(BUSINESS_EXCEPTION),false);
                log.error("ErrorCode Message Null" + code);
            }
        }else if(formatArgs==null || formatArgs.length == 0){
            result = new Result(code,ErrorCode.getMessage(code),false);
        }else {
            result = new Result(code,String.format(ErrorCode.getMessage(code),formatArgs),false);
        }

        if(CLEAR.equals(code)){
            ErrorCode.clear(CLEAR);
        }
        return result;
    }
}
