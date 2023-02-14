package print123ABC;

import java.util.concurrent.locks.LockSupport;

/**
 * 打印1A2B3C4D5E6F7G
 */
public class Print123ABC01 {
    private static Thread t1, t2;
    public static void main(String[] args){
        char[] charI = new char[]{'1', '2', '3', '4', '5', '6', '7'};
        char[] charC = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        t1 = new Thread(() -> {
            for (char c : charI) {
                System.out.println(c);
                //唤醒t2
                LockSupport.unpark(t2);
                //当前线程阻塞
                LockSupport.park();
            }
        });
        t2 = new Thread(() ->{
            for (char c : charC) {
                //当前线程阻塞
                LockSupport.park();
                System.out.println(c);
                //唤醒t1
                LockSupport.unpark(t1);
            }
        });
        t1.start();
        t2.start();
    }
}
