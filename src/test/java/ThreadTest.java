import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author niuzheju
 * @Date 15:49 2023/2/14
 */
public class ThreadTest {

    @Test
    public void test01() throws ExecutionException, InterruptedException {
        // Thread子类创建
        Thread thread1 = new MyThread();
        thread1.start();

        // Runnable实现类创建
        Thread thread2 = new Thread(() -> System.out.println("run2"));
        thread2.start();

        // Callable创建线程
        FutureTask<String> task = new FutureTask<>(() -> "call");
        Thread thread3 = new Thread(task);
        thread3.start();
        System.out.println(task.get());

    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("run1");
    }
}
