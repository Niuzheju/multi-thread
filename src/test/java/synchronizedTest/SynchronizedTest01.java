package synchronizedTest;


public class SynchronizedTest01 {

    private static int i;

    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread();
        Thread t1 = new Thread(myThread, "t1");
        Thread t2 = new Thread(myThread, "t2");
        t1.start();
        t2.start();
    }

    private static class MyThread implements Runnable {

        // 锁对象, 地址不能被改变, 对象的属性可以改变
        private final Object lock = new Object();

        @Override
        public void run() {
            for (int j = 0; j < 50; j++) {
                try {
                    Thread.sleep(200L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //使用普通锁对象
                synchronized (lock) {
                    i++;
                    System.out.println(i);
                }
            }
        }

        // 使用this作为锁
        public void m() {
            synchronized (this) {
                i++;
            }
        }

        // 直接用在方法上
        public synchronized void m1() {
            i++;
        }

        // 使用Class作为锁
        public void n() {
            synchronized (MyThread.class) {
                i++;
            }
        }
        // 直接用在方法上
        public static synchronized void n1() {
            i++;
        }
    }
}
