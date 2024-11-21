package org.example;

public class Car  extends Thread{
    public int order;
    protected int parking_time;
    public int arrival;
    private int gate;
    protected int waiting_time;
    public Car(int o ,int p, int g, int a){
        order=o; parking_time=p; gate=g; arrival =a;
    }
    public int getOrder(){
        return order;
    }
    public int getParking_time(){
        return parking_time;
    }
    public int getGate(){
        return gate;
    }
//    public int calculateWaitingTime() {
//        return (int) (System.currentTimeMillis() - arrival) / 1000;
//    }

    public void run() {
        try {

            // Attempt to park in the parking lot

            Main.parkingLot.park(this);

            // Simulate the car being parked for the duration of parking time
            Thread.sleep(parking_time * 1000L);

            // Leave the parking lot after parking duration
            Main.parkingLot.release(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
