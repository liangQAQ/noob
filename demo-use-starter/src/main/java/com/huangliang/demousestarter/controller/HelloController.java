package com.huangliang.demousestarter.controller;

import com.example.starter.HLTemplate;
import com.huangliang.demousestarter.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private HLTemplate template;

    @RequestMapping(value="/hello")
    public String hello(){
        User u = new User();
        u.setAge(11);
        u.setName("aassdd");
        return template.doFormat(u);
    }
}
