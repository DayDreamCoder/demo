package com.minliu.demo.java8demos;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.minliu.demo.pojo.Product;

import java.util.concurrent.*;

public class CompletableFutureTaskTest {
    public static void main(String[] args) {
        Executor executor = getExecutor();
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new Product(1, "Apple", 6800.00, "America", "A001");
        }, executor);
    }

    private static Executor getExecutor(){
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("test-%d").build();
        return new ThreadPoolExecutor(10,10,1, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),factory,new ThreadPoolExecutor.AbortPolicy());
    }
}
