package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock使用
 * lockInterruptibly 获取锁的过程中线程可以被中断, 即调用interrupt方法
 */
public class ReentrantLockTest03 {

    private Lock lock = new ReentrantLock();

    private void m1() {
        try {
            lock.lock();
            String name = Thread.currentThread().getName();
            System.out.println(name + " start");
            TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
            System.out.println(name + " end");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void m2() {

        String name = Thread.currentThread().getName();
        try {
            lock.lockInterruptibly();
            System.out.println(name + " start");
            TimeUnit.SECONDS.sleep(5L);
            System.out.println(name + " end");
        } catch (InterruptedException e) {
            System.out.println(name + " is interrupted");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockTest03 reentrantLockTest01 = new ReentrantLockTest03();
        new Thread(reentrantLockTest01::m1, "t1").start();
        TimeUnit.SECONDS.sleep(1L);
        Thread t2 = new Thread(reentrantLockTest01::m2, "t2");
        t2.start();
        t2.interrupt();
    }

}
