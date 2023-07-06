package codeFromBook;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 线程安全性委托给多个变量
 * @Author niuzheju
 * @Date 15:10 2023/7/6
 */
public class VisualComponent {

    private final List<KeyListener> keyListeners = new CopyOnWriteArrayList<>();

    private final List<MouseListener> mouseListeners = new CopyOnWriteArrayList<>();

    public void addKeyListener(KeyListener keyListener) {
        keyListeners.add(keyListener);
    }

    public void addMouseListener(MouseListener mouseListener) {
        mouseListeners.add(mouseListener);
    }

    public void removeKeyListener(KeyListener keyListener) {
        keyListeners.remove(keyListener);
    }

    public void removeMouseListener(MouseListener mouseListener) {
        mouseListeners.remove(mouseListener);
    }

    static class KeyListener {

    }

    static class MouseListener {

    }
}
