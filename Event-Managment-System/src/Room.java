import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Room {

    private String name;
    private boolean reserved;
    private List<TimeSlot> timeSlots;

    // Inner class to represent a time slot
    private static class TimeSlot {
        private LocalTime startTime;
        private LocalTime endTime;

        public TimeSlot(LocalTime startTime, LocalTime endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public LocalTime getStartTime() {
            return startTime;
        }

        public LocalTime getEndTime() {
            return endTime;
        }
    }

    public Room(String name) {
        this.name = name;
        this.reserved = false;
        this.timeSlots = new ArrayList<>();
    }

    public Room(String name, LocalTime startTime, LocalTime endTime) {
        this.name = name;
        this.reserved = false;
        this.timeSlots = new ArrayList<>();
        this.timeSlots.add(new TimeSlot(startTime, endTime));
    }

    public boolean isAvailable() {
        LocalTime now = LocalTime.now();
        // Room is available if it's not reserved and current time is within any time slot
        for (TimeSlot slot : timeSlots) {
            if (!reserved && !now.isBefore(slot.startTime) && !now.isAfter(slot.endTime)) {
                return true;
            }
        }
        return false;
    }

    public void reserve() {
        this.reserved = true;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void addTimeSlot(LocalTime startTime, LocalTime endTime) {
        timeSlots.add(new TimeSlot(startTime, endTime));
    }

    public void clearTimeSlots() {
        timeSlots.clear();
    }

    public static void viewAvailableRooms(List<Room> rooms) {
        System.out.println("Available Rooms:");
        boolean hasAvailable = false;
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.print(room.getName() + " - Available at: ");
                for (TimeSlot slot : room.timeSlots) {
                    System.out.print(slot.startTime + " to " + slot.endTime + "; ");
                }
                System.out.println();
                hasAvailable = true;
            }
        }
        if (!hasAvailable) {
            System.out.println("No rooms are currently available.");
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name + " (");
        for (int i = 0; i < timeSlots.size(); i++) {
            TimeSlot slot = timeSlots.get(i);
            sb.append(slot.startTime).append(" - ").append(slot.endTime);
            if (i < timeSlots.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        sb.append(isAvailable() ? " (Available)" : " (Reserved or Outside Hours)");
        return sb.toString();
    }
}