package com.java.collections;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Iterator;

public final class ConcurrentCollectionsDemo {
    private ConcurrentCollectionsDemo() {
    }

    public static void main(String[] args) throws InterruptedException {
        demonstrateAtomicMapUpdate();
        demonstrateCopyOnWriteSnapshot();
        demonstrateOrderedRangeView();
        demonstrateBoundedHandoff();
    }

    private static void demonstrateAtomicMapUpdate() {
        Map<String, Integer> visits = new ConcurrentHashMap<String, Integer>();
        visits.merge("orders", 1, Integer::sum);
        visits.merge("orders", 1, Integer::sum);
        System.out.println("Atomic per-key map update: " + visits.get("orders"));
    }

    private static void demonstrateCopyOnWriteSnapshot() {
        CopyOnWriteArrayList<String> listeners =
                new CopyOnWriteArrayList<String>();
        listeners.add("audit");
        Iterator<String> snapshot = listeners.iterator();
        listeners.add("metrics");

        while (snapshot.hasNext()) {
            System.out.println("Existing iterator snapshot: " + snapshot.next());
        }
        System.out.println("Current list: " + listeners);
    }

    private static void demonstrateOrderedRangeView() {
        ConcurrentSkipListMap<Integer, String> events =
                new ConcurrentSkipListMap<Integer, String>();
        events.put(10, "accepted");
        events.put(20, "processed");
        events.put(30, "archived");

        ConcurrentNavigableMap<Integer, String> middle =
                events.subMap(10, true, 30, false);
        System.out.println("Ordered range view: " + middle);
    }

    private static void demonstrateBoundedHandoff() throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(1);
        queue.put("one admitted item");
        System.out.println("Bounded queue capacity: " + queue.remainingCapacity());
        System.out.println("Consumer received: " + queue.take());
    }
}
