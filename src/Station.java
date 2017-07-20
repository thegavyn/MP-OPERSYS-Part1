import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by Mark Gavin on 7/17/2017.
 */
public class Station {
    //Conditions
    
    //Variables
    private Station nextStop;
    private int stationNo;
    private ArrayList<Passenger> waiting; // passengers who want to ride the train
    private ArrayList<Train> trainQueue; // queue of trains that are about to grab passengers
    private Train currentlyLoading;

    public Station(int number)
    {
        stationNo = number;
        nextStop = null;
        trainQueue = new ArrayList<Train>();
    }

    public void spawnPassenger(int stationDrop)
    {
        Passenger p = new Passenger(stationDrop); // GAVIN BOI PAANO INPUT NATIN NG DESTINATION HAHA
        waiting.add(p);
        System.out.println("Spawned Passenger " + p.getpassengerNo() +
            "in Station " + getStationNo() +
            ". Destination is Station " + p.getDestinationStation().getStationNo() + ".");
    }

    public void spawnTrain(int numberSeats) {
        Train holder;
        holder = new Train(numberSeats, this);
        setCurrentlyLoading();
        int ctr = 0;
        while(ctr < trainQueue.size())
        {
            System.out.println(trainQueue.get(ctr).getCurrentStation());
            ctr++;
        }
    }

    public void receiveTrain(Train nextIn)
    {
        trainQueue.add(nextIn);
        System.out.println("Train " + nextIn.getTrainNo() +
            "enqueued in Station " + getStationNo() + ".");
    }

    public void setCurrentlyLoading() {
        CalTrainII.trainLeaving_wait();
        if (currentlyLoading == null && !trainQueue.isEmpty()) {
            currentlyLoading = trainQueue.remove(0); // tanggalin si train sa queue, magload siya
            CalTrainII.trainArrived_signal();
            System.out.println("Train " + currentlyLoading.getTrainNo() +
                " arrived in Station " + getStationNo());
        }
    }

    public int getStationNo() {
        return stationNo;
    }

    public void loadTrain(int count)
    {
    	CalTrainII.getLock().lock();
        int numberSeatsAvail, ctr;
        Passenger loading;
        if(currentlyLoading != null) // kung may laman si trainQueue
        {
            numberSeatsAvail = currentlyLoading.countFreeSeats();
            ctr = 0;
            while(ctr < numberSeatsAvail)
            {
                loading = waiting.remove(ctr);
                loading.onBoard(currentlyLoading);
                currentlyLoading.getPassengers().add(loading); // how do u even loop through dis
                ctr++;
            }
            CalTrainII.trainFull_signal();
        }
        
        CalTrainII.getLock().unlock();
    }

    public void sendTrain() {
        if (currentlyLoading != null) {
            nextStop.receiveTrain(currentlyLoading);
            System.out.println("Train " + currentlyLoading.getTrainNo() +
                    " leaving Station " + getStationNo());
            CalTrainII.trainLeaving_signal();
            if(!trainQueue.isEmpty()) {
                // kung may laman si trainQueue
                setCurrentlyLoading();
            }
            else {
                // walang laman
                currentlyLoading = null;
                System.out.println("No train loading in Station " + getStationNo());
            }
        } else {
            System.out.println("No train is loading, no train leaving Station " 
                + currentlyLoading.getTrainNo() + ".");
        }
    }

    public Station getNextStop() {
        return nextStop;
    }

    public void setNextStop(Station stop) {
        nextStop = stop;
    }

    public Train getCurrentlyLoading() {
        return currentlyLoading;
    }

    public ArrayList<Passenger> getWaiting() {
        return waiting;
    }

    public void departPasaheros()//depart passengers
    {
        CalTrainII.getLock().lock();
        System.out.println("Station " + getStationNo() + " locked.");
        int ctr = 0;

        if(currentlyLoading != null)
        {
            while(ctr < currentlyLoading.getPassengers().size())
            {
                currentlyLoading.getPassengers().get(ctr).checkDepart(stationNo);
                currentlyLoading.getPassengers().get(ctr).departTrain(stationNo);
                currentlyLoading.getPassengers().remove(ctr);
                ctr++;
            }
        }
        CalTrainII.getLock().unlock();
        System.out.println("Station " + getStationNo() + " unlocked.");
    }
}
