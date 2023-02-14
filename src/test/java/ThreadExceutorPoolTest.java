import com.niuzj.task.MyTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

//线程池相关参考https://www.cnblogs.com/dolphin0520/p/3932921.html
//线程池测试
public class ThreadExceutorPoolTest {


    public static void main(String[] args) throws InterruptedException {
//        test01();
//        test02();
//        test03();
//        test04();
//        test05();
//        test06();
//        test07();
        new ThreadExceutorPoolTest().test08();

    }

    /**
     * 原始线程池创建方法
     */
    public static void test01() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200
                , TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5));
        for (int i = 0; i < 16; i++) {
            MyTask task = new MyTask(i);
            executor.execute(task);
            System.out.println("线程池中线程数目:" + executor.getPoolSize());
            System.out.println("队列中等待执行的任务数目:" + executor.getQueue().size());
            System.out.println("已执行完毕的任务数目:" + executor.getCompletedTaskCount());
            System.out.println("----------------------------");
        }
        executor.shutdown();
    }


    /**
     * 定长线程池, corePoolSize和maximumPoolSize相等
     * 线程池中最大线程数固定
     */
    private static void test02() {
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
    private static void test03() {
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
    private static void test04() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 15; i++) {
            MyTask myTask = new MyTask(i);
            executorService.execute(myTask);
        }
        executorService.shutdown();
    }

    /**
     * 延期线程池
     */
    private static void test05() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
        Runnable command = () -> {
            System.out.println("调度线程池" + (System.currentTimeMillis()) / 1000);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        executorService.schedule(command, 1, TimeUnit.SECONDS);
        // 从前一个任务开始时计算延迟时间
        executorService.scheduleAtFixedRate(command, 1, 2, TimeUnit.SECONDS);
        // 前一个任务结束后计算延迟时间
        executorService.scheduleWithFixedDelay(command, 1, 2, TimeUnit.SECONDS);
//        executorService.shutdown();


    }


    private static void test06() throws InterruptedException {
        List<Integer> numbers = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                numbers.add(10);
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();

        System.out.println(numbers.size());
    }

    private static void test07() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Future<Integer> future = executorService.submit(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 1;
            });
            futures.add(future);
        }

        List<Integer> numbers1 = new ArrayList<>();
        for (Future<Integer> future : futures) {
            try {
                Integer integer = future.get();
                numbers1.add(integer);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }


        System.out.println(numbers1.size());
    }

    private void test08() throws InterruptedException {
        Random random = new Random();
        List<Integer> numbers = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Future<?>> futures = new ArrayList<>();


        for (int i = 0; i < 10; i++) {
            Future<?> future = executorService.submit(() -> {
                try {
                    synchronized (this) {
                        TimeUnit.SECONDS.sleep(random.nextInt(3));
                        numbers.add(10);
                        System.out.println(this);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            futures.add(future);
        }

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }


        System.out.println(numbers.size());
    }

}
