import java.util.ArrayList;

/**
 * Created by Mark Gavin on 7/17/2017.
 */
public class Station {
    private Station nextStop;
    private int stationNo;
    private ArrayList<Passenger> waiting;
    public Train boardingNow;

    public Station(int number)
    {
        stationNo = number;
        nextStop = null;
        boardingNow = null;
    }

    public void spawnPassenger()
    {
        Passenger person = new Passenger();
        waiting.add(person);
    }

    public void spawnTrain(int numberSeats) {
        if(boardingNow == null)
            boardingNow = new Train(numberSeats);
    }

    public void receiveTrain(Train nextIn)
    {
        boardingNow = nextIn;
    }

    public void loadTrain(int count)
    {
        int numberSeatsAvail, ctr;
        Passenger loading;
        if(boardingNow != null)
        {
            numberSeatsAvail = boardingNow.countFreeSeats();
            ctr = 0;
            while(ctr < numberSeatsAvail)
            {
                loading = waiting.remove(ctr);
                loading.onBoard(boardingNow);
                boardingNow.passengerArrayList.add(loading);
                ctr++;
            }

        }
    }

    public void sendTrain()
    {
        nextStop.receiveTrain(this.boardingNow);
        this.boardingNow = null;
    }

    public ArrayList<Passenger> getWaiting() {
        return waiting;
    }

}
