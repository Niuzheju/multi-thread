package print123ABC;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Print123ABC03 {
    public static void main(String[] args) {
        char[] charI = new char[]{'1', '2', '3', '4', '5', '6', '7'};
        char[] charC = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        new Thread(() -> {
            try {
                lock.lock();
                for (char c : charI) {
                    System.out.print(c);
                    condition2.signal();
                    condition1.await();

                }
                condition2.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            try {
                lock.lock();
                for (char c : charC) {
                    System.out.print(c);
                    condition1.signal();
                    condition2.await();

                }
                condition1.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

    }
}
