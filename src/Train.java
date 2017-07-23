/**
 * Created by Mark Gavin on 7/17/2017.
 */

import java.util.ArrayList;


public class Train extends Thread{

    /* Variables */
    private ArrayList<Passenger> passengerArrayList;
    private Station currentStation;
    private final int passengerCapacity;
    public static int trainsSpawned;
    private final int trainNo;


    public Train(int cap, Station here)
    {
        trainsSpawned++;
        trainNo = trainsSpawned;
        passengerArrayList = new ArrayList<Passenger>();
        passengerCapacity = cap;
        currentStation = here;

        this.start();
    }
    public void setCurrentStation(Station here)
    {
        currentStation = here;
    }
    public int countFreeSeats() {
        return passengerCapacity - passengerArrayList.size();
    }
    
    public ArrayList<Passenger> getPassengers() {
    	return passengerArrayList;
    }
    
    public Station getCurrentStation() {
    	return currentStation;
    }

    public int getTrainNo() {
        return trainNo;
    }
    public int getNumberofPassengers(){
        return passengerArrayList.size();
    }
    public void addPassenger(Passenger x)
    {
        passengerArrayList.add(x);
    }

    public void run() {
        int ctr = 0;
        while(true) {
            this.currentStation.station_load_train(this);
            this.currentStation.getNextStop().station_load_train(this);
            ctr++;
        }
    }

}