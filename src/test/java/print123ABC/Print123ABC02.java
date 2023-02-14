package print123ABC;

/**
 * 打印1A2B3C4D5E6F7G
 */
public class Print123ABC02 {

    private static final Object o = new Object();
    private static boolean print = false;

    public static void main(String[] args) {
        char[] charI = new char[]{'1', '2', '3', '4', '5', '6', '7'};
        char[] charC = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        new Thread(() -> {
            synchronized (o) {
                print = true;
                for (char c : charI) {
                    System.out.print(c);
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //最后叫醒wait的线程, 否则无法线程无法停下
                o.notify();
            }
        }).start();
        new Thread(() -> {
            synchronized (o) {
                try {
                    //保证t1先打印
                    if (!print) {
                        o.wait();
                    }
                    for (char c : charC) {
                        System.out.print(c);
                        o.notify();
                        o.wait();

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                o.notify();
            }
        }).start();

    }
}
