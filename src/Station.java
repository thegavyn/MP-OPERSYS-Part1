import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by Mark Gavin on 7/17/2017.
 */
public class Station {
    //Conditions
    private Condition trainArrived;
    private Condition trainFull;
    private Lock lock;
    
    //Variables
    private Station nextStop;
    private int stationNo;
    private ArrayList<Passenger> waiting; // passengers who want to ride the train
    private Queue<Train> trainQueue; // queue of trains that are about to grab passengers
    private Train currentlyLoading;

    public Station(int number)
    {
        stationNo = number;
        nextStop = null;
        trainQueue = null;
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
        if(trainQueue == null || trainQueue.isEmpty()) {
            currentlyLoading = new Train(numberSeats); // direkta nalang na magload
            System.out.println("Spawned Train " + currentlyLoading.getTrainNo() + ".");
        } else { // since walang laman yung queue
            receiveTrain(new Train(numberSeats));
        }
    }

    public void receiveTrain(Train nextIn)
    {
        trainQueue.add(nextIn);
        System.out.println("Train " + nextIn.getTrainNo() +
            "enqueued in Station " + getStationNo() + ".");
    }

    public void setCurrentlyLoading() {
        if (currentlyLoading == null && !trainQueue.isEmpty()) {
            currentlyLoading = trainQueue.remove(); // tanggalin si train sa queue, magload siya
            trainArrived_signal();
            System.out.println("Train " + currentlyLoading.getTrainNo() +
                " arrived in Station " + getStationNo());
        }
    }

    public int getStationNo() {
        return stationNo;
    }

    public void loadTrain(int count)
    {
    	getLock().lock();
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
            this.trainFull_signal();
        }
        
        getLock().unlock();
    }

    public void sendTrain() {
        nextStop.receiveTrain(currentlyLoading);
        System.out.println("Train " + currentlyLoading.getTrainNo() +
                " leaving Station " + getStationNo());
        if(!trainQueue.isEmpty()) {
            // kung may laman si trainQueue
            setCurrentlyLoading();
        }
        else {
            // walang laman
            currentlyLoading = null;
            System.out.println("No train loading in Station " + getStationNo());
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
        getLock().lock();
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
        getLock().unlock();
        System.out.println("Station " + getStationNo() + " unlocked.");
    }

    // Sync stuff!!!

    public void trainArrived_wait() {
        try {
            
            trainArrived.wait();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void trainArrived_signal() {
        try {
            trainArrived.signal();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void trainFull_wait() {
        try {
            trainFull.await();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void trainFull_signal() {
        try {
            trainFull.signal();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Lock getLock() {
        return lock;
    }
}
