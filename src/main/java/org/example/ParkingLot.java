package org.example;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private final int totalSpots = 4;
    private int occupiedSpots = 0;
    private final semaphore se = new semaphore(totalSpots);
    private final List<Car> parkedCars = new ArrayList<>();

    public void park(Car car) throws InterruptedException {
        long arrivalTimeMillis = System.currentTimeMillis();

        se.P(car); // Attempt to acquire a parking spot

        synchronized (this) {
            occupiedSpots++;
            parkedCars.add(car);
        }

        // Calculate waiting time after acquiring a spot
        int waitingTime = (int) ((System.currentTimeMillis() - arrivalTimeMillis) / 1000);
        System.out.println(waitingTime);
        if (waitingTime > 0) {
            System.out.println("Car " + car.getOrder() + " from Gate " + car.getGate() + " parked after waiting for " + waitingTime + " units of time. (Parking Status: " + occupiedSpots + " spots occupied)");
        } else {
            System.out.println("Car " + car.getOrder() + " from Gate " + car.getGate() + " parked. (Parking Status: " + occupiedSpots + " spots occupied)");
        }
    }

    public void release(Car car) {
        synchronized (this) {
            occupiedSpots--;
            parkedCars.remove(car);
        }

        se.V(); // Release the parking spot and notify waiting cars
        System.out.println("Car " + car.getOrder() + " from Gate " + car.getGate() + " left after " + car.getParking_time() + " units of time. (Parking Status: " + occupiedSpots + " spots occupied)");
    }
    public int getOccupiedSpots(){
        return occupiedSpots;
    }
}
