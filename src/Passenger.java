/**
 * Created by Mark Gavin on 7/17/2017.
 */
public class Passenger extends Thread {

    private Station currentStation;
    private Station destinationStation;
    private int destinationStationNo;
    private Train inside;

    public Passenger(int x) {
        destinationStationNo = x;
        this.start();
    }

    public void waitForTrain() {
        // Wait until for currentStation to give a signal!!!
        currentStation.trainArrived_wait();
        this.onBoard(currentStation.getCurrentlyLoading());
        //currentStation.trainArrived_signal();
    }

    public void departTrain(int stationNum)//resets the train
    {
            inside = null;
    }
    public boolean checkDepart(int stationNum)//checks if this is destination station
    {
        if(destinationStationNo == stationNum)
            return true;
        else
            return false;
    }

    public void onBoard(Train t) {
        inside = t;
    }

    public Station getCurrentStation() {
        return currentStation;
    }

    public Station getDestinationStation() {
        return destinationStation;
    }

    public Train getTrainInside() {
        return inside;
    }

    public void run() {
        // insert locking stuff here? if needed
        this.waitForTrain();
    }

}

