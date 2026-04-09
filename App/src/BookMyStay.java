import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

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

// Inventory
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
}

// Queue
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }
}

// Validator
class ReservationValidator {

    public void validate(String guestName, String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (!inventory.getRoomAvailability().containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        if (inventory.getRoomAvailability().get(roomType) <= 0) {
            throw new InvalidBookingException("Selected room is not available.");
        }
    }
}

// MAIN CLASS
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Booking Validation\n");

        Scanner scanner = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue queue = new BookingRequestQueue();

        try {
            // Input
            System.out.print("Enter guest name: ");
            String name = scanner.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String type = scanner.nextLine();

            // Validation
            validator.validate(name, type, inventory);

            // If valid → add booking
            Reservation r = new Reservation(name, type);
            queue.addRequest(r);

            System.out.println("Booking request added successfully!");

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}