package com.xly.mall.common.base;

import com.xly.mall.common.base.result.CommonUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLUtil {
    public URLUtil() {
    }

    public static final String getURIWithoutSuffix(String uri) {
        if (uri != null && !"".equals(uri.trim())) {
            int pointIndex = uri.indexOf(".");
            return pointIndex == -1 ? uri : uri.substring(0, pointIndex);
        } else {
            return uri;
        }
    }

    public static final String getURISuffix(String uri) {
        if (uri != null && !"".equals(uri.trim())) {
            int pointIndex = uri.indexOf(".");
            return pointIndex == -1 ? "" : uri.substring(pointIndex);
        } else {
            return uri;
        }
    }

    public static final String getURIWithoutSuffix(String url, String contextPath) {
        if (url != null && !"".equals(url.trim())) {
            int contextPathPonit = url.indexOf(contextPath);
            if (contextPathPonit != -1) {
                url = url.substring(contextPathPonit);
            }

            int pointIndex = url.indexOf(".");
            return pointIndex == -1 ? url : url.substring(0, pointIndex);
        } else {
            return url;
        }
    }

    public static final boolean isAjaxUrl(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        if (StringUtils.isNotBlank(accept) && (accept.contains("application/json") || accept.contains("application/jsonp"))) {
            return true;
        } else {
            String uri = request.getRequestURI();
            int suffixIndex = uri.lastIndexOf(".");
            String format;
            if (suffixIndex != -1) {
                format = uri.substring(suffixIndex + 1);
                if ("json".equals(format) || "jsonp".equals(format)) {
                    return true;
                }
            }

            format = request.getParameter("format");
            if (!"json".equals(format) && !"jsonp".equals(format)) {
                String ajaxHeader = request.getHeader("X-Requested-With");
                return StringUtils.isNotBlank(ajaxHeader) && "XMLHttpRequest".equalsIgnoreCase(ajaxHeader);
            } else {
                return true;
            }
        }
    }

    public static final boolean isJsonp(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        if (StringUtils.isNotBlank(accept) && accept.contains("application/jsonp")) {
            return true;
        } else {
            String format = request.getParameter("format");
            if ("jsonp".equals(format)) {
                return true;
            } else {
                String uri = request.getRequestURI();
                int suffixIndex = uri.lastIndexOf(".");
                if (suffixIndex != -1) {
                    String suffix = uri.substring(suffixIndex + 1);
                    if ("jsonp".equals(suffix)) {
                        return true;
                    }
                }

                return false;
            }
        }
    }

    public static String getClientAddr(HttpServletRequest request) {
        String str = request.getHeader("X-Forwarded-For");
        if (str == null || str.length() == 0 || "unknown".equalsIgnoreCase(str)) {
            str = request.getHeader("X-Real-IP");
        }

        if (str == null || str.length() == 0 || "unknown".equalsIgnoreCase(str)) {
            str = request.getHeader("Proxy-Client-IP");
        }

        if (str == null || str.length() == 0 || "unknown".equalsIgnoreCase(str)) {
            str = request.getHeader("WL-Proxy-Client-IP");
        }

        if (str == null || str.length() == 0 || "unknown".equalsIgnoreCase(str)) {
            str = request.getHeader("HTTP_CLIENT_IP");
        }

        if (str == null || str.length() == 0 || "unknown".equalsIgnoreCase(str)) {
            str = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (str == null || str.length() == 0 || "unknown".equalsIgnoreCase(str)) {
            str = request.getRemoteAddr();
        }

        if (str != null && str.indexOf(",") != -1) {
            str = str.substring(str.lastIndexOf(",") + 1, str.length()).trim();
        }

        return String.valueOf(str);
    }

    public static String environmentUrl(String url) {
        if (url == null) {
            throw new com.xly.mall.common.base.SystemException("输入的url为空");
        } else {
            String domain;
            String http;
            String www;
            String uri;
            if (CommonUtil.isPreEnvironment()) {
                domain = getDomain(url);
                http = getHttp(url);
                www = getWWW(url);
                uri = getURI(domain, url);
                if (!uri.equals(url)) {
                    url = http + www + "pre" + domain + uri;
                }
            } else if (CommonUtil.isGrayEnvironment()) {
                domain = getDomain(url);
                http = getHttp(url);
                www = getWWW(url);
                uri = getURI(domain, url);
                if (!uri.equals(url)) {
                    url = http + www + "gray" + domain + uri;
                }
            }

            return url;
        }
    }

    private static String getDomain(String url) {
        String domain = null;
        Pattern p = Pattern.compile("(?<=http(s)?://)[^.].*?\\.(com|cn|net|org|biz|info|cc|tv)", 2);
        Matcher matcher = p.matcher(url);
        if (matcher.find()) {
            domain = matcher.group();
        }

        if (domain == null) {
            domain = "";
        } else {
            String www = "www.";
            int index = domain.indexOf("www.");
            if (index != -1) {
                domain = domain.substring(index + www.length());
            }
        }

        return domain;
    }

    private static String getHttp(String url) {
        String http = "";
        if (url.indexOf("http://") != -1) {
            http = "http://";
        } else if (url.indexOf("https://") != -1) {
            http = "https://";
        }

        return http;
    }

    private static String getWWW(String url) {
        String www = "";
        if (url.indexOf("www.") != -1) {
            www = "www.";
        }

        return www;
    }

    private static String getURI(String domain, String url) {
        String uri = "";
        if (StringUtils.isNotBlank(domain)) {
            int index = url.indexOf(domain);
            if (index != -1) {
                uri = url.substring(index + domain.length());
            }
        } else {
            uri = url;
        }

        return uri;
    }

    public static void main(String[] args) {
        String url = "http://payment.berbon.com/trade/callback/sztfBalancePay";
        String grayUrl = environmentUrl(url);
        System.out.println(grayUrl);
    }
}
