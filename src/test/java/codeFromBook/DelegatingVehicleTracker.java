package codeFromBook;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 车辆追踪
 * 不可变类的构造
 *
 * @Author niuzheju
 * @Date 14:38 2023/7/5
 */
public class DelegatingVehicleTracker {

    private final ConcurrentHashMap<String, Point> locations;
    private final Map<String, Point> unmodifiableMap;

    public DelegatingVehicleTracker(Map<String, Point> locations) {
        this.locations = new ConcurrentHashMap<>(locations);
        this.unmodifiableMap = Collections.unmodifiableMap(this.locations);
    }

    public Map<String, Point> getLocations() {
        return unmodifiableMap;
    }

    public Point getLocation(String id) {
        return locations.get(id);
    }

    public void setLocation(String id, int x, int y) {
        if (locations.replace(id, new Point(x, y)) == null) {
            throw new IllegalArgumentException("No such ID:" + id);
        }
    }

    public static void main(String[] args) {
        Map<String, Point> locations = new HashMap<>();
        locations.put("特斯拉", new Point(101, 222));
        locations.put("奥迪", new Point(110, 342));
        locations.put("奔驰", new Point(199, 567));
        DelegatingVehicleTracker delegatingVehicleTracker = new DelegatingVehicleTracker(locations);

        new Thread(() -> {
            long start = System.currentTimeMillis();
            while ((System.currentTimeMillis() - start) < 5000) {
                System.out.println(delegatingVehicleTracker.getLocations());
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            delegatingVehicleTracker.setLocation("特斯拉", 1000, 2000);
            delegatingVehicleTracker.setLocation("奔驰", 1000, 2000);
            delegatingVehicleTracker.setLocation("奥迪", 4000, 2000);
        }).start();


    }
}

class Point {

    public final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
