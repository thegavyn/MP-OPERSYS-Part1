/**
 * Created by Mark Gavin on 7/17/2017.
 */
public class Passenger extends Thread{

    public Station currentStation;
    public Station destinationStation;
    public Train inIt;

    public Passenger()
    {
        this.start();
    }

    public void waitForTrain()
    {
        // Check if currentStation has a train.
        if (currentStation.boardingNow != null) {
            inIt = currentStation.boardingNow;
            onBoard(inIt);
        }

    }

    public void onBoard(Train t)
    {
        inIt = t;
    }



}

