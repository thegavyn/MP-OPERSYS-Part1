/**
 * Created by Mark Gavin on 7/17/2017.
 */
public class Passenger {

    public boolean seated;
    public Train seatedIn;
    public Station currentStation;

    public Passenger()
    {
        seated = false;
    }

    public void waitForTrain()
    {
        if(currentStation.meronBa())
        {
            ;
        }
    }

    public void onBoard(Train T)
    {
        seated = true;
    }

    public void gettingOff()
    {
        seated = false;
    }
}

