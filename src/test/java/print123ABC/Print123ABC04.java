package print123ABC;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

public class Print123ABC04 {
    public static void main(String[] args) {
        char[] charI = new char[]{'1', '2', '3', '4', '5', '6', '7'};
        char[] charC = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        // 阻塞队列, 往里面存储数据时会阻塞当前队列直到有其他线程取走这个数据
        TransferQueue<Character> queue = new LinkedTransferQueue<>();
        new Thread(() -> {
            for (char c : charI) {
                try {
                    queue.transfer(c);
                    System.out.print(queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            for (char c : charC) {
                try {
                    System.out.print(queue.take());
                    queue.transfer(c);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
