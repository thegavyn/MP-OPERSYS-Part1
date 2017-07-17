/**
 * Created by Mark Gavin on 7/17/2017.
 */
public class Passenger implements Runnable{

    private boolean seated;
    public Station currentStation;

    public Passenger()
    {
        seated = false;
    }

    public void waitForTrain()
    {

    }

    public void onBoard(Train t)
    {
        seated = true;
    }

    public void gettingOff()
    {
        seated = false;
    }

    public void run()
    {

    }

}

