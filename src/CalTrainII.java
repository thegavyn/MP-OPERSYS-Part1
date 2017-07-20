import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CalTrainII {
    private static CalTrainII caltrainii = new CalTrainII();
    private Condition trainArrived;
    private Condition trainFull;
    private Condition trainLeaving;
    private Lock lock;

    private CalTrainII() {
        lock = new ReentrantLock();
        trainArrived = new ReentrantLock().newCondition();
        trainFull = new ReentrantLock().newCondition();
        trainLeaving = new ReentrantLock().newCondition();
    }

    public static CalTrainII getInstance() {
        return caltrainii;
    }

    public static synchronized void trainArrived_wait() {
    try {
        caltrainii.trainArrived.await();
    } catch(Exception e) {
        e.printStackTrace();
    }
    }

    public static synchronized void trainArrived_signal() {
        try {
            caltrainii.trainArrived.signal();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void trainFull_wait() {
        try {
            caltrainii.trainFull.await();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void trainFull_signal() {
        try {
            caltrainii.trainFull.signal();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void trainLeaving_wait() {
        try {
            caltrainii.trainLeaving.await();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void trainLeaving_signal() {
        try {
            caltrainii.trainLeaving.signal();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static Lock getLock() {
        return caltrainii.lock;
    }
}