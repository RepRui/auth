package com.li.auth.basic;

import org.springframework.util.AntPathMatcher;


public class AccessInfo {
    private static final  AntPathMatcher matcher = new AntPathMatcher();
    public static final String[] WHITE_URI = {
        "/","/index","/login","/logout","/doc.html","/favicon.ico", "/webjars/**","/swagger-ui**","/swagger-ui/**", "/v3/**"
    };


    /**
     * 判断url是否与规则配置:
     * ? 表示单个字符
     * * 表示一层路径内的任意字符串，不可跨层级
     * ** 表示任意层路径
     *
     * @param path     需要匹配的URI
     * @return
     */
    public static boolean isMatch(String path) {
        for (String url : WHITE_URI) {
            if (matcher.match(url, path)) {
                return true;
            }
        }
        return false;
    }
}
