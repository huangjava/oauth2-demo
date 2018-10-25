package com.hzy.resourceserver.service;

import com.hzy.resourceserver.aop.LogAnnotation;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;

/**
 * @author HZY
 * @created 2018/10/17 13:55
 */
@Service
public class DemoAnnotationService {

    @LogAnnotation(name = "注解式拦截的log操作---------------")
    public void logPrint() {
        System.out.println("调用了logPrint方法");
    };
}
