import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {

    private int i = 0;

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    private CountDownLatch countDownLatch = new CountDownLatch(1000);


    /**
     * 多线程修改成员变量会有线程安全问题
     * @throws InterruptedException
     */
    @Test
    public void test01() throws InterruptedException {
        for (int j = 0; j < 1000; j++) {
            new Thread(() -> {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println(i);
    }

    /**
     * 使用AtomicInteger可以在多线程环境下保证线程安全
     * @throws InterruptedException
     */
    @Test
    public void test02() throws InterruptedException {
        for (int j = 0; j < 1000; j++) {
            new Thread(() -> {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                atomicInteger.getAndIncrement();
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println(atomicInteger);
    }
}
