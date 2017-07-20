/**
 * Created by Mark Gavin on 7/17/2017.
 */

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;

public class Train extends Thread{

    /* Variables */
    private ArrayList<Passenger> passengerArrayList;
    private Station currentStation;
    private final int passengerCapacity;
    public static int trainsSpawned;
    private final int trainNo;

    public Train(int cap)
    {
        trainsSpawned++;
        trainNo = trainsSpawned;
        passengerArrayList = new ArrayList<Passenger>();
        passengerCapacity = cap;
        this.start(); // Start thread
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
    
    public int getCapacity() {
    	return passengerCapacity;
    }

    public int getTrainNo() {
        return trainNo;
    }

    public void run() {
        System.out.println(currentStation);
        currentStation.getLock().lock();//error here
        currentStation.receiveTrain(this); // park train
        currentStation.getLock().unlock();

        currentStation.trainArrived_wait(); // wait hangga't hindi siya yung nagloload ng pasahero
        System.out.println("Eror here4");
        currentStation.setCurrentlyLoading();
        currentStation.loadTrain(countFreeSeats()); // load train depending on free seats
        System.out.println("Eror here4");
        currentStation.trainFull_wait();

        currentStation.getLock().lock();
        currentStation.sendTrain();
        currentStation.getLock().unlock();
    }

}