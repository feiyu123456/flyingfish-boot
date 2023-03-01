package com.flyingfish.controller;

import com.flyingfish.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author jianping.yu@karakal.com.cn
 * @version 1.0
 * @date 2023/2/16
 **/
@Controller
@Scope("prototype")
public class PersonController {

    @Autowired
    private TestService testService;

    @RequestMapping("/query")
    @ResponseBody
    public String getPerson() {
        String data = testService.doTrade();
        System.out.println("service属性：" + data);
        System.out.println(testService.toString());
        return "ok";
    }
    
}
