package org.example;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 一个简单的代码参考：
 */
public class Homework03_08 {

    private static Integer result = null;

    /**
     * 使用 ReentrantLock 的Condition
     */
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        long start = System.currentTimeMillis();

        final Lock lock = new ReentrantLock();
        Condition hasValue = lock.newCondition();

        executorService.submit(() -> {
            lock.lock();
            try {
                result = sum();
                hasValue.signal();
            } finally {
                lock.unlock();
            }

        });

        lock.lock();
        try {
            hasValue.await();

            // 确保  拿到result 并输出
            System.out.println("异步计算结果为：" + result);

            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
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
