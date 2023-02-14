import java.util.concurrent.TimeUnit;

/**
 * volatile关键字
 * 1.修饰一个变量时, 变量的值在线程间可见
 * 变量值的计算和修改是在cpu进行的, 不使用volatile时线程只会使用cpu缓存中的值, 而该值在内存中可能已发生变化
 * 所以一个线程修改了一个变量, 如果想要其他的线程立刻知道这个变量的新值,需要volatile修饰
 * 2.禁止指令重排序
 */
public class VolatileTest {

    private static volatile boolean b = true;

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                b = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        while (b) {
            // System.out.println(); 会从内存中同步数据, 不使用volatile循环不会结束
            //System.out.println();
        }

    }
}
