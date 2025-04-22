import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Event {
    private String title;
    private Organizer organizer;
    private Room room;
    private Category category;
    private double price;
    private ArrayList<Attendee> attendees;
    private int maxSeats;
    private int attendeeCount;


    public Event(String title, Organizer organizer, Room room, Category category, double price, int maxSeats) {
        this.title = title;
        this.organizer = organizer;
        this.room = room;
        this.category = category;
        this.price = price;
        this.maxSeats = maxSeats;
        this.attendees = new ArrayList<>();
        this.attendeeCount = 0;
        room.reserve(); // Reserve room when event is created
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public Room getRoom() {
        return room;
    }

    public Category getCategory() {
        return category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public double getPrice() {
        return price;
    }

    public void addAttendee(Attendee a) {
        if (attendeeCount < maxSeats) {
            attendees.add(a);
            attendeeCount++;
        } else {
            System.out.println("No available seats for this event.");
        }
    }

    public ArrayList<Attendee> getAttendees() {
        return new ArrayList<>(attendees); // Return a copy to prevent external modification
    }

    // Method to get the available seats
    public int getAvailableSeats() {
        return maxSeats - attendeeCount;
    }

    // Method to print the list of attendees for this event
    public void printAttendees() {
        System.out.println("Attendees for event: " + title);
        for (int i = 0; i < attendeeCount; i++) {
            if (attendees.get(i) != null) {
                System.out.println("- " + attendees.get(i).getUsername());
            }
        }
    }


    @Override
    public String toString() {
        return title + " | Category: " + category.getName() + " | Available seats: " + getAvailableSeats() + " | Event Price: " + getPrice();
    }


}