package codeFromBook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author niuzheju
 * @Date 15:21 2023/7/10
 */
public class ListHelper<E> {

    private final List<E> list = Collections.synchronizedList(new ArrayList<>());

    public void putIfAbsent(E e) {
        //外部加锁，要和list内部是同一把锁才能保证原子性
        synchronized (list) {
            boolean absent = !list.contains(e);
            if (absent) {
                list.add(e);
            }
        }
    }
}
