/**
 * Created by Mark Gavin on 7/17/2017.
 */
public class Train implements Runnable{
    private int numSeats;
    private int freeSeats;
    public Station currStation;

    public Train(int ns)
    {
        numSeats = ns;
        freeSeats = ns;
    }

    public void loadTrain()
    {
        if(freeSeats > 0)
        {
            freeSeats--;
        }

    }

    public void unloadTrain()
    {
        if(freeSeats < numSeats)
        {
            freeSeats++;
        }
    }
    public void run()
    {

    }

}