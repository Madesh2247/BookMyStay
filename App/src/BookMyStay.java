import java.util.HashMap;
import java.util.Map;

// Room class
class Room {
    String type;
    int beds;
    int size;
    double price;

    public Room(String type, int beds, int size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void display() {
        System.out.println(type + " Room:");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + price);
    }
}

// RoomInventory class
class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

// Main class
public class Main {
    public static void main(String[] args) {

        // Create room objects
        Room single = new Room("Single", 1, 250, 1500);
        Room doubleRoom = new Room("Double", 2, 400, 2500);
        Room suite = new Room("Suite", 3, 750, 5000);

        // Create inventory
        RoomInventory inventory = new RoomInventory();

        System.out.println("Hotel Room Inventory Status\n");

        // Single Room
        single.display();
        System.out.println("Available Rooms: " +
                inventory.getRoomAvailability().get("Single"));
        System.out.println();

        // Double Room
        doubleRoom.display();
        System.out.println("Available Rooms: " +
                inventory.getRoomAvailability().get("Double"));
        System.out.println();

        // Suite Room
        suite.display();
        System.out.println("Available Rooms: " +
                inventory.getRoomAvailability().get("Suite"));
    }
}