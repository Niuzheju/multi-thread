package collection;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author niuzheju
 * @date 16:57 2021/4/14
 */
public class ThreadSafeCollection {


    /**
     * CopyOnWriteArrayList
     * 是指在写的时候，不会直接操作源数据，而是先copy 一份数据进行修改，然后通过悲观锁或者乐观锁的方式写回。这样的好处是，读不用加锁。
     * CopyOnWriteArrayList 读操作的时候不会加锁，只有写的时候才会加同步锁，所以是线程安全的 ArrayList。
     */
    @Test
    public void test01() throws InterruptedException {
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 20; i++) {
            int e = i;
            new Thread(() -> {
                copyOnWriteArrayList.add(e);
            }).start();
        }

        TimeUnit.SECONDS.sleep(3);
        System.out.println(copyOnWriteArrayList);
    }

    /**
     * CopyOnWriteArraySet
     * 用array 实现的一个线程安全的 Set .保证所有元素不重复，封装的是CopyOnWriteArrayList
     */
    @Test
    public void test02() throws InterruptedException {
        CopyOnWriteArraySet<Integer> copyOnWriteArraySet = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 20; i++) {
            int e = i;
            new Thread(() -> {
                copyOnWriteArraySet.add(e);
            }).start();
        }

        TimeUnit.SECONDS.sleep(3);
        System.out.println(copyOnWriteArraySet);
    }

    /**
     * Vector
     *
     * @throws InterruptedException
     */
    @Test
    public void test03() throws InterruptedException {
        Vector<Integer> vector = new Vector<>();
        for (int i = 0; i < 20; i++) {
            int e = i;
            new Thread(() -> {
                vector.add(e);
            }).start();
        }

        TimeUnit.SECONDS.sleep(3);
        System.out.println(vector);

    }

    /**
     * Hashtable
     */
    @Test
    public void test04() {
        Hashtable<Integer, Integer> hashtable = new Hashtable<>();
        hashtable.put(1, 1);
        hashtable.put(2, 1);
        hashtable.put(3, 1);

        System.out.println(hashtable);

    }

    /**
     * Collections.synchronizedMap
     */
    @Test
    public void test05() {
        Map<Integer, Integer> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
        synchronizedMap.put(1, 1);
        synchronizedMap.put(2, 1);
        synchronizedMap.put(3, 1);
        System.out.println(synchronizedMap);
    }

    /**
     * ConcurrentHashMap
     */
    @Test
    public void test06() {
        ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put(1, 1);
        concurrentHashMap.put(2, 1);
        concurrentHashMap.put(3, 1);
        System.out.println(concurrentHashMap);
    }

    /**
     * Collections.synchronizedList
     */
    @Test
    public void test07() {
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        synchronizedList.add(1);
        synchronizedList.add(2);
        synchronizedList.add(3);
        System.out.println(synchronizedList);
    }

    /**
     * Collections.synchronizedSet
     */
    @Test
    public void test08() {
        Set<String> synchronizedSet = Collections.synchronizedSet(new HashSet<>());
        synchronizedSet.add("java");
        synchronizedSet.add("php");
        synchronizedSet.add("python");
        System.out.println(synchronizedSet);
    }

    /**
     * ArrayBlockingQueue
     */
    @Test
    public void test09() {
        ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<>(10);
        arrayBlockingQueue.add(1);
        arrayBlockingQueue.add(2);
        arrayBlockingQueue.add(3);
        System.out.println(arrayBlockingQueue);
    }

    /**
     * ConcurrentLinkedQueue
     */
    @Test
    public void test10() {
        ConcurrentLinkedQueue<Integer> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        concurrentLinkedQueue.add(1);
        concurrentLinkedQueue.add(2);
        concurrentLinkedQueue.add(3);
        System.out.println(concurrentLinkedQueue);

    }
}
