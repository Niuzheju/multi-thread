package codeFromBook;

/**
 * 构造线程安全的类
 *
 * @Author niuzheju
 * @Date 15:18 2023/7/4
 */
public class Counter {

    private long value = 0;

    public synchronized long getValue() {
        return value;
    }

    public synchronized void increment() {
        if (value == Long.MAX_VALUE) {
            throw new IllegalStateException("counter overflow");
        }
        ++value;
    }

    public static void main(String[] args) {
        Counter counter = new Counter();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                counter.increment();
                System.out.println(Thread.currentThread().getName() + "_" + counter.getValue());
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                counter.increment();
                System.out.println(Thread.currentThread().getName() + "_" + counter.getValue());
            }
        }).start();
    }
}
