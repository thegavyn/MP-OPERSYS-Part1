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


    public Train(int cap, Station here)
    {
        trainsSpawned++;
        trainNo = trainsSpawned;
        passengerArrayList = new ArrayList<Passenger>();
        passengerCapacity = cap;
        currentStation = here;

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
        System.out.println("nakapasok");
        int ctr = 0;

        while(true) {

            if(currentStation.isSpawn == true && ctr == 0) {
                this.currentStation.station_load_train(this);
                currentStation.sendTrain();
                currentStation = currentStation.getNextStop();
                currentStation.receiveTrain(this);
            }
            else{
                this.currentStation.departPasaheros();
                this.currentStation.station_load_train(this);
                currentStation.sendTrain();
                currentStation = currentStation.getNextStop();
                currentStation.receiveTrain(this);
            }
            ctr++;
        }
    /* /*
         // park train currentStation.trainArrived_signal();//for passengers to enter
        if(currentStation.isSpawn == false)
            currentStation.receiveTrain(this);
        System.out.println(currentStation.getqSize());
        currentStation.getLock().lock();//boarding here
        currentStation.setCurrentlyLoading();
        System.out.println(currentStation.getCurrentlyLoading());
        currentStation.getLock().unlock();
        //currentStation.loadTrain(countFreeSeats()); // load train depending on free seats
            /*

        System.out.println("Eror here4");
        currentStation.trainFull_wait();

        currentStation.getLock().lock();
        currentStation.sendTrain();
        currentStation.getLock().unlock();
        */
    }

}