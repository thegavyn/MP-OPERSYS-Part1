/**
 * Created by Mark Gavin on 7/17/2017.
 */
public class Lock {

    public boolean lockState;
    public int counter;
    Thread lockThread;

    public Lock()
    {
        boolean lockState = false;
        Thread lockThread = null;
        int counter =0;
    }

    public void lock(){
        while(lockState && lockThread != Thread.currentThread())
            wait();
        lockState = true;
        lockThread = Thread.currentThread();
        counter++;}

    public void unlock(){
        if(Thread.currentThread() == lockThread) {
            counter--;
            if(counter==0){
                lockState=false;
                notify();}}
    }
}
