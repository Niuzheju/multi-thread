package synchronizedTest;

/**
 * synchronized可重入
 */
public class SynchronizedTest03 {
    public static void main(String[] args) {
        T t = new T();
        t.m();
    }

    private static class T extends TT{

        public synchronized void m(){
            n();
        }

        public synchronized void n(){
           super.n();
        }
    }

    private static class TT{
        private synchronized void n(){
            System.out.println("n");
        }
    }
}
