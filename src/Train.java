/**
 * Created by Mark Gavin on 7/17/2017.
 */

import java.util.ArrayList;

public class Train extends Thread{
    public ArrayList<Passenger> passengerArrayList;
    public Station currStation;
    public final int passengerCapacity;

    public Train(int cap)
    {
        passengerArrayList = new ArrayList<Passenger>();
        passengerCapacity = cap;
        this.start(); // Start thread
    }

    public int countFreeSeats() {
        return passengerCapacity - passengerArrayList.size();
    }

}