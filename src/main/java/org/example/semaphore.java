package org.example;

import java.util.LinkedList;
import java.util.Queue;

class semaphore {
    private int value;
    private final Queue<Car> queue;
    private final Object lock = new Object();

    public semaphore(int value) {
        this.value = value;
        queue = new LinkedList<>();
    }

    public void P(Car car) throws InterruptedException {
        synchronized (lock) {
            value--;
            if (value < 0) {
                queue.add(car);
                System.out.println("Car " + car.getOrder() + " from Gate " + car.getGate() + " waiting for a spot.");
                lock.wait(); // Car waits for a spot to become available
            }
        }
    }

    public void V() {
        synchronized (lock) {
            value++;
            if (value <= 0 && !queue.isEmpty()) {
                Car car = queue.remove();

                lock.notify(); // Notify a waiting car
            }
        }
    }
}
