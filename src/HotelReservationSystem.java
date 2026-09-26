import java.util.ArrayList;
import java.util.Scanner;

public class HotelReservationSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<Room> rooms = new ArrayList<>();
    private static final ArrayList<Reservation> reservations = new ArrayList<>();

    public static void main(String[] args) {
        initializeRooms();
        boolean running = true;

        System.out.println("========================================");
        System.out.println("        HOTEL RESERVATION SYSTEM");
        System.out.println("========================================");

        while (running) {
            displayMenu();
            int choice = readInt("Enter your choice: ", 1, 7);

            switch (choice) {
                case 1: viewAvailableRooms(); break;
                case 2: makeReservation(); break;
                case 3: viewReservations(); break;
                case 4: cancelReservation(); break;
                case 5: searchReservation(); break;
                case 6: hotelStatistics(); break;
                case 7:
                    running = false;
                    System.out.println("\nThank you for using the Hotel Reservation System!");
                    break;
            }
        }
        scanner.close();
    }

    private static void initializeRooms() {
        rooms.add(new Room(101, "Single", 1500.00));
        rooms.add(new Room(102, "Single", 1500.00));
        rooms.add(new Room(201, "Double", 2500.00));
        rooms.add(new Room(202, "Double", 2500.00));
        rooms.add(new Room(301, "Deluxe", 4000.00));
        rooms.add(new Room(302, "Deluxe", 4000.00));
        rooms.add(new Room(401, "Suite", 6000.00));
        rooms.add(new Room(402, "Suite", 6000.00));
    }

    private static void displayMenu() {
        System.out.println("\n----------------------------------------");
        System.out.println("1. View Available Rooms");
        System.out.println("2. Make Reservation");
        System.out.println("3. View All Reservations");
        System.out.println("4. Cancel Reservation");
        System.out.println("5. Search Reservation");
        System.out.println("6. Hotel Statistics");
        System.out.println("7. Exit");
        System.out.println("----------------------------------------");
    }

    private static void viewAvailableRooms() {
        System.out.println("\n========== AVAILABLE ROOMS ==========");
        boolean found = false;
        for (Room room : rooms) {
            if (!room.isBooked()) {
                System.out.printf("Room %d | %-7s | Rs. %.2f/night%n",
                        room.getRoomNumber(), room.getType(), room.getPricePerNight());
                found = true;
            }
        }
        if (!found) System.out.println("No rooms are currently available.");
    }

    private static void makeReservation() {
        System.out.println("\n========== MAKE RESERVATION ==========");
        viewAvailableRooms();

        int roomNumber = readInt("Enter room number: ", 1, 9999);
        Room selectedRoom = findRoom(roomNumber);

        if (selectedRoom == null) {
            System.out.println("Invalid room number.");
            return;
        }
        if (selectedRoom.isBooked()) {
            System.out.println("That room is already booked.");
            return;
        }

        String customerName = readNonEmpty("Enter customer name: ");
        String phone = readPhone("Enter phone number: ");
        int nights = readInt("Enter number of nights: ", 1, 365);

        double totalAmount = selectedRoom.getPricePerNight() * nights;
        String reservationId = "RES" + (1001 + reservations.size());

        Reservation reservation = new Reservation(
                reservationId, customerName, phone, selectedRoom.getRoomNumber(),
                selectedRoom.getType(), nights, totalAmount
        );

        reservations.add(reservation);
        selectedRoom.setBooked(true);

        System.out.println("\nReservation confirmed!");
        reservation.display();
    }

    private static void viewReservations() {
        System.out.println("\n========== ALL RESERVATIONS ==========");
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }
        for (Reservation reservation : reservations) reservation.display();
    }

    private static void cancelReservation() {
        System.out.println("\n========== CANCEL RESERVATION ==========");
        if (reservations.isEmpty()) {
            System.out.println("No reservations available to cancel.");
            return;
        }

        String id = readNonEmpty("Enter reservation ID: ");
        Reservation reservation = findReservation(id);

        if (reservation == null) {
            System.out.println("Reservation not found.");
            return;
        }

        Room room = findRoom(reservation.getRoomNumber());
        if (room != null) room.setBooked(false);

        reservations.remove(reservation);
        System.out.println("Reservation " + id.toUpperCase() + " cancelled successfully.");
    }

    private static void searchReservation() {
        System.out.println("\n========== SEARCH RESERVATION ==========");
        String id = readNonEmpty("Enter reservation ID: ");
        Reservation reservation = findReservation(id);

        if (reservation == null) System.out.println("Reservation not found.");
        else reservation.display();
    }

    private static void hotelStatistics() {
        int bookedRooms = 0;
        double revenue = 0.0;

        for (Room room : rooms) if (room.isBooked()) bookedRooms++;
        for (Reservation reservation : reservations) revenue += reservation.getTotalAmount();

        System.out.println("\n========== HOTEL STATISTICS ==========");
        System.out.println("Total Rooms       : " + rooms.size());
        System.out.println("Booked Rooms      : " + bookedRooms);
        System.out.println("Available Rooms   : " + (rooms.size() - bookedRooms));
        System.out.printf("Current Revenue    : Rs. %.2f%n", revenue);
    }

    private static Room findRoom(int roomNumber) {
        for (Room room : rooms) if (room.getRoomNumber() == roomNumber) return room;
        return null;
    }

    private static Reservation findReservation(String id) {
        for (Reservation reservation : reservations)
            if (reservation.getReservationId().equalsIgnoreCase(id)) return reservation;
        return null;
    }

    private static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    private static String readPhone(String prompt) {
        while (true) {
            String phone = readNonEmpty(prompt);
            if (phone.matches("\\d{10}")) return phone;
            System.out.println("Please enter a valid 10-digit phone number.");
        }
    }

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) return value;
            } catch (NumberFormatException ignored) {}
            System.out.println("Please enter a number between " + min + " and " + max + ".");
        }
    }

    static class Room {
        private final int roomNumber;
        private final String type;
        private final double pricePerNight;
        private boolean booked;

        Room(int roomNumber, String type, double pricePerNight) {
            this.roomNumber = roomNumber;
            this.type = type;
            this.pricePerNight = pricePerNight;
        }
        int getRoomNumber() { return roomNumber; }
        String getType() { return type; }
        double getPricePerNight() { return pricePerNight; }
        boolean isBooked() { return booked; }
        void setBooked(boolean booked) { this.booked = booked; }
    }

    static class Reservation {
        private final String reservationId;
        private final String customerName;
        private final String phone;
        private final int roomNumber;
        private final String roomType;
        private final int nights;
        private final double totalAmount;

        Reservation(String reservationId, String customerName, String phone,
                    int roomNumber, String roomType, int nights, double totalAmount) {
            this.reservationId = reservationId;
            this.customerName = customerName;
            this.phone = phone;
            this.roomNumber = roomNumber;
            this.roomType = roomType;
            this.nights = nights;
            this.totalAmount = totalAmount;
        }

        String getReservationId() { return reservationId; }
        int getRoomNumber() { return roomNumber; }
        double getTotalAmount() { return totalAmount; }

        void display() {
            System.out.println("----------------------------------------");
            System.out.println("Reservation ID : " + reservationId);
            System.out.println("Customer       : " + customerName);
            System.out.println("Phone          : " + phone);
            System.out.println("Room           : " + roomNumber);
            System.out.println("Room Type      : " + roomType);
            System.out.println("Nights         : " + nights);
            System.out.printf("Total Amount   : Rs. %.2f%n", totalAmount);
            System.out.println("----------------------------------------");
        }
    }
}
