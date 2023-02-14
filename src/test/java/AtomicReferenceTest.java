import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicReferenceTest {

    private AtomicReference<Integer> atomicReference = new AtomicReference<>(1);

    private AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(1, 0);

    /**
     * AtomicReference对对象的包装支持原子操作
     * 必须内存的对象值等于期望值时才更新新值
     */
    @Test
    public void test01(){
        // new Integer(1) 更新失败, new出来的是一个新对象
        boolean b = atomicReference.compareAndSet(new Integer(1), 2);
        System.out.println(b);
    }

    /**
     * AtomicStampedReference对AtomicReference的增强, 增加了时间戳字段, 为了解决cas的ABA问题
     * ABA问题:
     * 线程1取到对象A进行了更新, 在把对象A写入内存前, 其他线程把内存中的A对象修改为B对象,又修改为A对象,
     * 虽然此时内存中依然是A对象但是对象中的属性可能已经改变
     * 如果线程1去更新A的对象值,会覆盖其他线程的操作
     * 此时需要添加一个版本号字段,版本号一直累加
     * 基本类型的数值可忽略ABA问题
     */
    @Test
    public void test02(){
        boolean b = atomicStampedReference.compareAndSet(atomicStampedReference.getReference(), 2, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
        System.out.println(b);
        System.out.println(atomicStampedReference.getReference());
        System.out.println(atomicStampedReference.getStamp());

    }
}

