public class BookMyStay {

    public static void main(String[] args) {

        Room single = new SingleRoom();
        Room doub = new DoubleRoom();
        Room suite = new SuiteRoom();

        single.displayRoomDetails();
        System.out.println();

        doub.displayRoomDetails();
        System.out.println();

        suite.displayRoomDetails();
    }
}

// Abstract base class
abstract class Room {

    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: $" + pricePerNight);
    }
}

// Single Room
class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 200, 75.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("=== Single Room ===");
        super.displayRoomDetails();
    }
}

// Double Room
class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 350, 120.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("=== Double Room ===");
        super.displayRoomDetails();
    }
}

// Suite Room
class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 600, 250.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("=== Suite Room ===");
        super.displayRoomDetails();
    }
}