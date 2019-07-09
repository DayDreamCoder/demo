package com.minliu.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ClassName: Demo <br>
 * date: 10:04 AM 11/06/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */

public class Demo {
    public static void main(String[] args) {
        String gen = gen("G=price,H=lbkum,I=zcpsl,J=mngko,K=sl,L=je");
        System.out.println(gen);
    }

    private static final List<String> raws = new ArrayList(Arrays.asList("A", "B", "C"));
    private static final List<String> raws1 = new ArrayList(Arrays.asList("_TZ", "_PBC", "_TZH"));

    private static String gen(String str) {
        StringBuilder sb = new StringBuilder();
        raws.forEach(raw -> {
            String raw1 = raws1.get(raws.indexOf(raw));
            Arrays.stream(str.split(",")).forEach(s -> {
                sb.append(raw).append(s).append(raw1).append(",");
            });
        });
        return sb.toString();
    }
}
