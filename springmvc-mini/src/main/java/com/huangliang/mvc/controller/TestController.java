package com.huangliang.mvc.controller;

import com.huangliang.framework.annotation.HLAutowire;
import com.huangliang.framework.annotation.HLController;
import com.huangliang.framework.annotation.HLRequestMapping;
import com.huangliang.framework.webmvc.HLModelAndView;
import com.huangliang.mvc.service.TestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@HLController
@HLRequestMapping(value="/test")
public class TestController {

    @HLAutowire
    private TestService testService;

    @HLRequestMapping(value = "/a1.json")
    public HLModelAndView a1Json(HttpServletRequest request, HttpServletResponse response){
        try {
            testService.print();
            response.getWriter().write("sssss");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @HLRequestMapping(value = "/a1.html")
    public HLModelAndView a1Html(String name,HttpServletRequest request, HttpServletResponse response){
        return new HLModelAndView("a1",null);
    }
}
