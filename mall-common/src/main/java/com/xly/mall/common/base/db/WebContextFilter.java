package com.xly.mall.common.base.db;

import com.xly.mall.common.base.LogUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



/**
 * 将request和response注册到WebContext中，结束时清除
 *
 * @author lxzl
 *
 */
public class WebContextFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest heq = (HttpServletRequest) request;
            HttpServletResponse hsr = (HttpServletResponse) response;
            WebContext.registry(heq, hsr);
            filterChain.doFilter(request, response);
        } finally {
            WebContext.release();
            InitializeService.clearDynamicSources();
            LogUtil.clean();
        }
    }

    public void init(FilterConfig arg0) throws ServletException {
    }

}

