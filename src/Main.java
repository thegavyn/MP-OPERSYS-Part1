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
            if (x == 7)
                stationList[x].setNextStop(stationList[0]);
        }

        // Create trains / passengers
        do {
            int trainCapacity, passengersToSpawn, whereToSpawn, passengerDestination;
            System.out.println("1 : Spawn a train");
            System.out.println("2 : Spawn passengers");
            System.out.println("3 : Exit");
            System.out.println("Enter choice: ");
            choice = sc.nextInt();
                
            switch(choice) {
                case 1: // Train
                    System.out.println("Enter capacity: ");
                    trainCapacity = sc.nextInt(); // Get train capacity
                    stationList[0].spawnTrain(trainCapacity);
                    break;
                case 2: // Passenger
                    System.out.print("Enter which station (0-7) to SPAWN passengers: ");
                    whereToSpawn = sc.nextInt();
                    if (whereToSpawn < 0 || whereToSpawn > 7) {
                        System.out.print("Enter which station (0-7) to DROP OFF passengers: ");
                        passengerDestination = sc.nextInt();
                        if (passengerDestination < 0 || passengerDestination > 7) {
                            System.out.print("Enter no. of passengers to spawn: ");
                            passengersToSpawn = sc.nextInt(); // Get how many to spawn                  
                            while (passengersToSpawn > 0) {
                                stationList[whereToSpawn].spawnPassenger(passengerDestination); //spawn passenger
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