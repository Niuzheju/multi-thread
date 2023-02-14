package collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author niuzheju
 * @date 15:22 2021/8/19
 */
public class CollectionThreadSafeTest {

    /**
     * ArrayList的不安全在于
     * 1. 多线程修改数据的同时遍历会抛出ConcurrentModificationException
     * 这是因为modCount和expectedModCount变量因为多线程变得不一致
     * 2. 多线程只修改数据但是不进行遍历,可能会出现ArrayIndexOutOfBoundsException
     * 数组越界时的元素插入失败成为null
     */
    @Test
    public void test01() throws InterruptedException {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            int e = i;
            new Thread(() -> {
                list.add(e);
            }).start();
        }

        TimeUnit.SECONDS.sleep(3);
        System.out.println(list);

    }

    /**
     * HashMap线程安全问题
     * 1. 内部数组resize时, 可能会出现多个线程同时resize创建新的数组, 最后相互覆盖导致丢失数据
     * 2. put元素时, 如果hash冲突, 多个线程往链表添加节点时可能会出现覆盖, 导致丢失数据
     * 3. 删除元素时, 多个线程同时操作, 一个线程删除的元素可能是另一个线程刚刚添加的元素
     * @throws InterruptedException
     */
    @Test
    public void test02() throws InterruptedException {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            int e = i;
            new Thread(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                map.put(e, e);

            }).start();
        }
        TimeUnit.SECONDS.sleep(10);
        System.out.println(map);

    }
}
