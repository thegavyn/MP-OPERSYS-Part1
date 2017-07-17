/**
 * Created by Mark Gavin on 7/17/2017.
 */
public class Station {
    public int stationNo;
    private Train stoppedTrain;
    private boolean mayTrain;

    public Station(int number)
    {
        stationNo = number;
        mayTrain = false;
    }
    public void receiveTrain(Train incomingTrain)
    {
        if(!mayTrain) {
            stoppedTrain = incomingTrain;
            mayTrain = true;
        }
    }

    public boolean meronBa()
    {
        return mayTrain;
    }
}
