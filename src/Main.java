/**
 * Created by Mark Gavin on 7/17/2017.
 */

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice = 0;

        // Initialize stations
        Station[] stationList = new Station[8];
        for (int x = 0; x < 8; x++) {
            stationList[x] = new Station(x);
            System.out.println(stationList[x]);
            if (x == 7)
                stationList[x].setNextStop(stationList[0]);
        }
        for (int x = 0; x < 8; x++) {
            if (x == 7)
                stationList[x].setNextStop(stationList[0]);
            else
                stationList[x].setNextStop(stationList[x + 1]);
        }

        // Create trains / passengers
        do {
            int trainCapacity, passengersToSpawn, trainsToSpawn, whereToSpawn, passengerDestination;
            System.out.println("1 : Spawn Train");
            System.out.println("2 : Spawn passengers");
            System.out.println("3 : Exit");
            System.out.println("Enter choice: ");
            choice = sc.nextInt();

            switch(choice) {
                case 1: // Train
                    System.out.println("Enter capacity: ");
                    trainCapacity = sc.nextInt(); // Get train capacity
                    System.out.println(stationList[0]);
                    System.out.print("Enter no. of trains to spawn: ");
                    trainsToSpawn = sc.nextInt(); // Get how many to spawn  d
                    while(trainsToSpawn > 0) {
                        stationList[0].spawnTrain(trainCapacity);//tinatapos muna to bago magmove
                        //stationList[0].getWaiting().size();
                        trainsToSpawn--;
                    }
                    break;
                case 2: // Passenger
                    System.out.print("Enter which station (1-8) to SPAWN passengers: ");
                    whereToSpawn = sc.nextInt();
                    if (whereToSpawn >= 1 || whereToSpawn < 8) {
                        System.out.print("Enter which station (1-8) to DROP OFF passengers: ");
                        passengerDestination = sc.nextInt();
                        if (passengerDestination >= 1 || passengerDestination <= 8) {
                            System.out.print("Enter no. of passengers to spawn: ");
                            passengersToSpawn = sc.nextInt(); // Get how many to spawn  d
                            System.out.println("papasok");
                            whereToSpawn--;
                            passengerDestination--;
                            while (passengersToSpawn > 0) {
                                stationList[whereToSpawn].spawnPassenger(passengerDestination, stationList[whereToSpawn], stationList[passengerDestination]); //spawn passenger
                                passengersToSpawn--;
                            }
                        }
                    }
                    else {
                        System.out.println("Invalid station.");
                    }
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid input, doing nothing...");
                    break;
            }
        } while (choice != 3);
        System.out.println("Exiting program...");
    }
}