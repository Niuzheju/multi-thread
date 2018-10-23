import com.niuzj.task.MyTask;

import java.util.concurrent.*;

//线程池相关参考https://www.cnblogs.com/dolphin0520/p/3932921.html
//线程池测试
public class ThreadExceutorPoolTest {


    public static void main(String[] args) {
//        test02();
//        test03();
        test04();

    }

    /**
     * 原始线程池创建方法
     */
    public static void test01(){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200
                , TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));
        for (int i = 0; i < 15; i++) {
            MyTask task = new MyTask(i);
            executor.execute(task);
            System.out.println("线程中线程数目:" + executor.getPoolSize());
            System.out.println("队列中等待执行的任务数目:" + executor.getQueue().size());
            System.out.println("已执行完毕的任务数目:" + executor.getCompletedTaskCount());
        }
        executor.shutdown();
    }


    /**
     * 定长线程池, corePoolSize和maximumPoolSize相等
     * 线程池中最大线程数固定
     */
    private static void test02(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 20; i++) {
            MyTask myTask = new MyTask(i);
            executorService.execute(myTask);
        }
        executorService.shutdown();
    }

    /**
     * 创建单线程线程池,只有一个线程
     */
    private static void test03(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 15; i++) {
            MyTask myTask = new MyTask(i);
            executorService.execute(myTask);
        }
        executorService.shutdown();
    }

    /**
     * 创建一个corePoolSize为0和maximumPoolSize为Integer.MAX_VALUE的线程池
     * 为每一个任务创建一个线程,并且线程超时60秒自动销毁
     */
    private static void test04(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 15; i++) {
            MyTask myTask = new MyTask(i);
            executorService.execute(myTask);
        }
        executorService.shutdown();
    }
}
