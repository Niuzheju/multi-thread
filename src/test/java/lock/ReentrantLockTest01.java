package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock使用, 必须手动释放锁
 */
public class ReentrantLockTest01 {

    private Lock lock = new ReentrantLock();

    private void m1() {
        try {
            lock.lock();
            for (int i = 0; i < 10; i++) {
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
        try {
            lock.lock();
            System.out.println("m2");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockTest01 reentrantLockTest01 = new ReentrantLockTest01();
        new Thread(reentrantLockTest01::m1).start();
        TimeUnit.SECONDS.sleep(1L);
        new Thread(reentrantLockTest01::m2).start();
    }

}
