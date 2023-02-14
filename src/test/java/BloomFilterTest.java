import java.util.BitSet;
import java.util.HashSet;

/**
 * @author niuzheju
 * @date 16:34 2021/3/24
 */
public class BloomFilterTest {

    private static int SIZE;

    private BitSet bits;

    private SimpleHash[] func;

    private Double autoClearRate;

    private HashSet<Integer> useCount = new HashSet<>(100);

    /**
     * 默认中等程序的误判率
     *
     * @param dataCount 处理数据量
     */
    public BloomFilterTest(long dataCount) {
        this(MisjudgmentRate.MIDDLE, dataCount, null);
    }

    /**
     * @param rate          误判率
     * @param dataCount     数据量
     * @param autoClearRate 自动清空过滤器内部信息的使用比率
     */
    public BloomFilterTest(MisjudgmentRate rate, long dataCount, Double autoClearRate) {
        long bitSize = rate.seeds.length * dataCount;
        if (bitSize < 0 || bitSize > Integer.MAX_VALUE) {
            throw new RuntimeException("位数太大溢出了，请降低误判率或者降低数据大小");
        }
        int[] SEEDS = rate.seeds;
        SIZE = (int) bitSize;
        func = new SimpleHash[SEEDS.length];
        for (int i = 0; i < func.length; i++) {
            func[i] = new SimpleHash(SIZE, SEEDS[i]);
        }
        bits = new BitSet(SIZE);
        this.autoClearRate = autoClearRate;
    }

    /**
     * 添加值
     */
    public void add(Object value) {
        checkNeedClear();

        for (SimpleHash simpleHash : func) {
            int hash = simpleHash.hash(value);
            bits.set(hash, true);
            useCount.add(hash);
        }
    }

    /**
     * 是否已经包含指定的值
     */
    public boolean contains(Object value) {
        boolean ret = true;
        for (SimpleHash simpleHash : func) {
            ret = ret && bits.get(simpleHash.hash(value));
        }
        return ret;
    }

    /**
     * 检查是否需要清除
     */
    private void checkNeedClear() {
        if (autoClearRate != null) {
            if (getUseRate() >= autoClearRate) {
                synchronized (this) {
                    if (getUseRate() >= autoClearRate) {
                        bits.clear();
                    }
                }
            }
        }

    }

    /**
     * 获取使用率
     *
     * @return 使用率
     */
    public double getUseRate() {
        return (double) useCount.size() / (double) SIZE;
    }

    public static class SimpleHash {

        private int cap;

        private int seed;

        public SimpleHash(int cap, int seed) {
            this.cap = cap;
            this.seed = seed;
        }

        public int hash(Object value) {
            int h;
            return value == null ? 0 : Math.abs(seed * (cap - 1) & ((h = value.hashCode()) ^ h >>> 16));
        }
    }


    public enum MisjudgmentRate {

        /**
         * 4位
         */
        VERY_SMALL(new int[]{2, 3, 5, 7}),

        /**
         * 8位
         */
        SMALL(new int[]{2, 3, 5, 7, 11, 13, 17, 19}),

        /**
         * 16位
         */
        MIDDLE(new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53}),

        /**
         * 32位
         */
        HIGH(new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59,
                61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131});

        private int[] seeds;

        MisjudgmentRate(int[] seeds) {
            this.seeds = seeds;
        }

        public int[] getSeeds() {
            return seeds;
        }

        public void setSeeds(int[] seeds) {
            this.seeds = seeds;
        }
    }

    public static void main(String[] args) {
        BloomFilterTest bloomFilterTest = new BloomFilterTest(10);
        for (int i = 0; i < 10; i++) {
            bloomFilterTest.add(i);
        }
        boolean contains = bloomFilterTest.contains(1);
        System.out.println(contains);
        System.out.println(bloomFilterTest.bits);
        System.out.println(bloomFilterTest.getUseRate() * 100 + "%");

    }


}
