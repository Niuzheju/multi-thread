package queue;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author niuzheju
 * @date 15:05 2021/4/6
 */
public class QueueTest {

    /**
     * ArrayBlockingQueue
     * 底层为数组的队列
     *
     * @throws InterruptedException
     */
    @Test
    public void test01() throws InterruptedException {
        ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<>(5);
        arrayBlockingQueue.add(1);
        arrayBlockingQueue.add(2);
        arrayBlockingQueue.add(3);
        arrayBlockingQueue.add(4);
        arrayBlockingQueue.put(5);
        // 往队列末尾添加元素, 如果队列已满则阻塞等待队列可用
        arrayBlockingQueue.put(6);
        // 取出队列第一个元素
        System.out.println(arrayBlockingQueue.peek());

    }

    /**
     * LinkedBlockingQueue
     * 底层为链表的队列
     */
    @Test
    public void test02() {
        LinkedBlockingQueue<Integer> linkedBlockingDeque = new LinkedBlockingQueue<>(3);
        linkedBlockingDeque.add(1);
        linkedBlockingDeque.add(2);
        linkedBlockingDeque.add(3);
        System.out.println(linkedBlockingDeque.peek());

    }

    /**
     * PriorityBlockingQueue
     * 无界队列, 底层是数组
     */
    @Test
    public void test03() {
        // 仅仅初始化底层数组的大小, 并不会限制元素个数
        PriorityBlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue<>(3);
        priorityBlockingQueue.add(1);
        priorityBlockingQueue.offer(2);
        priorityBlockingQueue.offer(3);
        priorityBlockingQueue.offer(3);
        System.out.println(priorityBlockingQueue.peek());
        System.out.println(priorityBlockingQueue);

    }

    /**
     * DelayQueue
     *
     * @throws InterruptedException
     */
    @Test
    public void test04() throws InterruptedException {
        DelayQueue<Delayed> delayQueue = new DelayQueue<>();
        delayQueue.add(new DelayedElement(1));
        delayQueue.add(new DelayedElement(2));
        System.out.println(delayQueue);
        System.out.println(delayQueue.poll(5, TimeUnit.SECONDS));

    }

    /**
     * SynchronousQueue 不存储元素, 一次put就阻塞直到有线程take
     *
     * @throws InterruptedException
     */
    @Test
    public void test05() throws InterruptedException {
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                String msg = synchronousQueue.take();
                System.out.println(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        synchronousQueue.put("msg2");

    }

    /**
     * LinkedTransferQueue, 支持transfer, take阻塞操作
     * 也支持存储元素
     *
     * @throws InterruptedException
     */
    @Test
    public void test06() throws InterruptedException {
        LinkedTransferQueue<String> linkedTransferQueue = new LinkedTransferQueue<>();

        new Thread(() -> {
            try {
                String s = linkedTransferQueue.take();
                System.out.println(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        linkedTransferQueue.transfer("transfer");

        linkedTransferQueue.put("msg1");
        linkedTransferQueue.add("msg2");
        System.out.println(linkedTransferQueue.toString());
    }

    @Test
    public void test07() {
        LinkedBlockingDeque<String> linkedBlockingDeque = new LinkedBlockingDeque<>();
        linkedBlockingDeque.add("msg1");
        linkedBlockingDeque.add("msg2");
        linkedBlockingDeque.addFirst("msg3");
        linkedBlockingDeque.addFirst("msg4");
        System.out.println(linkedBlockingDeque.element());
        System.out.println(linkedBlockingDeque.peekLast());
        System.out.println(linkedBlockingDeque.toString());
    }

    class DelayedElement implements Delayed {

        private int ele;

        private long firstTime = System.currentTimeMillis();

        public DelayedElement(int ele) {
            this.ele = ele;
        }

        @Override
        public String toString() {
            return ele + "";
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long delay = 3000;
            return delay - (System.currentTimeMillis() - firstTime);
        }

        @Override
        public int compareTo(Delayed o) {
            if (o instanceof DelayedElement) {
                DelayedElement delayedElement = (DelayedElement) o;
                return this.ele - delayedElement.ele;
            }
            throw new RuntimeException("Class not instanceof DelayedElement");
        }
    }
}
