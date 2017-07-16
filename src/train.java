/**
 * Created by Mark Gavin on 7/16/2017.
 */
public class train {
    private int numSeats;
    private int freeSeats;

    public train(int ns)
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



}
