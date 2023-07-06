package codeFromBook;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 可变且安全的Point类
 * @Author niuzheju
 * @Date 15:47 2023/7/6
 */
public class PublishingVehicleTracker {

    private final ConcurrentHashMap<String, SafePoint> locations;
    private final Map<String, SafePoint> unmodifiableMap;

    public PublishingVehicleTracker(Map<String, SafePoint> locations) {
        this.locations = new ConcurrentHashMap<>(locations);
        this.unmodifiableMap = Collections.unmodifiableMap(this.locations);
    }

    public Map<String, SafePoint> getLocations() {
        return unmodifiableMap;
    }

    public SafePoint getLocation(String id) {
        return locations.get(id);
    }

    public void setLocation(String id, int x, int y) {
        if (!locations.containsKey(id)) {
            throw new IllegalArgumentException("No such ID:" + id);
        }
        locations.get(id).set(x, y);
    }


    static class SafePoint {
        private int x, y;

        private SafePoint(int[] a) {
            this(a[0], a[1]);
        }

        public SafePoint(SafePoint safePoint) {
            this(safePoint.get());
        }

        public SafePoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public synchronized int[] get() {
            return new int[]{x, y};
        }

        public synchronized void set(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
