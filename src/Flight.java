import java.util.Random;

public class Flight extends Thread {
    private int number;//Flight number
    private Airport airportDepart, airportLand;
    //Numbers for depart, landing and flying times.
    private final int MAX = 3000;
    private final int MIN = 2000;
    Random rand = new Random();

    public Flight(int num, Airport depart, Airport land){
        number = num;
        airportDepart = depart;
        airportLand = land;
    }

    public void run(){
        int runway;
        //Depart - allocate, use and free - section.
        runway = airportDepart.depart(number);
        System.out.printf("Flight %s departing in airport %s by runway number %s%n", number,airportDepart.name, runway);
        try {
            Thread.sleep(rand.nextInt(MAX)+MIN);
        }catch (InterruptedException e){
            System.out.println("interrupted");
        }
        airportDepart.freeRunway(runway, number);
        System.out.printf("Flight %s took off and free runway number in airport %s %s%n",
                number, runway, airportDepart.name);

        //The plane is flying.
        try {
            Thread.sleep(rand.nextInt(MAX)+MAX);
        }catch (InterruptedException e){
            System.out.println("interrupted");
        }

        //Land - allocate, use and free - section.
        runway = airportLand.land(number);
        System.out.printf("Flight %s landing in airport %s by runway number %s%n", number,airportLand.name, runway);
        try {
            Thread.sleep(rand.nextInt(MAX)+MIN);
        }catch (InterruptedException e){
            System.out.println("interrupted");
        }
        airportLand.freeRunway(runway, number);
        System.out.printf("Flight %s landed and free runway number %s in airport %s%n", number, runway, airportLand.name);

    }

}
