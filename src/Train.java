/**
 * Created by Mark Gavin on 7/17/2017.
 */

import java.util.ArrayList;

public class Train extends Thread{
    public ArrayList<Passenger> passengerArrayList;
    public Station currStation;
    public static int numberPass;

    public Train(int cap)
    {
        passengerArrayList = new ArrayList<Passenger>();
        numberPass = cap;
    }


    public int countFreeSeats() {
        return numberPass - passengerArrayList.size();
    }

}