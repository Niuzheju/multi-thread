package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock使用
 * tryLock 尝试获取锁, 可以指定一个时间
 * 在等待指定时间后如果还获取不到锁,就返回false
 */
public class ReentrantLockTest02 {

    private Lock lock = new ReentrantLock();

    private void m1() {
        try {
            lock.lock();
            for (int i = 0; i < 7; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void m2() {
        boolean locked = false;
        long time = System.currentTimeMillis();
        try {
            locked = lock.tryLock(5, TimeUnit.SECONDS);
            System.out.println("m2, " + locked);
            System.out.println(System.currentTimeMillis() - time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (locked) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockTest02 reentrantLockTest01 = new ReentrantLockTest02();
        new Thread(reentrantLockTest01::m1).start();
        TimeUnit.SECONDS.sleep(1L);
        new Thread(reentrantLockTest01::m2).start();
    }

}
