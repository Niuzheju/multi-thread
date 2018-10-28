import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * java信号量使用
 * Semaphore类是一个计数信号量，必须由获取它的线程释放，
 * 通常用于限制可以访问某些资源（物理或逻辑的）线程数目。
 * 线程要想访问资源,必须要先从Semaphore中获取一个计数, 获取后Semaphore中资源数目-1
 * 如果Semaphore中资源数目为0, 那么再进来的线程就需要阻塞, 直到有其他线程释放资源数目
 * @author niuzheju
 * @date 2018/10/27 16:31
 */
public class SemaphoreThread {
    private int a = 1;

    class Bank{
        private int account = 20;

        public int getAccount() {
            return account;
        }

        public void save(int account) {
            this.account += account;
        }
    }

    class WorkThread implements Runnable{
        private Bank bank;

        private Semaphore semaphore;

        private WorkThread(Bank bank, Semaphore semaphore) {
            this.bank = bank;
            this.semaphore = semaphore;
        }

        public void run() {
            int b = a++;
            //判断目前可用资源数目
            if (semaphore.availablePermits() > 0){
                //当前线程能获取到资源
                System.out.println("线程" + b + "启动,进入银行,有位置, 立即存钱");
            } else {
                //当前线程获取不到资源,将会在此阻塞
                System.out.println("线程" + b + "启动,进入银行,没有位置, 等待");
            }
            try {
                //获取一个资源并继续执行线程
                semaphore.acquire();
                bank.save(10);
                System.out.println(b + "存钱完毕, 银行余额:" + bank.getAccount());
                Thread.sleep(1000L);
                System.out.println(b + "离开银行");
                //需要进行的操作执行完毕,释放资源
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void run(){
        Bank bank = new Bank();
        //创建信号量,并把资源数目设置为2
        Semaphore semaphore = new Semaphore(2);
        //利用线程池去执行任务
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 10; i++) {
            executorService.execute(new WorkThread(bank, semaphore));
        }
        executorService.shutdown();
        //获取2个资源,获取不到将阻塞
        semaphore.acquireUninterruptibly(2);
        System.out.println("工作结束");
        //释放2个资源
        semaphore.release(2);
    }

    public static void main(String[] args) {
        new SemaphoreThread().run();
    }
}
