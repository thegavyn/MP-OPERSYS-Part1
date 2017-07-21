/**
 * Created by Mark Gavin on 7/21/2017.
 */
public class spawnStation extends Station {

    public spawnStation(int station)
    {
        super(station);
    }

    public void spawnTrain(int numberSeats) {
        Train holder;
        holder = new Train(numberSeats);
        holder.setCurrentStation(this);
        receiveTrain(holder);
    }

    



}
