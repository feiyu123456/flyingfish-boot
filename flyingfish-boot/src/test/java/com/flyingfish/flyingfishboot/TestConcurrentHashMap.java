package com.flyingfish.flyingfishboot;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * @author jianping.yu@karakal.com.cn
 * @version 1.0
 * @date 2023/3/15
 **/
@Slf4j
public class TestConcurrentHashMap {

    private static <V> void demo(Supplier<Map<String, V>> supplier,
                                 BiConsumer<Map<String, V>, List<String>> consumer) {
        Map<String, V> counterMap = supplier.get();
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(() -> {
                 List<String> words = Arrays.asList(new String[]{"a", "b", "c", "d", "e", "f", "g"});
                 consumer.accept(counterMap, words);
            });
            ts.add(thread);
        }
        ts.forEach(t -> t.start());
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(counterMap);
    }

    public static void main(String[] args) {
//        demo(() -> new HashMap<String, Integer>(),
//                (map, words) -> {
//                for (String word : words) {
//                    Integer counter = map.get(word);
//                    int newValue = counter == null ? 1 : counter + 1;
//                    map.put(word, newValue);
//                }
//            }
//        );

        demo(() -> new ConcurrentHashMap<String, LongAdder>(),
                (map, words) -> {
                    for (String word : words) {
                        map.computeIfAbsent(word, (key) -> new LongAdder()).increment();
                    }
                }
        );
    }


}
