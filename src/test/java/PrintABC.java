import java.util.concurrent.Semaphore;

/**
 * 三个线程分别打印A，B，C，要求这三个线程一起运行，打印n次，输出形如“ABCABCABC....”的字符串
 * @author niuzheju
 * @date 2018/10/27 17:31
 */
public class PrintABC {

    private Semaphore a = new Semaphore(1);
    private Semaphore b = new Semaphore(1);
    private Semaphore c = new Semaphore(1);

    {
        try {
            b.acquire();
            c.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void printA(){
        try {
            a.acquire();
            System.out.println("A");
            b.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void printB(){
        try {
            b.acquire();
            System.out.println("B");
            c.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void printC(){
        try {
            c.acquire();
            System.out.println("C");
            a.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final PrintABC printABC = new PrintABC();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                printABC.printA();
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                printABC.printB();
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                printABC.printC();
            }
        }).start();


    }

}
