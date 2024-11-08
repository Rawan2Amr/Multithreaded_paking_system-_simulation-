package org.example;

import java.util.HashMap;
import java.util.Map;

public class Gate extends Thread{
    public int number;
    public int car_served=0;
    public int intial_time=0;
    public Map<Integer,Car> map = new HashMap<>();
    public Gate (int n){number=n;}
    public void add_car(int arrival_time,Car car){
        map.put(arrival_time,car);
    }
    public void run(){
        for(Map.Entry<Integer,Car> entry : map.entrySet()){
            try{
                sleep((entry.getKey()-intial_time)* 1000L);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            intial_time=entry.getKey();

            Car car = entry.getValue();
            System.out.println("Car "+car.getOrder()+" from Gate "+number+"arrived at time "+entry.getKey());
            car.start();
            car_served++;
        }
    }
}
