/**
 * Created by Mark Gavin on 7/17/2017.
 */
public class Passenger extends Thread{

    private Lock seated;
    public Station currentStation;
    public Station destinationStation;

    public Passenger()
    {
        this.start();
    }

    public void waitForTrain()
    {
        // Check if currentStation has a train.
        if (currentStation.getRail().onMe != null) {
            seated = new Lock();
        }
    }

    public void onBoard(Train t)
    {
        seated.lock();
    }

    public void getOff()
    {
        seated.unlock();
    }

    public void run()
    {
        this.waitForTrain();
    }

}

