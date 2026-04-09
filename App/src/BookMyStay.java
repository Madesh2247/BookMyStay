import java.util.*;

// Reservation
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

// Inventory
class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Single", 3);
        rooms.put("Double", 2);
        rooms.put("Suite", 1);
    }

    public Map<String, Integer> getRooms() { return rooms; }

    public void reduce(String type) {
        rooms.put(type, rooms.get(type) - 1);
    }
}

// Queue
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNext() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Allocation
class RoomAllocationService {
    private Map<String, Integer> counter = new HashMap<>();

    public void allocateRoom(Reservation r, RoomInventory inv) {
        String type = r.getRoomType();

        if (inv.getRooms().get(type) > 0) {
            int count = counter.getOrDefault(type, 0) + 1;
            counter.put(type, count);

            inv.reduce(type);

            System.out.println("Booking confirmed for Guest: "
                    + r.getGuestName() + ", Room ID: " + type + "-" + count);
        } else {
            System.out.println("No rooms available for " + r.getGuestName());
        }
    }
}

// Concurrent Processor
class ConcurrentBookingProcessor implements Runnable {

    private BookingRequestQueue queue;
    private RoomInventory inventory;
    private RoomAllocationService service;

    public ConcurrentBookingProcessor(
            BookingRequestQueue queue,
            RoomInventory inventory,
            RoomAllocationService service) {
        this.queue = queue;
        this.inventory = inventory;
        this.service = service;
    }

    @Override
    public void run() {
        while (true) {
            Reservation r;

            // Synchronize queue access
            synchronized (queue) {
                if (queue.isEmpty()) break;
                r = queue.getNext();
            }

            // Synchronize inventory update
            synchronized (inventory) {
                service.allocateRoom(r, inventory);
            }
        }
    }
}

// MAIN CLASS
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation\n");

        // Setup
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        RoomAllocationService service = new RoomAllocationService();

        // Add requests
        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Vanmathi", "Double"));
        queue.addRequest(new Reservation("Kural", "Suite"));
        queue.addRequest(new Reservation("Subha", "Single"));

        // Threads
        Thread t1 = new Thread(new ConcurrentBookingProcessor(queue, inventory, service));
        Thread t2 = new Thread(new ConcurrentBookingProcessor(queue, inventory, service));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }

        // Final inventory
        System.out.println("\nRemaining Inventory:");
        for (String type : inventory.getRooms().keySet()) {
            System.out.println(type + ": " + inventory.getRooms().get(type));
        }
    }
}