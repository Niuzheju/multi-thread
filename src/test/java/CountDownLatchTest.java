import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch 用于一个线程需要等待其他线程执行完毕后才执行的情况
 * @author niuzheju
 * @date 2018/11/1 21:24
 */
public class CountDownLatchTest {

    //传入一个int值作为计数
    private CountDownLatch count = new CountDownLatch(2);

    @Test
    public void test01() throws InterruptedException {
        new Thread(this::threadRun, "t1").start();
        new Thread(this::threadRun, "t2").start();
        System.out.println("等待子线程执行 ... ");
        //阻塞当前线程, 知道CountDownLatch中的计数为0才会唤醒当前线程
        count.await();
        System.out.println("线程退出 ... ");
    }

    private void threadRun(){
        System.out.println(Thread.currentThread().getName() + "正在执行");
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "执行完毕");
        //当前线程运行完毕, 计数-1
        count.countDown();
    }
}
