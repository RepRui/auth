package com.li.auth.controller;

import com.li.auth.basic.StaticPages;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class IndexController {

    @RequestMapping(value = {"/","/index"},method = {RequestMethod.GET} )
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(StaticPages.INDEX_PAGE);
        out.flush();
        out.close();
    }
}
