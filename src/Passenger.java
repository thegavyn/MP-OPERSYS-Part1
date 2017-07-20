/**
 * Created by Mark Gavin on 7/17/2017.
 */
public class Passenger extends Thread {

    private Station currentStation;
    private Station destinationStation;
    private int destinationStationNo;
    private Train inside;
    private final int passengerNo;
    public static int passengersSpawned;

    public Passenger(int x) {
        destinationStationNo = x;
        this.start();
        passengersSpawned++;
        passengerNo = passengersSpawned;
        }

    public void waitForTrain() {
        // Wait until for currentStation to give a signal!!!
        System.out.println("Passenger " + passengerNo + 
            " waiting for train in Station " + currentStation.getStationNo() + ".");
    	while (currentStation.getCurrentlyLoading() == null);
        	currentStation.trainArrived_wait();
        this.onBoard(currentStation.getCurrentlyLoading());
        //currentStation.trainArrived_signal();
    }

    public void departTrain(int stationNum)//resets the train
    {
            inside = null;
            System.out.println("Passenger " + passengerNo + 
                " departed from Station " + stationNum + ".");
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
        System.out.println("Passenger " + passengerNo +
            " boarded Train " + inside.getTrainNo() + ".");
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

    public int getpassengerNo() {
        return passengerNo;
    }

    public void run() {
        // insert locking stuff here? if needed
        this.waitForTrain();
    }

}

