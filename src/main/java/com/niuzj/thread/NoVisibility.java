package com.niuzj.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @Author niuzheju
 * @Date 16:17 2022/8/8
 */
public class NoVisibility {

    private static boolean ready;
    private static int number;

    private static final CountDownLatch latch = new CountDownLatch(1);

    private static class ReaderThread extends Thread {

        @Override
        public void run() {
            // 测试线程可见性, ready, number被主线程修改, 这里读值
            while (!ready) {
                Thread.yield();
                System.out.println(number);
                latch.countDown();
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();
        number = 42;
        ready = true;

        latch.await();
    }
}
