import java.util.*;

// Reservation class
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// RoomInventory
class RoomInventory {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 2);
        roomAvailability.put("Double", 2);
        roomAvailability.put("Suite", 1);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void reduceRoom(String type) {
        roomAvailability.put(type, roomAvailability.get(type) - 1);
    }
}

// Queue (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean hasPendingRequests() {
        return !queue.isEmpty();
    }
}

// Allocation Service
class RoomAllocationService {
    private Map<String, Integer> counter = new HashMap<>();

    public String allocateRoom(Reservation r, RoomInventory inventory) {
        String type = r.getRoomType();

        if (inventory.getRoomAvailability().get(type) > 0) {
            int count = counter.getOrDefault(type, 0) + 1;
            counter.put(type, count);

            String roomId = type + "-" + count;
            inventory.reduceRoom(type);

            System.out.println("Booking confirmed for " + r.getGuestName()
                    + ", Room ID: " + roomId);

            return roomId;
        } else {
            System.out.println("No rooms available for " + r.getGuestName());
            return null;
        }
    }
}

// Add-On Service class
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

// AddOnServiceManager
class AddOnServiceManager {
    private Map<String, List<AddOnService>> servicesMap;

    public AddOnServiceManager() {
        servicesMap = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {
        servicesMap.putIfAbsent(reservationId, new ArrayList<>());
        servicesMap.get(reservationId).add(service);
    }

    public double calculateTotalServiceCost(String reservationId) {
        double total = 0;
        List<AddOnService> list = servicesMap.get(reservationId);

        if (list != null) {
            for (AddOnService s : list) {
                total += s.getCost();
            }
        }
        return total;
    }
}

// MAIN CLASS
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Add-On Service Selection\n");

        // Setup
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        RoomAllocationService allocator = new RoomAllocationService();
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Add booking
        queue.addRequest(new Reservation("Abhi", "Single"));

        // Process booking
        Reservation r = queue.getNextRequest();
        String reservationId = allocator.allocateRoom(r, inventory);

        // Add services
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService spa = new AddOnService("Spa", 1000);

        serviceManager.addService(reservationId, breakfast);
        serviceManager.addService(reservationId, spa);

        // Display total cost
        double total = serviceManager.calculateTotalServiceCost(reservationId);

        System.out.println("\nReservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + total);
    }
}