package com.flyingfish.flyingfishboot;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author jianping.yu@karakal.com.cn
 * @version 1.0
 * @date 2023/3/15
 **/
@Slf4j
public class TestLinkedBlockingDeque {

    final static LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(10);

    public static void main(String[] args) {

    }
    
}
