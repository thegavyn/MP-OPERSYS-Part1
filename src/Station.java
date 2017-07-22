import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Mark Gavin on 7/17/2017.
 */
public class Station {
    //Conditions

    private Lock lock;
    private Condition trainArrived;
    private Condition trainLeaving;

    //Variables
    private Station nextStop;
    private int stationNo;
    private ArrayList<Passenger> waiting; // passengers who want to ride the train
    private ArrayList<Train> trainQueue; // queue of trains that are about to grab passengers
    private Train currentlyLoading;
    private boolean isLoaded;
    public boolean isSpawn;

    public Station(int number)
    {

        trainArrived = new ReentrantLock().newCondition();
        trainLeaving = new ReentrantLock().newCondition();
        stationNo = number;
        nextStop = null;
        trainQueue = new ArrayList<Train>();
        waiting = new ArrayList<Passenger>();
        lock = new ReentrantLock();
        trainArrived = lock.newCondition();
        trainLeaving = lock.newCondition();
        isLoaded = false;
        if(number == 0)
            isSpawn = true;
        else
            isSpawn = false;
    }

    /*
        Check if may naka-assign na train sa loob ng station
     */
    public boolean checkAssignment()
    {
        return isLoaded;
    }

    /*
        Spawn passenger inside station
     */
    public void spawnPassenger(int stationDrop, Station s, Station d)
    {
        Passenger p = new Passenger(stationDrop, s, d);
        waiting.add(p);
        System.out.println(waiting.size());
        System.out.println("Spawned Passenger " + p.getpassengerNo() +
            "in Station " + getStationNo() +
            ". Destination is Station " + p.getDestinationStation().getStationNo() + ".");
    }

    /*
        Spawn train (from STATION 0 ONLY)
     */
    public void spawnTrain(int numberSeats) {
        //System.out.println("Hello1");
        Train holder;
        holder = new Train(numberSeats, this);
        //System.out.println("Hello2");
        receiveTrain(holder);
        System.out.println("Created");
        //System.out.println("Hello3");
    }
    public void onBoard(Passenger x) {
        currentlyLoading.addPassenger(x);

    }

    /*
        Enqueue train in station
     */
    public void receiveTrain(Train nextIn)
    {
        trainQueue.add(nextIn);
        /*
        System.out.println("Train " + nextIn.getTrainNo() +
            " enqueued in Station " + getStationNo() + ".");
            */
    }
    public int getqSize()
    {
        return trainQueue.size();
    }

    /*
        Load head of station queue
     */
    public void setCurrentlyLoading() {
        if (trainQueue.size() > 0)
        {
            currentlyLoading = trainQueue.get(0);
            isLoaded = true;
        }
    }
    public void station_load_train(Train tobeLoaded)
    {
        if(this.isSpawn == false)
            this.receiveTrain(tobeLoaded);
        System.out.println(this.getqSize());
        this.getLock().lock();//boarding here
        this.setCurrentlyLoading();//set currently loading train
        System.out.println(currentlyLoading);
        if(waiting.size() > 0) {
            try {
                trainArrived.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } //signals the others train has arrived
            try {
                trainLeaving.await();
            } catch (Exception e) {
                e.printStackTrace();
            } //waits for train to be full
            System.out.println("Done Boarding Passengers");
        }
        this.getLock().unlock();
    }

    /*
        Load passengers inside train
     */
    public void station_wait_for_train(Passenger x) {
        /*
synchronized (x) {
    x.wait();
}
*/
        // Wait until for currentStation to give a signal!!!
        this.getLock().lock();
        trainArrived_wait();
        //System.out.println("Kow Kow Kow");
        this.onBoard(x);
        System.out.println("Boarded Train" + currentlyLoading.getName() + x.getName());
        if(currentlyLoading.countFreeSeats() == 0 || waiting.size() == 0) {
            trainFull_signal();
            System.out.println("Leaving the Station");
        }
        System.out.println("Current Number of Free Seats on currentlyBoarding " + currentlyLoading.countFreeSeats());
        this.getLock().unlock();

        //currentStation.trainArrived_signal();
    }
    /*
    public void loadTrain(Passenger tobeSeated)
    {
        try {
            int numberSeatsAvail, ctr;
            Passenger loading;
            if (currentlyLoading != null) // kung may laman si trainQueue
            {
                numberSeatsAvail = currentlyLoading.countFreeSeats();
                System.out.println("Hello1");
                ctr = 0;
                while (ctr < numberSeatsAvail) {
                    System.out.println(waiting.size());
                    loading = waiting.remove(ctr);
                    System.out.println("Hello2");
                    loading.onBoard(currentlyLoading);
                    System.out.println("Hello3");
                    currentlyLoading.getPassengers().add(loading); // how do u even loop through dis
                    System.out.println("Hello4");
                    ctr++;
                }
                this.trainFull_signal();
            }
        }catch(Exception e){
            System.out.println(e);
        }

    }
    */

    /*
        Send off train to next station
     */
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


    /*
        Depart passengers
     */
    public void departPasaheros()
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

    /*------------------------------------------
     *
     *          SETTERS AND GETTERS
     *
     *-----------------------------------------*/
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

    public int getStationNo() {
        return stationNo;
    }

    /*------------------------------------------
     *
     *          SYNCHRONIZATION STUFF
     *
     *-----------------------------------------*/

    public void trainArrived_wait() {
        try {

            trainArrived.await();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void trainArrived_signal() {
        try {
            trainArrived.signalAll();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void trainFull_wait() {
        try {
            trainLeaving.await();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void trainFull_signal() {
        try {
            trainLeaving.signal();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Lock getLock() {
        return lock;
    }
}
