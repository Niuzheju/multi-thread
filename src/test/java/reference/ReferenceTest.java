package reference;

import org.junit.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * 对象的引用类型
 * 强, 软, 弱, 虚
 * 强引用就是普通的引用
 *
 * @author niuzheju
 * @date 17:23 2021/4/14
 */
public class ReferenceTest {

    /**
     * 软引用
     * 软引用是用来描述一些非必需但仍有用的对象。在内存足够的时候，软引用对象不会被回收，只有在内存不足时，系统则会回收软引用对象，
     * 如果回收了软引用对象之后仍然没有足够的内存，才会抛出内存溢出异常。这种特性常常被用来实现缓存技术，比如网页缓存，图片 缓存等。
     */
    @Test
    public void test01() {
        SoftReference<String> softReference = new SoftReference<>("soft");
        System.out.println(softReference.get());

    }

    /**
     * 弱引用
     * 弱引用的引用强度比软引用要更弱一些，无论内存是否足够，只要 JVM 开始进行垃圾回收，那些被弱引用关联的对象都会被回收。
     */
    @Test
    public void test02() {
        WeakReference<String> weakReference = new WeakReference<>("weak");
        System.out.println(weakReference.get());
    }

    /**
     * 虚引用
     * 虚引用是最弱的一种引用关系，如果一个对象仅持有虚引用，那么它就和没有任何引用一样，它随时可能会被回收，
     * 在 JDK1.2 之后，用PhantomReference 类来表示，通过查看这个类的源码，发现它只有一个构造函数和一个 get()方法，
     * 而且它的 get()方法仅仅是返回一个null，也就是说将永远无法通过虚引用来获取对象，虚引用必须要和 ReferenceQueue 引用队列一起使用。
     */
    @Test
    public void test03() {
        PhantomReference<String> phantomReference = new PhantomReference<>("phantom", new ReferenceQueue<>());
        System.out.println(phantomReference.get());

    }
}
