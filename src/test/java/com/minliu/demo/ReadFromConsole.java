package com.minliu.demo;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

/**
 * ClassName: ReadFromConsole <br>
 * date: 3:45 PM 03/07/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */

public class ReadFromConsole {
    private static Scanner in = new Scanner(System.in);


    public void setIn(ByteArrayInputStream baIn) {
        in = new Scanner(baIn);
    }

    public static String readAndReturn() {
        return in.nextLine();
    }
}
