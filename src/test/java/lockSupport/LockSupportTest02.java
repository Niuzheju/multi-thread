package lockSupport;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport使用
 */
public class LockSupportTest02 {

    private static final Object o = new Object();

    public static void main(String[] args) throws InterruptedException {
        MyThread t1 = new MyThread("t1");
        t1.start();
        // park和unpark的调用并不需要顺序, 并不一定要先park再unpark
        LockSupport.unpark(t1);

    }

    private static class MyThread extends Thread {

        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (o) {
                System.out.println("in " + this.getName());
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
