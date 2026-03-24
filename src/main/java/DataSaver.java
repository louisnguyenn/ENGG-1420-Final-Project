import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

// Saves the current system state to CSV files so it can be restored next run
public class DataSaver {

    // Saves all three data sets — call this when the program closes
    public static void saveAll(UserRegistry userRegistry,
                                EventController eventController,
                                ManageBooking manageBooking) {

        saveUsers("users.csv", userRegistry);
        saveEvents("events.csv", eventController);
        saveBookings("bookings.csv", manageBooking);
    }

    // Writes all users to users.csv
    private static void saveUsers(String filename, UserRegistry userRegistry) {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

            // Write the header line
            writer.write("userId,name,email,userType");
            writer.newLine();

            // Write one row per user
            ArrayList<User> users = userRegistry.getAllUsers();
            for (User u : users) {
                String row = u.getUserId() + ","
                        + u.getName() + ","
                        + u.getEmail() + ","
                        + u.getUserType();
                writer.write(row);
                writer.newLine();
            }

            writer.close();

        } catch (Exception e) {
            System.out.println("Could not save users.csv: " + e.getMessage());
        }
    }

    // Writes all events to events.csv
    private static void saveEvents(String filename, EventController eventController) {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

            // Write the header line
            writer.write("eventId,title,dateTime,location,capacity,status,eventType,topic,speakerName,ageRestriction");
            writer.newLine();

            // Write one row per event
            ArrayList<Event> events = eventController.getEventList();
            for (Event ev : events) {

                // Figure out event type and type-specific field
                String eventType      = "";
                String topic          = "";
                String speakerName    = "";
                String ageRestriction = "";

                if (ev instanceof Workshop) {
                    eventType = "Workshop";
                    topic = ((Workshop) ev).getTopic();
                } else if (ev instanceof Seminar) {
                    eventType = "Seminar";
                    speakerName = ((Seminar) ev).getSpeakerName();
                } else if (ev instanceof Concert) {
                    eventType = "Concert";
                    ageRestriction = ((Concert) ev).getAgeRestriction();
                }

                String row = ev.getEventId() + ","
                        + ev.getTitle() + ","
                        + ev.getDateTime() + ","
                        + ev.getLocation() + ","
                        + ev.getCapacity() + ","
                        + ev.getStatus() + ","
                        + eventType + ","
                        + topic + ","
                        + speakerName + ","
                        + ageRestriction;

                writer.write(row);
                writer.newLine();
            }

            writer.close();

        } catch (Exception e) {
            System.out.println("Could not save events.csv: " + e.getMessage());
        }
    }

    // Writes all bookings to bookings.csv
    private static void saveBookings(String filename, ManageBooking manageBooking) {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

            // Write the header line
            writer.write("bookingId,userId,eventId,createdAt,bookingStatus");
            writer.newLine();

            // Write one row per booking
            ArrayList<Booking> bookings = manageBooking.getAllBookings();
            for (Booking b : bookings) {
                String row = b.getBookingID() + ","
                        + b.getUserID() + ","
                        + b.getEventID() + ","
                        + b.getCreatedAt() + ","
                        + b.getBookingStatus();
                writer.write(row);
                writer.newLine();
            }

            writer.close();

        } catch (Exception e) {
            System.out.println("Could not save bookings.csv: " + e.getMessage());
        }
    }
}
