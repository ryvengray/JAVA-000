package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 一个简单的代码参考：
 */
public class Homework03_05_1 {

    private static Integer result = null;

    /**
     * 使用暴力等待-优化，添加等待时间
     */
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        long start = System.currentTimeMillis();

        executorService.submit(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = sum();
        });

        final long waitTime = 3000;

        while (result == null && (System.currentTimeMillis() - start) < waitTime) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (result == null) {
            System.out.println("获取超时");
        }

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        executorService.shutdown();
        // 然后退出main线程
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }
}
