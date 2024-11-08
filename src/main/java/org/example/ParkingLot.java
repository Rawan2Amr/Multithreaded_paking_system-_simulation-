package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.min;

public class ParkingLot {
    private final int totalSpots = 4;
    private int occupiedSpots = 0;
    private final semaphore se = new semaphore(totalSpots);
    private final List<Car> parkedCars = new ArrayList<>();
    private int mini= Integer.MAX_VALUE;

    public void park(Car car) throws InterruptedException {
        long arrivalTimeMillis = System.currentTimeMillis();

        int waitingTime = this.calcuate_min()-car.arrival;
        se.P(car); // Attempt to acquire a parking spot

        synchronized (this) {
            occupiedSpots++;
            parkedCars.add(car);
        }
        System.out.println("the waiting time: "+waitingTime);
        // Calculate waiting time after acquiring a spot

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
    private int calcuate_min(){

        if (occupiedSpots >= totalSpots) {
            mini = Integer.MAX_VALUE;

            for (Car car : parkedCars) {
                int departureTime = car.arrival + car.getParking_time(); // Time when this car leaves
                mini = Math.min(mini, departureTime);}
        } else {
            //l
            mini = 0;
        }
        return mini;
    }
}
