package synchronizedTest;

import java.util.concurrent.TimeUnit;

/**
 * 程序异常会释放锁
 * 释放锁后, 其他线程可能会直接进来执行
 * 所以需要特别注意同步代码块中异常的处理
 */
public class SynchronizedTest02 {
    public static void main(String[] args) throws InterruptedException {
        T t = new T();
        Runnable runnable = t::m;
        new Thread(runnable, "t1").start();
        TimeUnit.SECONDS.sleep(3L);
        new Thread(runnable, "t2").start();
    }

    private static class T{

        private int count;

        public synchronized void m(){
            while (true){
                String name = Thread.currentThread().getName();
                System.out.println(name + " start");
                count++;
                System.out.println(name + " " + count);
                try {
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (count == 5){
                    int a = count / 0;
                    System.out.println(count);
                }

            }
        }
    }
}
