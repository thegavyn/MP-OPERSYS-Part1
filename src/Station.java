import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Mark Gavin on 7/17/2017.
 */
public class Station {
    //Conditions

    private Lock lock;
    private Lock lockF;
    private Condition trainArrived;
    private Condition trainLeaving;

    //Variables
    private Station nextStop;
    private int stationNo;
    public ArrayList<Passenger> waiting; // passengers who want to ride the train
    private Train currentlyLoading;
    private boolean isLoaded;
    public boolean isSpawn;

    public Station(int number)
    {

        trainArrived = new ReentrantLock().newCondition();
        trainLeaving = new ReentrantLock().newCondition();
        stationNo = number;
        nextStop = null;
        waiting = new ArrayList<Passenger>();
        lock = new ReentrantLock();
        lockF = new ReentrantLock();
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
        System.out.println("Spawned Train");
        station_load_train(new Train(numberSeats, this));
        System.out.println("Spawned Train");
    }

    public void onBoard(Passenger x) {
        currentlyLoading.addPassenger(x);
        x.setTrain(currentlyLoading);
    }


    /*
        Load head of station queue
     */
    public void setCurrentlyLoading(Train t) {
            currentlyLoading = t;
            isLoaded = true;
    }
    public void station_load_train(Train tobeLoaded)
    {
        long time1 = System.currentTimeMillis();
        System.out.println("In Station Number " + (getStationNo()+1));
        this.getLockF().lock();
        this.getLock().lock();
        this.setCurrentlyLoading(tobeLoaded);
        this.departPasaheros();
        if(currentlyLoading.getCurrentStation().waiting.size() > 0) {
            try {
                trainArrived.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                trainLeaving.await();
                System.out.println("Done Boarding Passengers");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
            System.out.println("No Passengers Moving Stations now");
        this.getLock().unlock();
        this.getLockF().unlock();
        long time2 = System.currentTimeMillis();
        System.out.println ("Passenger "
                + "onBoard: " + (time2 - time1));
    }

    /*
        Load passengers inside train
     */
    public void station_wait_for_train(Passenger x) {
        this.getLock().lock();
        try {
            trainArrived_wait();
            if (currentlyLoading.countFreeSeats() > 0 && waiting.size() > 0) {
                this.onBoard(x);
                System.out.println("Boarded Train " + currentlyLoading.getName() + " " +  x.getName());
                if(currentlyLoading.countFreeSeats() == 0) {
                    try {
                        trainLeaving.signal();
                        System.out.println("Leaving the Station");
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("Leaving the Station");
                }
                else if(waiting.size() == 0) {
                    try {
                        trainLeaving.signal();
                        System.out.println("Leaving the Station");
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("Leaving the Station");
                }
            }
            System.out.println("Current Number of Free Seats on currentlyBoarding " + currentlyLoading.countFreeSeats());
        }
        finally {
            this.getLock().unlock();
        }
    }

    /*
        Depart passengers
     */
    public void departPasaheros()
    {
        int ctr = 0;

        if(currentlyLoading != null)
        {
            while(ctr < currentlyLoading.getPassengers().size())
            {
                if(currentlyLoading.getPassengers().get(ctr).checkDepart(stationNo)) {
                    currentlyLoading.getPassengers().get(ctr).departTrain(stationNo);
                    currentlyLoading.getPassengers().remove(ctr);//set it back?
                    System.out.println(currentlyLoading.getNumberofPassengers() + "Still let's see");
                }
                ctr++;
            }
        }
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
            if(waiting.size() == 0 || currentlyLoading.countFreeSeats() == 0 )
                trainLeaving.signal();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Lock getLock() {
        return lock;
    }
    public Lock getLockF() {
        return lockF;
    }
}
