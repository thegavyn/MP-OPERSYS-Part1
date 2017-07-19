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



}

