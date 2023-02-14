/**
 * ThreadLocal把一个数据存储到当前线程中
 */
public class ThreadLocalTest {

    private static final ThreadLocal<Integer> tl = new ThreadLocal<>();

    public static void main(String[] args) {
        new Thread(() -> {
            tl.set(1);
            print();
        }, "t1").start();

        new Thread(() -> {
            tl.set(2);
            print();
        }, "t2").start();

    }

    private static void print() {
        System.out.println(Thread.currentThread().getName() + ": " + tl.get());
    }
}
