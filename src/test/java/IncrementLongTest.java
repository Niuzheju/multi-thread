import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * AtomicLong, long, LongAdder性能对比
 * LongAdder 效率最高
 * AtomicLong和long视情况而定
 */
public class IncrementLongTest {

    static AtomicLong count1 = new AtomicLong(0);

    static long count2;

    static LongAdder count3 = new LongAdder();

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads1 = new Thread[1000];
        for (int i = 0; i < threads1.length; i++) {
            threads1[i] = new Thread(() -> {
                for (int j = 0; j < 100000; j++) {
                    count1.incrementAndGet();
                }
            });
        }
        long start = System.currentTimeMillis();
        for (Thread thread : threads1) {
            thread.start();
        }
        for (Thread thread : threads1) {
            thread.join();
        }
        System.out.println("AtomicLong " + count1.get() + " time " + (System.currentTimeMillis() - start));
        TimeUnit.SECONDS.sleep(3L);

        final Object lock = new Object();
        start = System.currentTimeMillis();
        for (int i = 0; i < threads1.length; i++) {
            threads1[i] = new Thread(() -> {
                for (int j = 0; j < 100000; j++) {
                    synchronized (lock){
                        count2++;
                    }
                }
            });
        }
        for (Thread thread : threads1) {
            thread.start();
        }
        for (Thread thread : threads1) {
            thread.join();
        }
        System.out.println("long " + count2 + " time " + (System.currentTimeMillis() - start));
        TimeUnit.SECONDS.sleep(3L);

        start = System.currentTimeMillis();
        for (int i = 0; i < threads1.length; i++) {
            threads1[i] = new Thread(() -> {
                for (int j = 0; j < 100000; j++) {
                   count3.increment();
                }
            });
        }
        for (Thread thread : threads1) {
            thread.start();
        }
        for (Thread thread : threads1) {
            thread.join();
        }
        System.out.println("LongAdder " + count3 + " time " + (System.currentTimeMillis() - start));
    }
}
