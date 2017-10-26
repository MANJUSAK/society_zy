package com.goodsoft.society_zy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * description:
 * ===>
 *
 * @author 严彬荣 Created on 2017-10-25 17:52
 */
public class Test {
    @org.junit.Test
    public void test() {
        List<Persion> list = new ArrayList<>();
        for (int i = 0; i < 6; ++i) {
            Persion data = new Persion();
            data.setL1("1");
            data.setL2("1");
            data.setL3("1");
            data.setL4("1");
            data.setL5("1");
            data.setL6("1");
            data.setL7("1");
            data.setL8("1");
            data.setL9("1");
            data.setL10("1");
            data.setL11("1");
            data.setL12("1");
            data.setL13("1");
            data.setL14("1");
            data.setL15("1");
            data.setL16("1");
            data.setL17("1");
            data.setL18("1" + i);
            list.add(data);
        }
        System.out.println("原始数据" + list);
        Set<Persion> set = new HashSet<>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        System.out.println("去重后的数据" + list);
    }
}
