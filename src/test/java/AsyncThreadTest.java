import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AsyncThreadTest {

    @Test
    public void test01() throws Exception {
        long time1 = System.currentTimeMillis();
        String s = task();
        System.out.println(s);
        System.out.println("程序执行时间:" + (System.currentTimeMillis() - time1));
    }

    private String task() throws Exception {
        String s = "result1";
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<String> future1 = executorService.submit(() -> {
            Thread.sleep(2000);
            return "result2";
        });
        Future<String> future2 = executorService.submit(() -> {
            Thread.sleep(1000);
            return "result3";
        });
        // get方法会阻塞,直到结果返回
        s = s + future1.get();
        s = s + future2.get();
        return s;
    }
}
