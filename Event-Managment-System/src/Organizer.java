import java.util.Scanner;
import java.time.LocalTime;

public class Organizer extends User {
    private Wallet wallet;

    public Organizer() {}

    public Organizer(String username, String password, String dob, double balance) {
        super(username, password, dob);
        this.wallet = new Wallet(balance);
    }

    @Override
    public void Register() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        this.username = scanner.nextLine();

        System.out.print("Enter password: ");
        this.password = scanner.nextLine();

        System.out.print("Enter date of birth (e.g., YYYY-MM-DD): ");
        this.dateOfBirth = scanner.nextLine();

        System.out.print("Enter initial wallet balance: ");
        double balance;
        try {
            balance = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
        } catch (Exception e) {
            System.out.println("Invalid balance. Defaulting to 0.0.");
            scanner.nextLine();
            balance = 0.0;
        }
        this.wallet = new Wallet(balance);
    }

    @Override
    public void showDashboard() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("===== Organizer Dashboard =====");
            System.out.println("Username: " + username);
            System.out.println("1. Create New Event");
            System.out.println("2. View My Events");
            System.out.println("3. Update Events");
            System.out.println("4. View My Balance");
            System.out.println("5. Delete Events");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            if (choice == 1) {
                System.out.print("Enter event title: ");
                String title = scanner.nextLine();

                System.out.println("Available Rooms:");
                for (int i = 0; i < Database.rooms.size(); i++) {
                    System.out.println((i + 1) + ". " + Database.rooms.get(i).getName());
                }
                System.out.print("Choose a room (number): ");
                int roomChoice;
                try {
                    roomChoice = scanner.nextInt() - 1;
                    scanner.nextLine();
                } catch (Exception e) {
                    scanner.nextLine();
                    System.out.println("Invalid input. Defaulting to first room.");
                    roomChoice = 0;
                }
                Room room = (roomChoice >= 0 && roomChoice < Database.rooms.size()) ? Database.rooms.get(roomChoice) : Database.rooms.get(0);

                System.out.println("Available Categories:");
                for (int i = 0; i < Database.categories.size(); i++) {
                    System.out.println((i + 1) + ". " + Database.categories.get(i).getName());
                }
                System.out.print("Choose a category (number): ");
                int categoryChoice;
                try {
                    categoryChoice = scanner.nextInt() - 1;
                    scanner.nextLine();
                } catch (Exception e) {
                    scanner.nextLine();
                    System.out.println("Invalid input. Defaulting to first category.");
                    categoryChoice = 0;
                }
                Category category = (categoryChoice >= 0 && categoryChoice < Database.categories.size()) ? Database.categories.get(categoryChoice) : Database.categories.get(0);

                System.out.print("Enter event price: ");
                double price;
                try {
                    price = scanner.nextDouble();
                    scanner.nextLine();
                } catch (Exception e) {
                    scanner.nextLine();
                    System.out.println("Invalid price. Defaulting to 0.0.");
                    price = 0.0;
                }

                System.out.print("Enter maximum seats: ");
                int maxSeats;
                try {
                    maxSeats = scanner.nextInt();
                    scanner.nextLine();
                } catch (Exception e) {
                    scanner.nextLine();
                    System.out.println("Invalid number. Defaulting to 50.");
                    maxSeats = 50;
                }

                Event newEvent = new Event(title, this, room, category, price, maxSeats);
                Database.events.add(newEvent);
                Database.eventCount++;
                System.out.println("Event created successfully: " + title);

            }
            else if (choice == 2) {
                System.out.println("My Events:");
                boolean hasEvents = false;
                for (Event event : Database.events) {
                    if (event.getOrganizer().getUsername().equals(this.username)) {
                        System.out.println(event.toString());
                        hasEvents = true;
                    }
                }
                if (!hasEvents) {
                    System.out.println("You have not created any events.");
                }
            }
            else if (choice == 3){

            }
            else if (choice==4) {
                System.out.println("current balance: ");
                System.out.println(this.getWallet().getBalance());
                System.out.println("Want to add balance? (yes/no)");
                String balanceChoice= scanner.nextLine();
                if (balanceChoice.equals("yes")) {
                    System.out.println("enter amount:" );
                    double amount= scanner.nextDouble();
                    this.getWallet().deposit(amount);
                }
                else this.showDashboard();
            }
            else if (choice == 5) {
                System.out.println("My Events:");
                boolean hasEvents = false;
                int index = 1;
                for (Event event : Database.events) {
                    if (event.getOrganizer().getUsername().equals(this.username)) {
                        System.out.println(index + ". " + event.getTitle());
                        hasEvents = true;
                        index++;
                    }
                }
                if (!hasEvents) {
                    System.out.println("You have not created any events.");
                    continue;
                }

                System.out.print("Choose an event to delete (number): ");
                int eventChoice;
                try {
                    eventChoice = scanner.nextInt() - 1;
                    scanner.nextLine();
                } catch (Exception e) {
                    scanner.nextLine();
                    System.out.println("Invalid input. Deletion cancelled.");
                    continue;
                }

                // Find the event to delete
                index = 0;
                Event eventToDelete = null;
                int eventIndex = -1;
                for (Event event : Database.events) {
                    if (event.getOrganizer().getUsername().equals(this.username)) {
                        if (index == eventChoice) {
                            eventToDelete = event;
                            eventIndex = Database.events.indexOf(event);
                            break;
                        }
                        index++;
                    }
                }

                if (eventToDelete != null) {
                    // Refund attendees
                    for (Attendee attendee : eventToDelete.getAttendees()) {
                        double refundAmount = eventToDelete.getPrice();
                        attendee.getWallet().refund(eventToDelete, refundAmount);
                        System.out.println("Refunded " + refundAmount + " to " + attendee.getUsername());
                    }
                    // Remove event from database
                    Database.events.remove(eventIndex);
                    Database.eventCount--;
                    System.out.println("Event '" + eventToDelete.getTitle() + "' deleted successfully.");
                } else {
                    System.out.println("Invalid event selection. Deletion cancelled.");
                }
            }
            else if (choice == 6) {
                super.logout();
                return;
            }
            else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Getter for wallet
    public Wallet getWallet() {
        return wallet;
    }
}