package com.li.auth.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.text.MessageFormat;

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

}
