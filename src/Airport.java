import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Airport {
    String name;
    private final int numRW;//Number of runways in airport
    Runway[] runways;//Array of runway class
    int counter = 0;
    private Lock allock = new ReentrantLock(true);//Lock for allocate runway
    private Lock countLock = new ReentrantLock(true);//lock for counter of used runway
    private final Condition free = allock.newCondition();

    public Airport(String name, int num) {
        this.name = name;
        numRW = num;
        runways = new Runway[num];
        //Initialize all runways
        for (int i = 0; i < num; i++){
            runways[i] = new Runway(i);
        }
    }

    public int depart(int flight){
        int runway = -1;
        System.out.printf("Flight %s wait in airport %s to runway to depart%n", flight, name);
        try {
            //Calling for allocate a runway and receive the number of the allotted runway.
            runway = locked(flight);
        }
        catch (InterruptedException e){
            System.out.println("There is a problem with the runway allocate.");
        }
        return runway;
    }

    public int land(int flight){
        System.out.printf("Flight %s wait in airport %s to runway to land%n", flight, name);
        int runway = -1;
        try {
            runway = locked(flight);
        }
        catch (InterruptedException e){
            System.out.println("There is a problem with the runway allocate.");
        }
        return runway;
    }

    private int locked(int flight) throws InterruptedException {
        int i;
        //Allocate runway with fair turn and condition (there is one runway free).
        allock.lock();
        try {
            while (counter == numRW)
                free.await();

            for (i = 0; i < numRW; i++){
                if (!runways[i].ifBusy()){
                    //Initialize details of first free runway.
                    runways[i].setBusy(true);
                    runways[i].setFlight(flight);
                    count();
                    break;
                }
            }
            //there is a free runway
            if (counter < numRW) free.signal();
        }
        finally {
            allock.unlock();
        }
        return i;
    }
    //Counter of used runways.
    private synchronized void count(){
        countLock.lock();
        try {
            counter++;
        }
        finally {
            countLock.unlock();
        }
    }

    //Free runway and update the counter.
    public synchronized void freeRunway(int runway, int flight){
        if (runways[runway].getFlight() == flight){
            runways[runway].setBusy(false);
            runways[runway].setFlight(0);
            counter --;
        }
    }
}
