import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark Gavin on 7/17/2017.
 */
public class Base {
    private ArrayList<Train> listIn;
    public Station launchTo;
    public Station recieveFrom;

    public Base(Station x, Station y)
    {
        List<Train> listIn = new ArrayList<Train>();
        //put train initializations here
        launchTo = new Station(x.stationNo);
        recieveFrom = new Station(y.stationNo);
    }

    public void addTrain(Train incoming)
    {
        if(listIn.size() < 8)
        {
            listIn.add(incoming);
        }
    }

    public void launchTrain()
    {
        if(launchTo.meronBa()) {
            launchTo.receiveTrain(listIn.get(1));
            listIn.remove(1);
        }

    }




}
