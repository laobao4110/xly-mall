package com.xly.mall.common.base.result;

import com.xly.mall.common.base.URLUtil;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class CommonMethodReturnValueHandler implements HandlerMethodReturnValueHandler {


//    public CommonMethodReturnValueHandler() {
//    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        //判断返回值是否是Result对象（不是这个类型直接跳转额所以返回其他；类型是可以直接跳转的额）
        return Result.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public void handleReturnValue(Object returnVale, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        if (returnVale != null) {
            try {
                Result result = (Result) returnVale;
                HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
                HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
                response.setCharacterEncoding("UTF-8");
                StringBuffer responseSb = new StringBuffer();
                String resultJsonString = Result.toJSONString(result);
                String responseStr;
                if(URLUtil.isAjaxUrl(request)) {
                    if(URLUtil.isJsonp(request)){
                        response.setContentType("application/javascript;charset=UTF-8");
                        responseStr = request.getParameter("callback");
                        responseSb.append(responseStr).append("(").append(resultJsonString).append(")");
                        request.setAttribute("callback",responseStr);
                    }else {
                        response.setContentType("application/json;charset=UTF-8");
                        responseSb.append(resultJsonString);
                    }
                }else {
                    response.setContentType("text/html;charset=UTF-8");
                    responseSb.append(resultJsonString);
                }

                request.setAttribute("result",result);
                request.setAttribute("encrpy",true);
                responseStr = responseSb.toString();
                PrintWriter writer = null;
                try {
                    writer = response.getWriter();
                } catch (Exception var13) {
                    ;
                }

                if (writer != null) {
                    writer.write(responseStr);
                }
            } catch (Exception e) {

            }
            //不向下传递，处理结束
            mavContainer.setRequestHandled(true);

        }
    }
}
