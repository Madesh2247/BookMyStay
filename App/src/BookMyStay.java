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

// RoomInventory class
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

    public void reduceRoom(String roomType) {
        roomAvailability.put(roomType, roomAvailability.get(roomType) - 1);
    }
}

// BookingRequestQueue (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

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

// RoomAllocationService
class RoomAllocationService {

    private Set<String> allocatedRoomIds;
    private Map<String, Integer> roomCounters;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        roomCounters = new HashMap<>();
    }

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String type = reservation.getRoomType();
        Map<String, Integer> availability = inventory.getRoomAvailability();

        if (availability.get(type) > 0) {

            String roomId = generateRoomId(type);

            allocatedRoomIds.add(roomId);
            inventory.reduceRoom(type);

            System.out.println("Booking confirmed for Guest: "
                    + reservation.getGuestName()
                    + ", Room ID: " + roomId);

        } else {
            System.out.println("No rooms available for " + type
                    + " (Guest: " + reservation.getGuestName() + ")");
        }
    }

    private String generateRoomId(String roomType) {
        int count = roomCounters.getOrDefault(roomType, 0) + 1;
        roomCounters.put(roomType, count);
        return roomType + "-" + count;
    }
}

// MAIN CLASS
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Room Allocation Processing\n");

        // Initialize components
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        RoomAllocationService allocator = new RoomAllocationService();

        // Create booking requests
        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Subha", "Single"));
        queue.addRequest(new Reservation("Vanmathi", "Suite"));

        // Process queue (FIFO)
        while (queue.hasPendingRequests()) {
            Reservation r = queue.getNextRequest();
            allocator.allocateRoom(r, inventory);
        }
    }
}