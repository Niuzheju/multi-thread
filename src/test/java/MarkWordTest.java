import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * markword第一个字节的后三位为锁的标识位
 */
public class MarkWordTest {


    public static void main(String[] args) throws InterruptedException {
        // 虚拟机参数设置-XX:BiasedLockingStartupDelay=0后关闭了偏向锁的延迟, 对象new出来后就是偏向锁, 标识位101
        // 在不设置-XX:BiasedLockingStartupDelay=0的情况下,要休眠超过4秒再创建锁对象, 对象才会进入偏向锁
        TimeUnit.SECONDS.sleep(5);
        final Object o = new Object();
        //默认无锁, 001
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        //如果出现其他线程争抢锁, 锁会升级成重量级锁010
        new Thread(() -> {
            synchronized (o) {
                //synchronized中默认轻量级锁,000
                System.out.println("t1:" + ClassLayout.parseInstance(o).toPrintable());
            }
        }).start();
        synchronized (o) {
            //在偏向锁不出现的情况下, synchronized中默认轻量级锁,000
            System.out.println("main:" + ClassLayout.parseInstance(o).toPrintable());
        }
    }
}
