package com.flyingfish.controller;

import com.flyingfish.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author jianping.yu@karakal.com.cn
 * @version 1.0
 * @date 2023/2/16
 **/
//@RestController
@ResponseBody
@RequestMapping("/person")
//@Scope(value = "prototype")
public class PersonController {

    @Resource
    private TestService testService;

    @GetMapping("/query")
    public String getPerson() {
        String data = testService.doTrade();
        System.out.println("service属性：" + data);
        System.out.println(testService.toString());
        return "ok";
    }
    
}
