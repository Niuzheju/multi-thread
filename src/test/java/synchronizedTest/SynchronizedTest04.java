package synchronizedTest;

import java.util.concurrent.TimeUnit;

/**
 * @author niuzheju
 * @date 16:28 2021/8/13
 */
public class SynchronizedTest04 {

    private static boolean ready;

    private static int number;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                // 不使用volatile时, 使用同步代码块也可以获取变量的最新值
                synchronized (SynchronizedTest04.class) {

                }
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        number = 10;
        ready = true;
    }
}
