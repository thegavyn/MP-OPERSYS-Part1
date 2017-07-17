import java.util.ArrayList;

/**
 * Created by Mark Gavin on 7/17/2017.
 */
public class Station {
    public int stationNo;
    private Rail inFront;
    private ArrayList<Passenger> waiting;

    public Station(int number)
    {
        stationNo = number;
        inFront = new Rail((stationNo/2) + 1);

    }
}
