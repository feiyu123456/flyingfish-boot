package com.flyingfish.service;

import com.flyingfish.interfacecustom.TestInterface;
import org.junit.Test;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author jianping.yu@karakal.com.cn
 * @version 1.0
 * @date 2023/2/16
 **/
@Service
@Scope("prototype")
public class TestService implements TestInterface {

    private static ThreadLocal<Integer> local = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public String doTrade() {
        Integer integer = local.get();
        return Integer.toString(integer++);
    }

    @Override
    public void testOne() {
         TestInterface.testTwo();
    }

    @Test
    public void test(){
         testOne();
    }
}
