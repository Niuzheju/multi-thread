package codeFromBook;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 车辆追踪
 * 不可变类的构造
 * @Author niuzheju
 * @Date 14:38 2023/7/5
 */
public class MonitorVehicleTracker {

    private final Map<String, MutablePoint> locations;

    public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
        this.locations = deepCopy(locations);
    }

    public synchronized Map<String, MutablePoint> getLocations() {
        return deepCopy(this.locations);
    }

    public synchronized MutablePoint getLocation(String id) {
        MutablePoint point = this.locations.get(id);
        return point == null ? null : new MutablePoint(point);
    }

    public synchronized void setLocation(String id, int x, int y) {
        MutablePoint mp = this.locations.get(id);
        if (mp == null) {
            throw new IllegalArgumentException("No such ID:" + id);
        }
        mp.x = x;
        mp.y = y;
    }

    private Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> locations) {
        Map<String, MutablePoint> map = new HashMap<>(locations.size());
        for (Map.Entry<String, MutablePoint> entry : locations.entrySet()) {
            map.put(entry.getKey(), new MutablePoint(entry.getValue()));
        }
        return Collections.unmodifiableMap(map);
    }

    public static void main(String[] args) {
        Map<String, MutablePoint> locations = new HashMap<>();
        locations.put("特斯拉", new MutablePoint(101, 222));
        locations.put("奥迪", new MutablePoint(110, 342));
        locations.put("奔驰", new MutablePoint(199, 567));
        MonitorVehicleTracker monitorVehicleTracker = new MonitorVehicleTracker(locations);

        new Thread(() -> {
            long start = System.currentTimeMillis();
            while ((System.currentTimeMillis() - start) < 5000) {
                System.out.println(monitorVehicleTracker.getLocations());
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            monitorVehicleTracker.setLocation("特斯拉", 1000, 2000);
            monitorVehicleTracker.setLocation("比亚迪", 1000, 2000);
            monitorVehicleTracker.setLocation("奥迪", 4000, 2000);
        }).start();


    }
}

class MutablePoint {

    public int x, y;

    public MutablePoint() {
    }

    public MutablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public MutablePoint(MutablePoint p) {
        this.x = p.x;
        this.y = p.y;
    }

    @Override
    public String toString() {
        return "MutablePoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
