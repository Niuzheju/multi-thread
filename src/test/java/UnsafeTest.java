import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Unsafe的cas使用示例
 */
public class UnsafeTest {

    private int i = 0;

    private static UnsafeTest unsafeTest = new UnsafeTest();

    public static void main(String[] args) throws Exception {
        Field field = Unsafe.class.getDeclaredFields()[0];
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        Field iField = UnsafeTest.class.getDeclaredField("i");
        long fieldOffset = unsafe.objectFieldOffset(iField);
        System.out.println(fieldOffset);
        boolean success = unsafe.compareAndSwapInt(unsafeTest, fieldOffset, 0, 1);
        System.out.println(success);
        System.out.println(unsafeTest.i);
    }

}
