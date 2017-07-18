import java.util.ArrayList;

/**
 * Created by Mark Gavin on 7/17/2017.
 */
public class Station {
    private Rail inFront;
    private ArrayList<Passenger> waiting;

    public Station(int number)
    {
        inFront = new Rail();
    }

    public void spawnTrain(int numberSeats) {
        Train bounded = new Train(numberSeats);
        inFront.onMe = bounded;
    }

    public ArrayList<Passenger> getWaiting() {
        return waiting;
    }

    public Rail getRail() {
        return inFront;
    }
}
