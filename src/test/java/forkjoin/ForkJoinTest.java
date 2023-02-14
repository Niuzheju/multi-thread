package forkjoin;

import com.niuzj.task.MyForkJoinTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author niuzheju
 * @date 15:52 2021/3/16
 */
public class ForkJoinTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(new MyForkJoinTask(1, 1001));
        Integer result = forkJoinTask.get();
        System.out.println(result);

    }
}
