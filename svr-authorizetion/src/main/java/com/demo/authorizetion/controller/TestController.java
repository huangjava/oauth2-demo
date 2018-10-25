package com.demo.authorizetion.controller;

import com.demo.authorizetion.model.TestModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * @author HZY
 * @created 2018/10/17 17:26
 */
@RestController
@RequestMapping("/")
public class TestController {

    @RequestMapping(value="order/demo")
    public TestModel getDemo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth);
        TestModel yy = new TestModel();
        yy.setName("中文");
        yy.setCode(3);
        return yy;
    }

    @GetMapping("/test")
    public String getTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TestModel yy = new TestModel();
        yy.setName("中文");
        yy.setCode(3);
        return objectMapper.writeValueAsString(yy);
    }


}
