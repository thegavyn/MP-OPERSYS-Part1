/**
 * Created by Mark Gavin on 7/17/2017.
 */
public class Rail {
    public Train onMe;
    public int railNo;

    public Rail(int number){
        railNo = number;
    }

    public void spawnTrain(int numberSeats) {
        onMe = new Train(numberSeats);
    }
}
