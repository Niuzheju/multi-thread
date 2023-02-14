package queue.fileindex;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;

/**
 * @author niuzheju
 * @date 16:34 2021/9/13
 */
public class FileCrawler implements Runnable {

    private final BlockingQueue<File> fileQueue;
    private final FileFilter fileFilter;
    private final File root;

    public FileCrawler(BlockingQueue<File> fileQueue, FileFilter fileFilter, File root) {
        this.fileQueue = fileQueue;
        this.fileFilter = fileFilter;
        this.root = root;
    }

    @Override
    public void run() {
        try {
            crawl(root);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void crawl(File root) throws InterruptedException {
        File[] files = root.listFiles(fileFilter);
        if (files != null) {
            for (File file : files) {
                fileQueue.put(file);
                if (file.isDirectory()) {
                    crawl(file);
                }
            }
        }
    }
}
