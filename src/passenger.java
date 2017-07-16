/**
 * Created by Mark Gavin on 7/16/2017.
 */
public class passenger {

    public boolean seated;
    public train seatedIn;

    public passenger()
    {
        seated = false;
    }

    public void waitForTrain()
    {

    }

    public void onBoard(train T)
    {
        seated = true;
    }

    public void gettingOff()
    {
        seated = false;
    }
}
