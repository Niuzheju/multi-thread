package codeFromBook;

import java.util.Vector;

/**
 * 扩展现有集合类
 * @Author niuzheju
 * @Date 15:10 2023/7/10
 */
public class BetterVector<E> extends Vector<E> {

    public synchronized void putIfAbsent(E e) {
        boolean absent = !contains(e);
        if (absent) {
            add(e);
        }
    }

    public static void main(String[] args) {
        BetterVector<String> bv = new BetterVector<>();
        bv.putIfAbsent("a");
        bv.putIfAbsent("b");
        bv.putIfAbsent("a");
        System.out.println(bv);

    }
}
