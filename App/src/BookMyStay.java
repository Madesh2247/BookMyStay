import java.io.*;
import java.util.*;

// RoomInventory
class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Single", 5);
        rooms.put("Double", 3);
        rooms.put("Suite", 2);
    }

    public Map<String, Integer> getRooms() {
        return rooms;
    }

    public void setRoom(String type, int count) {
        rooms.put(type, count);
    }
}

// File Persistence Service
class FilePersistenceService {

    // Save inventory to file
    public void saveInventory(RoomInventory inventory, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            for (Map.Entry<String, Integer> entry : inventory.getRooms().entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }

            System.out.println("Inventory saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving inventory.");
        }
    }

    // Load inventory from file
    public void loadInventory(RoomInventory inventory, String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("No valid inventory data found. Starting fresh.\n");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                inventory.setRoom(parts[0], Integer.parseInt(parts[1]));
            }

            System.out.println("Inventory loaded successfully.\n");

        } catch (IOException e) {
            System.out.println("Error loading inventory.");
        }
    }
}

// MAIN CLASS
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("System Recovery\n");

        String filePath = "inventory.txt";

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistence = new FilePersistenceService();

        // Load previous state
        persistence.loadInventory(inventory, filePath);

        // Display inventory
        System.out.println("Current Inventory:");
        for (String type : inventory.getRooms().keySet()) {
            System.out.println(type + ": " + inventory.getRooms().get(type));
        }

        // Save state
        persistence.saveInventory(inventory, filePath);
    }
}