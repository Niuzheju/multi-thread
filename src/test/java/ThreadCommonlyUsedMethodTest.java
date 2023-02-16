/**
 * 线程常用方法
 *
 * @Author niuzheju
 * @Date 16:41 2023/2/16
 */
public class ThreadCommonlyUsedMethodTest {

    public static void main(String[] args) {
//        test01();
//        test02();
//        test03();
        test04();
    }

    /**
     * sleep()
     * 作用是让当前线程停止执行，把cpu让给其他线程执行，但不会释放对象锁和监控的状态，到了指定时间后线程又会自动恢复运行状态。
     * 注意：线程睡眠到期自动苏醒，并返回到可运行状态，不是运行状态。sleep()中指定的时间是线程不会运行的最短时间。
     * 因此，sleep()方法不能保证该线程睡眠到期后就开始执行
     */
    public static void test01() {

        Thread t = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(i * 10);
                    System.out.println("thread sleep ...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();

    }

    /**
     * yield
     * 作用：暂停当前正在执行的线程对象（及放弃当前拥有的cup资源），并执行其他线程。yield()做的是让当前运行线程回到可运行状态，
     * 以允许具有相同优先级的其他线程获得运行机会。
     * 线程状态变化：运行 --》 就绪
     * 注意：使用yield()的目的是让相同优先级的线程之间能适当的轮转执行。
     * 但是，实际中无法保证yield()达到让步目的，因为让步的线程还有可能被线程调度程序再次选中。
     */
    public static void test02() {

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i == 3) {
                    Thread.yield();
                    System.out.println("thread1 yield ...");
                }
                System.out.println("thread1 running");
            }
        });

        t1.start();

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i == 6) {
                    Thread.yield();
                    System.out.println("thread2 yield ...");
                }
                System.out.println("thread2 running");
            }
        });

        t2.start();

    }

    /**
     * join
     * 作用：主线程(生成子线程的线程)等待子线程（生成的线程）的终止。也就是在子线程调用了join()方法后面的代码，只有等到子线程结束了才能执行。
     * 线程状态变化：
     * 主线程：运行 --》 等待/超时等待(取决于调用的是join()还是join(long millis))  调用join方法的子线程执行完后会 等待/超时等待 --》 运行/阻塞(对象被锁)
     * 子线程：就绪 --》 运行 --》 终止
     * 注意：主线程不会释放已经持有的对象锁。
     */
    public static void test03() {

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("thread1 running");
            }
        });

        t1.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("thread2 running");
            }
        });

        t2.start();

    }

    // 和synchronized结合的对象锁的wait, notify, notifyAll
    public static void test04() {
        Object lock = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("thread1 wait ...");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread1 run ...");
            }
        });

        t1.start();

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("thread2 wait ...");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread2 run ...");
            }
        });

        t2.start();

        Thread t3 = new Thread(() -> {
            System.out.println("thread3 run ...");
            synchronized (lock) {
                System.out.println("thread3 notify");
                // 随机唤醒在lock上阻塞的一个线程
                lock.notify();
                // 唤醒在lock上阻塞的所有线程
                lock.notifyAll();
            }
        });

        t3.start();

    }
}
