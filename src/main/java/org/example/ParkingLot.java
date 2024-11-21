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

        synchronized (this){
            System.out.println(car.order+" first leave: "+this.calcuate_min()+" arrive: "+car.arrival+ " : "+occupiedSpots);
            if(this.calcuate_min()<car.arrival||occupiedSpots<totalSpots){

                car.waiting_time  =0 ;
            } else {

                car.waiting_time = this.calcuate_min() - car.arrival;
            }
        if (car.arrival==0){
            car.waiting_time =0;}

        }

//        if(this.calcuate_min()==car.arrival && occupiedSpots==4){
//            waitingTime += 1 ;
//        }
        se.P(car); // Attempt to acquire a parking spot

        synchronized (this) {
            occupiedSpots++;
            parkedCars.add(car);
        }
        // System.out.println("the waiting time: "+waitingTime);
        // Calculate waiting time after acquiring a spot

        if (car.waiting_time > 0) {
            System.out.println("Car " + car.getOrder() + " from Gate " + car.getGate() + " parked after waiting for " + car.waiting_time + " units of time. (Parking Status: " + occupiedSpots + " spots occupied)");
        } else {
            System.out.println("Car " + car.getOrder() + " from Gate " + car.getGate() + " parked. (Parking Status: " + occupiedSpots + " spots occupied)");
        }
    }

    public void release(Car car) {
        synchronized (this) {
            occupiedSpots--;
            parkedCars.remove(car);
        }
        int parkingDuration = car.getParking_time();
        System.out.println("Car " + car.getOrder() + " from Gate " + car.getGate() + " left after " + parkingDuration + " units of time. (Parking Status: " + occupiedSpots + " spots occupied)");
        se.V(); // Release the parking spot and notify waiting cars
    }
    public int getOccupiedSpots(){
        return occupiedSpots;
    }
//    private  synchronized  int calcuate_min(){
//
//        if (occupiedSpots >= totalSpots) {
//            mini = Integer.MAX_VALUE;
//
//            for (Car car : parkedCars) {
//                int departureTime = car.arrival + car.getParking_time(); // Time when this car leaves
//                mini = Math.min(mini, departureTime);}
//        } else {
//            //l
//            mini = 0;
//        }
//        return mini;
//    }
private synchronized int calcuate_min() {
    if (occupiedSpots == 0) {
        return Integer.MAX_VALUE; // No cars parked, so no waiting time
    }
    mini = Integer.MAX_VALUE;
    for (Car car : parkedCars) {
        int departureTime = car.arrival + car.getParking_time(); // Calculate when the car will leave
        mini = Math.min(mini, departureTime);
    }
    return mini; // Returns the earliest departure time
}
}
