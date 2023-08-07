package com.flyingfish.flyingfishboot;

import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;

public class C1 {

    public static void main(String[] args) {
        Collections.synchronizedMap(new HashMap<>());
    }

}
