/**
 * Created by Mark Gavin on 7/17/2017.
 */

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Initialize stations
        Station[] stationList = new Station[8];
        for (int x = 0; x < 8; x++) {
            stationList[x] = new Station(x);
            if (x == 7)
                stationList[x].setNextStop(stationList[0]);
        }

        // Create trains
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                int choice, trainsToSpawn, passengersToSpawn;
                System.out.println("1 : Spawn a train");
                System.out.println("2 : Spawn passengers");
                System.out.println("Enter choice: ");
                choice = sc.nextInt();
                
                switch(choice) {
                    case 1: // Train
                        System.out.println("Enter capacity: ");
                        trainsToSpawn = sc.nextInt(); // Get how many to spawn
                        if (trainsToSpawn > 0) {  // Pag may isospawn
                            while (trainsToSpawn > 0) {
                                stationList[0].spawnTrain
                            }
                }
                    while trains
                    stationList[0].spawnTrain();
                } else {
                    System.out.println("Did not spawn passengers.");
                }
            }
        }, 0, 5000);
        // Create passenger
    }
}