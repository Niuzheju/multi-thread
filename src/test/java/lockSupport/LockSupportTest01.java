package lockSupport;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport使用
 */
public class LockSupportTest01 {

    private static final Object o = new Object();

    public static void main(String[] args) throws InterruptedException {
        MyThread t1 = new MyThread("t1");
        MyThread t2 = new MyThread("t2");
        t1.start();
        Thread.sleep(1000L);
        t2.start();
        Thread.sleep(20000L);
        System.out.println(LockSupport.getBlocker(t1));
        t1.interrupt();
        // 唤醒暂停中的线程
        LockSupport.unpark(t2);
        t1.join();
        t2.join();

    }

    private static class MyThread extends Thread {

        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (o) {
                System.out.println("in " + this.getName());
                // 暂停当前线程
                LockSupport.park();
                if (this.isInterrupted()) {
                    System.out.println("被中断了");
                }
                System.out.println("继续执行");
            }
        }
    }
}
