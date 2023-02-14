import org.junit.Test;

public class CASTest {

    private int memoryValue = 1;

    /**
     * cas逻辑
     * 从内存中取出值expect
     * 进行计算得到newValue
     * 当且仅当内存中的值等于expect时把newValue设置到内存中
     * 如果不相等，重新从内存中取值，重新计算并设置
     * 同时cas又称为轻量级锁, 自旋锁
     */
    @Test
    public void casTest(){
        int expect = this.getValue();
        int newValue = expect + 1;
        boolean b = cas(newValue, expect);
        System.out.println(b);
        while (!cas(newValue, expect)){
            expect = this.getValue();
            newValue = expect + 1;
        }
    }

    private boolean cas(int newValue, int expect) {
        int memoryValue = getValue();
        if (memoryValue == expect){
            this.memoryValue = newValue;
            return true;
        }
        return false;
    }

    private int getValue(){
        return this.memoryValue;
    }
}
