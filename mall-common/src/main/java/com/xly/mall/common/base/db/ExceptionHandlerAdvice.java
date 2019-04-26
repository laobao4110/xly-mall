package com.xly.mall.common.base.db;


import com.xly.mall.common.base.StringUtil;
import com.xly.mall.common.base.result.ResultGenerator;
import com.xly.mall.common.constant.ErrorCode;
import com.xly.mall.common.domain.ConstantConfig;
import com.xly.mall.common.domain.base.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.PrintWriter;
import java.io.StringWriter;

/** 主要是控制台异常打印，不设置是没有控制台打印的
 * User : LiuKe
 * Date : 2016/12/30
 * Time : 16:37
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> exception(BusinessException businessException , WebRequest request) {
        StringWriter exceptionFormat=new StringWriter();
        businessException.printStackTrace(new PrintWriter(exceptionFormat,true));
//        log.error("ExceptionHandlerAdvice catch the BusinessException, ", businessException);
        if(StringUtil.isBlank(businessException.getMessage())){
            if(ConstantConfig.exceptionLogDetail!=null&&ConstantConfig.exceptionLogDetail){
                return new ResponseEntity<Object>(resultGenerator.generateError(ErrorCode.SYSTEM_ERROR,exceptionFormat.toString() ), HttpStatus.OK);
            }else{
                return new ResponseEntity<Object>(resultGenerator.generateError(ErrorCode.SYSTEM_ERROR,ErrorCode.getMessage(ErrorCode.SYSTEM_ERROR) ), HttpStatus.OK);
            }

        }else{
            String code = businessException.getCode();
            code = code==null?ErrorCode.BUSINESS_EXCEPTION:code;
            String description = businessException.getMessage();
            if(StringUtil.isNotBlank(description)){
                String msg = ErrorCode.getMessage(description);
                //获取到了错误码对应的描述，则返回错误码描述
                if(!StringUtil.isBlank(msg)){
                    return new ResponseEntity<Object>(resultGenerator.generateError(code,msg), HttpStatus.OK);
                }else{
                    return new ResponseEntity<Object>(resultGenerator.generateError(code,description), HttpStatus.OK);
                }

            }else{
                //先判断获得的code是否为错误码，如果不是错误码，直接把code字段当做描述处理
                String msg = ErrorCode.getMessage(code);
                if(!StringUtil.isBlank(msg)){
                    return new ResponseEntity<Object>(resultGenerator.generateError(code,msg), HttpStatus.OK);
                }else{
                    return new ResponseEntity<Object>(resultGenerator.generateError(ErrorCode.BUSINESS_EXCEPTION,code), HttpStatus.OK);
                }
            }
        }
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception exception , WebRequest request) {
        StringWriter exceptionFormat=new StringWriter();
        exception.printStackTrace(new PrintWriter(exceptionFormat,true));
        exception.printStackTrace();
        log.error("ExceptionHandlerAdvice catch the SystemException, ", exception);
        if(StringUtil.isEmpty(ErrorCode.getMessage(exception.getMessage()))){
            if(ConstantConfig.exceptionLogDetail!=null&&ConstantConfig.exceptionLogDetail){
                return new ResponseEntity<Object>(resultGenerator.generateError(ErrorCode.SYSTEM_ERROR,exceptionFormat.toString() ), HttpStatus.OK);
            }else{
                return new ResponseEntity<Object>(resultGenerator.generateError(ErrorCode.SYSTEM_ERROR,ErrorCode.getMessage(ErrorCode.SYSTEM_ERROR) ), HttpStatus.OK);
            }
        }else{
            return new ResponseEntity<Object>(resultGenerator.generate(exception.getMessage(),ErrorCode.getMessage(exception.getMessage()) ), HttpStatus.OK);
        }
    }
    @Autowired
    private ResultGenerator resultGenerator;
}
