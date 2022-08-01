import java.util.Random;

public class Main {

    public static void main(String[] args){
        final int NUMRUNWAY = 3;
        final int NUMFLIGHTS = 10;
        final int BASENUMFLIGHT = 221;//Number for flight number
        Random rand = new Random();

        Airport[] airports = {new Airport("Ben Gurion", NUMRUNWAY),
                new Airport("Ramon", NUMRUNWAY)};

        //Initialize all flights with number and departure airport
        Flight[] flights = new Flight[NUMFLIGHTS];
        for (int i = 0; i< NUMFLIGHTS; i++){
            int flightRandom = i;
            int airport1 = rand.nextInt(2);
            int airport2 = (airport1-1)*-1;
            flights[i] = new Flight(BASENUMFLIGHT+flightRandom,
                    airports[airport1], airports[airport2]);
        }

        //Run all the flights/threads.
        for (int i = 0; i< NUMFLIGHTS; i++){
            flights[i].start();
        }

        //Wait to end them.
        for (int i = 0; i< NUMFLIGHTS; i++){
            try {
                flights[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("FINAL");
    }
}
