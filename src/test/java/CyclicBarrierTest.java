import org.junit.Test;

import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier会设置一个屏障, 只有当CyclicBarrier中设置的线程数目都达到这个屏障状态, 所有线程才会继续执行
 * 并且达到状态后会优先执行CyclicBarrier构造中传入的Runnable
 * 而且当所有阻塞线程恢复执行后CyclicBarrier可以再次利用
 * 详细参考https://blog.csdn.net/hanchao5272/article/details/79779639
 * @author niuzheju
 * @date 2018/11/1 21:41
 */
public class CyclicBarrierTest {

    //传入一个数目以及一个Runnable实现
    private CyclicBarrier cyclicBarrier = new CyclicBarrier(4, () -> System.out.println("缓存写入完毕, 开始回收jvm..."));

    @Test
    public void test01() throws InterruptedException {
        for (int i = 0; i < 4; i++) {
            new Thread(this::threadRun).start();
        }

        Thread.sleep(Integer.MAX_VALUE);
    }

    private void threadRun() {
        System.out.println("正在写入缓存...");
        try {
            Thread.sleep(2000L);
            System.out.println("写入缓存完毕,等待其他线程 ...");
            //阻塞当前线程, 直到所有CyclicBarrier中规定个数线程都进入屏障状态, 所有线程才会继续运行
            cyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("所有线程都已写入完毕, 继续执行代码...");
    }
}
