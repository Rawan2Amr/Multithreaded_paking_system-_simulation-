package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static ParkingLot parkingLot =new ParkingLot();
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        File myFile = new File("D:\\study\\level5\\OS\\Multithreaded_paking_system\\src\\parking");
        Scanner myReader = new Scanner(myFile);
        List<Gate> gates= new ArrayList<>();
        Gate gate1= new Gate(1);
        Gate gate2= new Gate(2);
        Gate gate3= new Gate(3);
        gates.add(gate1);gates.add(gate2);gates.add(gate3);
        while(myReader.hasNext()){
            String data = myReader.nextLine();
            data=data.replace(',',' ');
            String [] datai = data.split(" ");
            int gate = Integer.parseInt(datai[1]);
            int car = Integer.parseInt(datai[4]);
            int arrival = Integer.parseInt(datai[7]);
            int park = Integer.parseInt(datai[10]);
            gates.get(gate - 1).add_car(arrival,new Car(car,park,gate, arrival));

        }
        for (Gate gate : gates) {
            gate.start();
        }
        int total_cars=0;
        for (Gate gate : gates) {
            Map <Integer,Car> map=gate.map;
            for(Map.Entry<Integer,Car> entry : map.entrySet()){
                Car car = entry.getValue();
                car.join();
            }
            gate.join();
            total_cars+=gate.car_served;
        }
        System.out.println("Total Cars Served: "+total_cars);
        System.out.println("Current cars in parking: "+parkingLot.getOccupiedSpots());
        System.out.println("Details: ");
        for (Gate gate : gates) {
           System.out.println("Gate "+gate.number+" served "+ gate.car_served+" cars.");
        }
    }
}