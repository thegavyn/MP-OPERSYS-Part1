/**
 * Created by Mark Gavin on 7/17/2017.
 */

import java.util.ArrayList;

public class Train extends Thread{
    public Lock[] seats;
    public ArrayList<Passenger> passengerArrayList;
    public Station currStation;
    public static int spawned;
    public Rail rail;

    public Train(int cap)
    {
        spawned++;
        this.start();
        seats = new Lock[cap];
    }

    public void loadTrain() {
        if (currStation.getWaiting().isEmpty()) {
            rail = rail.nextRail;
        } else { // kung may tambay sa station
            int currFreeSeats = countFreeSeats(); // count free seats
            for (int x = 0; x < currStation.getWaiting().size(); x++) {
                currStation.getWaiting().get(x).start();//will start thread for passenger
                if (currFreeSeats > 0) { // if train has enough free seats
                    currStation.getWaiting().get(x).onBoard(this);         // put passenger on board
                    passengerArrayList.add(currStation.getWaiting().get(x)); // put passenger on board
                    currStation.getWaiting().remove(x); // remove passenger from station
                    // lock seat (how do i do this)
                    Lock a = new Lock();
                    a.lock();
                    seats[x] = a;

                }

            }
        }
    }

    public void unloadTrain()
    {
        for (int x = 0; x < passengerArrayList.size(); x++) {
            if (passengerArrayList.get(x).destinationStation == currStation) {
                passengerArrayList.get(x).getOff();
                passengerArrayList.remove(x);
            }
        }
    }

    public int countFreeSeats() {
        int freeSeats = 0;
        for (int x = 0; x < seats.length; x++) {
            if (!seats[x].lockState) { // check if which seats are not locked
                freeSeats++;
            }
        }
        return freeSeats;
    }

}