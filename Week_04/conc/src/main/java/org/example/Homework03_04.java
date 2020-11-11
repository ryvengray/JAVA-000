package org.example;

import java.util.concurrent.*;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 一个简单的代码参考：
 */
public class Homework03_04 {

    private static int result = 0;

    /**
     * 使用Semaphore
     */
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        long start = System.currentTimeMillis();

        Semaphore semaphore = new Semaphore(1);

        executorService.submit(() -> {
            try {
                // 子线程睡眠，模拟main线程先acquire的情形
                Thread.sleep(400);
                semaphore.acquire();
                result = sum();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 释放两次，主线程acquire(2)才能获得
                semaphore.release();
                semaphore.release();
            }

        });

        try {
            semaphore.acquire(2);
            // 确保  拿到result 并输出
            System.out.println("异步计算结果为：" + result);

            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
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
