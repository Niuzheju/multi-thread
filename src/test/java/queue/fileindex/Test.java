package queue.fileindex;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author niuzheju
 * @date 16:59 2021/9/13
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<File> queue = new ArrayBlockingQueue<>(10);
        FileFilter fileFilter = pathname -> !pathname.isHidden();
        File root = new File("E:\\");
        FileCrawler fileCrawler = new FileCrawler(queue, fileFilter, root);
        Indexer indexer = new Indexer(queue);
        new Thread(fileCrawler).start();
        Thread thread = new Thread(indexer);
        thread.start();
        TimeUnit.SECONDS.sleep(10L);
        indexer.showIndexedFile();
        thread.interrupt();

    }
}
