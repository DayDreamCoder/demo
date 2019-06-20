package com.minliu.demo.jdk8demos;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * JAVA8最近的多线程
 * <p>
 * ClassName: CompletableFutureTest <br>
 * date: 9:30 AM 01/03/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */

public class CompletableFutureTest {

    public static void main(String[] args) {
        Executor executor = getExecutor();
        Integer join = CompletableFuture.supplyAsync(() -> 3, executor)
                .thenApplyAsync(s -> s + 4, executor).join();
        System.out.println(join);

    }

    //创建线程池
    private static Executor getExecutor() {
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("test-%d").setPriority(5).build();
        return new ThreadPoolExecutor(10,
                10,
                0,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1024),
                factory,
                new ThreadPoolExecutor.AbortPolicy());
    }
}
