package queue;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author niuzheju
 * @date 16:14 2021/4/14
 */
public class MyBlockingQueue {

    private int[] data = new int[10];
    private int putIndex;
    private int getIndex;
    private int size;

    public synchronized void put(int val) {
        if (size == data.length) {
            try {
                System.out.println("添加元素阻塞中......size=" + size + ", length=" + data.length);
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            put(val);
        } else {
            data[putIndex] = val;
            putIndex++;
            if (putIndex == data.length) {
                putIndex = 0;
            }
            size++;
            this.notifyAll();
        }
    }

    public synchronized int get() {
        if (size == 0) {
            try {
                System.out.println("获取元素阻塞中......size=" + size);
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return get();
        } else {
            int val = data[getIndex];
            getIndex++;
            if (getIndex == data.length) {
                getIndex = 0;
            }
            size--;
            this.notifyAll();
            return val;
        }
    }

    public static void main(String[] args) {

        MyBlockingQueue myBlockingQueue = new MyBlockingQueue();
        Random random = new Random();

        ExecutorService executorService = Executors.newFixedThreadPool(30);
        for (int i = 0; i < 30; i++) {
            executorService.submit(() -> {
                try {
                    TimeUnit.SECONDS.sleep(random.nextInt(5));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myBlockingQueue.put(2);
            });
        }

        ExecutorService executorService1 = Executors.newFixedThreadPool(30);
        for (int i = 0; i < 30; i++) {
            executorService1.submit(() -> {
                try {
                    TimeUnit.SECONDS.sleep(random.nextInt(5));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(myBlockingQueue.get());
            });
        }

    }

}
