package com.flyingfish.entity;

import com.flyingfish.interfacecustom.ExcelTitle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jianping.yu@karakal.com.cn
 * @version 1.0
 * @date 2023/3/15
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private long id;

    public String name;

    @ExcelTitle("年龄")
    private int age;
}
