package com.xly.mall.common.util;

/**
 * User : LiuKe
 * Date : 2016/11/12
 * Time : 15:03
 */

        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;

        import javax.servlet.http.HttpServletRequest;

/**
 * 常用获取客户端信息的工具
 *
 */
public final class NetworkUtil {
    /**
     * 日志
     */
    private final static Logger log = LoggerFactory.getLogger(NetworkUtil.class);

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     *
     * @param request
     * @return String
     * @throws java.io.IOException
     */
    public final static String getIpAddress(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");
        try{
            // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
            if (log.isInfoEnabled()) {
                log.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("Proxy-Client-IP");
                    if (log.isInfoEnabled()) {
                        log.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
                    }
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("WL-Proxy-Client-IP");
                    if (log.isInfoEnabled()) {
                        log.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
                    }
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_CLIENT_IP");
                    if (log.isInfoEnabled()) {
                        log.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
                    }
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                    if (log.isInfoEnabled()) {
                        log.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
                    }
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                    if (log.isInfoEnabled()) {
                        log.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
                    }
                }
            } else if (ip.length() > 15) {
                String[] ips = ip.split(",");
                for (int index = 0; index < ips.length; index++) {
                    String strIp = (String) ips[index];
                    if (!("unknown".equalsIgnoreCase(strIp))) {
                        ip = strIp;
                        break;
                    }
                }
            }
        }catch (Exception e){
            return "unknown";
        }
        return ip;
    }
}