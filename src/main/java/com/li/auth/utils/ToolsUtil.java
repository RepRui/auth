package com.li.auth.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.text.MessageFormat;
import java.util.Enumeration;

public class ToolsUtil {

    /**
     * 从请求中获取token
     * @param request
     * @return
     */
    public static String getLoginToken (HttpServletRequest request){
        //从请求的头部获取token
        String token = request.getHeader("Authorization");
        //如果请求头部没有获取到token，则从请求参数中获取token
        if(StringUtils.isEmpty(token)){
            token = request.getHeader("token");
            if(StringUtils.isEmpty(token)){
                token = request.getParameter("token");
            }
            return token;
        }
        return token.replace("Bearer ", "");
    }

    public static void writeLogs(String msg,Logger logger, Exception ex){
        //ex.printStackTrace();
        StringBuilder sbException = new StringBuilder();
        sbException.append(ex.getClass().getName()).append(": ").append(ex.getMessage()).append("\n");
        for (StackTraceElement ele : ex.getStackTrace()) {
            sbException.append(MessageFormat.format("\tat {0}.{1}({2}:{3})\n",
                    ele.getClassName(), ele.getMethodName(), ele.getFileName(), ele.getLineNumber()));;
        }
        logger.error("{}：{}\n{}", msg,ex.getMessage(),sbException);
    }

    /**
     * 获取真实IP
     * @param request 请求体
     * @return 真实IP
     */
    public static String getRealIp(HttpServletRequest request) {
        // 这个一般是Nginx反向代理设置的参数
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 处理多IP的情况（只取第一个IP）
        if (ip != null && ip.contains(",")) {
            String[] ipArray = ip.split(",");
            ip = ipArray[0];
        }
        return ip;
    }

    /**
     * 获取POST请求的参数
     * @param request
     * @return
     */
    public static String getPostDataString(HttpServletRequest request) {
        StringBuilder data = new StringBuilder();
        String line = null;
        BufferedReader reader = null;
        try{
            reader = request.getReader();
            while((line = reader.readLine()) != null){
                data.append(line);
            }
        }catch (Exception ex){
            return "";
            // logger.error("{}\n",ex.getMessage());
        }
        return data.toString();
    }
    /**
     * 获取POST请求的参数
     * @param request
     * @return
     */
    public static String getGetDataString(HttpServletRequest request) {
        return request.getQueryString();
        /*StringBuilder data = new StringBuilder();
        Enumeration<String> paraNames = request.getParameterNames();
        String key;
        String value;
        while (paraNames.hasMoreElements()) {
            key = paraNames.nextElement();
            value = request.getParameter(key);
            data.append(key).append("=").append(value).append("&");
        }
        return data.toString();*/
    }

}
