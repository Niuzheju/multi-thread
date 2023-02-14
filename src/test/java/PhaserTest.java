import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * Phaser 阶段加线程数控制
 * @Author niuzheju
 * @Date 2020/4/22 15:58
 */
public class PhaserTest {

    static Random random = new Random();

    static MarriagePhaser marriagePhaser = new MarriagePhaser();

    static void milliSleep(int milli) {
        try {
            TimeUnit.MILLISECONDS.sleep(milli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        marriagePhaser.bulkRegister(7);
        for (int i = 0; i < 5; i++) {
            new Thread(new Person("p" + i)).start();
        }
        new Thread(new Person("新郎")).start();
        new Thread(new Person("新娘")).start();
    }

    static class MarriagePhaser extends Phaser {
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase) {
                case 0:
                    System.out.println("所有人到齐了! " + registeredParties);
                    System.out.println();
                    return false;
                case 1:
                    System.out.println("所有人吃完了! " + registeredParties);
                    System.out.println();
                    return false;
                case 2:
                    System.out.println("所有人离开了! " + registeredParties);
                    System.out.println();
                    return false;
                case 3:
                    System.out.println("婚礼结束, 新郎新娘抱抱! " + registeredParties);
                    System.out.println();
                    return true;
                default:
                    // 返回true表示程序将会结束, 返回false表示程序还有下一阶段, 不会停止
                    return true;
            }
        }
    }

    static class Person implements Runnable {

        String name;

        public Person(String name) {
            this.name = name;
        }

        public void arrive() {
            milliSleep(random.nextInt(1000));
            System.out.printf("%s 到达现场!\n", name);
            //7个线程执行到此处时会触发case 0
            marriagePhaser.arriveAndAwaitAdvance();
        }

        public void eat() {
            milliSleep(random.nextInt(1000));
            System.out.printf("%s 吃完!\n", name);
            //7个线程执行到此处时会触发case 1
            marriagePhaser.arriveAndAwaitAdvance();
        }

        public void leave() {
            milliSleep(random.nextInt(1000));
            System.out.printf("%s 离开!\n", name);
            //7个线程执行到此处时会触发case 2
            marriagePhaser.arriveAndAwaitAdvance();
        }

        private void hug() {
            if ("新娘".equals(name) || "新郎".equals(name)) {
                milliSleep(random.nextInt(1000));
                System.out.printf("%s 洞房!\n", name);
                //2个线程执行到此处时会触发case 3, 因为其他线程注销了
                marriagePhaser.arriveAndAwaitAdvance();
            } else {
                marriagePhaser.arriveAndDeregister();
            }
        }

        @Override
        public void run() {
            arrive();
            eat();
            leave();
            hug();
        }
    }
}
