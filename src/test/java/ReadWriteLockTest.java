import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author niuzheju
 * @date 15:38 2021/3/18
 */
public class ReadWriteLockTest {

    /**
     * 可重入读写锁
     * 读读不互斥, 读写互斥, 写写互斥
     */
    public static void main(String[] args) {
        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(false);
        final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

        new Thread(() -> {

            writeLock.lock();
            System.out.println("获取写锁");

        }).start();

        new Thread(() -> {

            readLock.lock();
            System.out.println("获取读锁");
            readLock.unlock();

        }).start();

    }
}
