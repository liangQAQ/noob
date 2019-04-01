package com.huangliang.mvc.controller;

import com.huangliang.mvc.annotation.HLAutowire;
import com.huangliang.mvc.annotation.HLController;
import com.huangliang.mvc.annotation.HLRequestMapping;
import com.huangliang.mvc.service.TestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@HLController
@HLRequestMapping(value="/a1")
public class TestController {

    @HLAutowire
    private TestService testService;

    @HLRequestMapping(value = "/b2")
    public String a1(HttpServletRequest request, HttpServletResponse response){
        try {
            response.getWriter().write("sssss");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "qwewww";
    }
}
