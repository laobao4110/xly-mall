package com.xly.mall.common.base.result;

import com.github.pagehelper.util.StringUtil;
import com.xly.mall.common.base.user.SessionManagement;
import com.xly.mall.common.constant.CommonConstant;
import com.xly.mall.common.domain.user.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SessionFilter  implements Filter {

    /**
     * 日志
     */
    private final Logger log = LoggerFactory.getLogger(getClass());
    /**
     * 需要排除的页面
     */
    private String excludedPages;

    private String[] excludedPageArray;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludedPages = filterConfig.getInitParameter("excludedPages");
        if (!StringUtil.isEmpty(excludedPages)) {
            excludedPageArray = excludedPages.split(",");
        }
        return;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        boolean isExcludedPage = false;
        String requestPath = request.getServletPath();
        if (requestPath.startsWith("/html/user/randomCode") || requestPath.endsWith(".js") || requestPath.endsWith(".css") ||
                requestPath.endsWith(".png") || requestPath.endsWith(".jpg") || requestPath.endsWith(".jpeg")||requestPath.startsWith("/api/product")) {
            isExcludedPage = true;
        } else {
            for (String page : excludedPageArray) {//判断是否在过滤url之外
                Pattern pattern = Pattern.compile(page);
                Matcher matcher = pattern.matcher(requestPath);
                if ((requestPath.endsWith(page) || matcher.find())) {
                    isExcludedPage = true;
                    break;
                }
            }
        }
        if (isExcludedPage) {//在过滤url之外
            filterChain.doFilter(servletRequest, servletResponse);
        } else {//不在过滤url之外，判断session是否存在
            HttpSession session = request.getSession();
            if (session != null && session.getAttribute(CommonConstant.ERP_USER_SESSION_KEY) != null) {
                Integer loginId = ((User) session.getAttribute(CommonConstant.ERP_USER_SESSION_KEY)).getUserId();
                if (SessionManagement.getInstance().hasSession(loginId.toString())) {
                    filterChain.doFilter(request, servletResponse);
                    return;
                } else {
                    session.invalidate();
                }
            }

            log.info("用户session已失效,请求地址" + requestPath);
            String type = request.getHeader("X-Requested-With");
            if ("XMLHttpRequest".equalsIgnoreCase(type)) {// AJAX REQUEST PROCESS
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                response.setHeader("sessionStatus", "timeout");
                response.setHeader("path", request.getContextPath());
                PrintWriter out = response.getWriter();
                out.write("timeout");
//                out.close();
            } else {
                String sss = request.getContextPath() + "/pageController/toLoginPage";
                ((HttpServletResponse) servletResponse).sendRedirect(request.getContextPath() + "/pageController/toLoginPage");
//                    filterChain.doFilter(request, servletResponse);
            }

        }
    }

    @Override
    public void destroy() {
        return;
    }

    private void doRefuse() {

    }
}
