/**
 * Created by Mark Gavin on 7/17/2017.
 */
public class Rail {
    public Train onMe;
    public boolean thereIs;
    public Rail nextRail;
    public Rail prevRail;

    public Rail(){
         thereIs = false;
         onMe = null;
    }

    public void lagayTrain(Train incoming)
    {
        thereIs = true;
        onMe = incoming;
    }
}
