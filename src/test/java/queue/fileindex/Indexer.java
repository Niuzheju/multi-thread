package queue.fileindex;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author niuzheju
 * @date 16:51 2021/9/13
 */
public class Indexer implements Runnable {

    private final BlockingQueue<File> fileQueue;
    private final ConcurrentHashMap<String, List<File>> indexedFile = new ConcurrentHashMap<>(64);

    public Indexer(BlockingQueue<File> fileQueue) {
        this.fileQueue = fileQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                indexFile(fileQueue.take());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void indexFile(File file) {
        List<File> files = indexedFile.computeIfAbsent(file.getName(), k -> new ArrayList<>());
        files.add(file);

    }

    public void showIndexedFile() {
        int count = 0;
        for (List<File> entry : indexedFile.values()) {
            for (File file : entry) {
                System.out.println(file.getName() + ",  " + file.getAbsolutePath());
                count++;
            }
        }
        System.out.println(count);
    }
}
