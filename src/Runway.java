
public class Runway {
    private int number, flight;
    private boolean isBusy;

    public Runway(int num){
        number = num;
        isBusy = false;
    }

    public synchronized void setFlight(int flight) {
        this.flight = flight;
    }

    public synchronized int getFlight(){
        return flight;
    }

    public synchronized boolean ifBusy(){
        return isBusy;
    }
    public synchronized void setBusy(boolean busy) {
        isBusy = busy;
    }
}
