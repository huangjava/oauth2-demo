package com.hzy.resourceserver.controller;

import com.hzy.resourceserver.aop.LogAnnotation;
import com.hzy.resourceserver.common.EventType;
import com.hzy.resourceserver.service.DemoConfigService;
import com.hzy.resourceserver.service.DemoAnnotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author HZY
 * @created 2018/10/15 17:01
 */
@RequestMapping("/demo")
@RestController
public class AopDemoController {

    @Autowired
    private DemoConfigService demoConfigService;

    @Autowired
    private DemoAnnotationService demoAnnotationService;

    @GetMapping("test1")
    public void test1(){
        System.out.println("测试接口~！！！！！！！！");
    }

    @GetMapping("test2")
    public void test2(){
        demoAnnotationService.logPrint();
    }


    @GetMapping("getConfigDemoValue")
    public void getConfigValue(){
        List<String> listProprites = demoConfigService.getListProprites();
        Map<String, String> mapProprites = demoConfigService.getMapProprites();
        System.out.println(listProprites);
    }

    @GetMapping("getEventType")
    public void getEventType(String eventType){
        EventType mapProprites = demoConfigService.getEventType(eventType);
        System.out.println(mapProprites.getType());

    }


}
