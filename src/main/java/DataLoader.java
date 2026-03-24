import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;

// Loads the starter CSV files into the system at startup
public class DataLoader {

    // Loads all three CSV files — call this once at startup in MainApp
    public static void loadAll(UserRegistry userRegistry,
                                EventController eventController,
                                ManageBooking manageBooking,
                                WaitlistRegistry waitlistRegistry) {

        loadUsers("users.csv", userRegistry);
        loadEvents("events.csv", eventController);
        loadBookings("bookings.csv", manageBooking, userRegistry, eventController, waitlistRegistry);
    }

    // Reads users.csv and adds each user to the registry
    private static void loadUsers(String filename, UserRegistry userRegistry) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            // Skip the header line
            String line = reader.readLine();

            // Read each user row
            line = reader.readLine();
            while (line != null) {

                // Skip blank lines
                if (!line.trim().isEmpty()) {

                    // Split the line by comma
                    String[] parts = line.split(",");

                    // Need at least 4 columns
                    if (parts.length >= 4) {

                        // Wrap each row so one bad row doesn't stop the whole file
                        try {
                            String userId   = parts[0].trim();
                            String name     = parts[1].trim();
                            String email    = parts[2].trim();
                            String userType = parts[3].trim();

                            // Create the user and add to registry
                            User user = new User(userId, name, email, userType);
                            userRegistry.addUser(user);

                        } catch (Exception rowError) {
                            System.out.println("Skipped bad user row: " + line);
                            System.out.println("Reason: " + rowError.getMessage());
                        }
                    }
                }

                line = reader.readLine();
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Could not load users.csv: " + e.getMessage());
        }
    }

    // Reads events.csv and adds each event to the controller
    private static void loadEvents(String filename, EventController eventController) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            // Skip the header line
            String line = reader.readLine();

            // Read each event row
            line = reader.readLine();
            while (line != null) {

                // Skip blank lines
                if (!line.trim().isEmpty()) {

                    // Split the line by comma — use -1 to keep trailing empty fields
                    String[] parts = line.split(",", -1);

                    // Need at least 10 columns
                    if (parts.length >= 10) {

                        // Wrap each row so one bad row doesn't stop the whole file
                        try {
                            String eventId        = parts[0].trim();
                            String title          = parts[1].trim();
                            String dateTimeStr    = parts[2].trim();
                            String location       = parts[3].trim();
                            int capacity          = Integer.parseInt(parts[4].trim());
                            String statusStr      = parts[5].trim();
                            String eventType      = parts[6].trim();
                            String topic          = parts[7].trim();
                            String speakerName    = parts[8].trim();
                            String ageRestriction = parts[9].trim();

                            // Parse the date/time
                            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr);

                            // Parse the status
                            Event.Status status;
                            if (statusStr.equalsIgnoreCase("Cancelled")) {
                                status = Event.Status.Cancelled;
                            } else {
                                status = Event.Status.Active;
                            }

                            // Create the correct event subclass based on type
                            Event event;
                            if (eventType.equalsIgnoreCase("Workshop")) {
                                event = new Workshop(eventId, title, dateTime, location,
                                        capacity, status, topic);

                            } else if (eventType.equalsIgnoreCase("Seminar")) {
                                event = new Seminar(eventId, title, dateTime, location,
                                        capacity, status, speakerName);

                            } else if (eventType.equalsIgnoreCase("Concert")) {
                                event = new Concert(eventId, title, dateTime, location,
                                        capacity, status, ageRestriction);

                            } else {
                                // Unknown event type — skip this row
                                line = reader.readLine();
                                continue;
                            }

                            // Add directly via EventController
                            eventController.addEventObject(event);

                        } catch (Exception rowError) {
                            // Print which row failed but keep loading the rest
                            System.out.println("Skipped bad event row: " + line);
                            System.out.println("Reason: " + rowError.getMessage());
                        }
                    }
                }

                line = reader.readLine();
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Could not load events.csv: " + e.getMessage());
        }
    }

    // Reads bookings.csv and restores all bookings and waitlists
    private static void loadBookings(String filename,
                                      ManageBooking manageBooking,
                                      UserRegistry userRegistry,
                                      EventController eventController,
                                      WaitlistRegistry waitlistRegistry) {

        // We need to collect waitlisted bookings and sort them by createdAt
        // before adding to the waitlist, to preserve correct FIFO order
        ArrayList<Booking> waitlistedBookings = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            // Skip the header line
            String line = reader.readLine();

            // Read each booking row
            line = reader.readLine();
            while (line != null) {

                // Skip blank lines
                if (!line.trim().isEmpty()) {

                    // Split the line by comma
                    String[] parts = line.split(",");

                    // Need at least 5 columns
                    if (parts.length >= 5) {

                        // Wrap each row so one bad row doesn't stop the whole file
                        try {
                            String bookingId = parts[0].trim();
                            String userId    = parts[1].trim();
                            String eventId   = parts[2].trim();
                            String createdAt = parts[3].trim();
                            String statusStr = parts[4].trim();

                            // Parse the booking status
                            Booking.BookingStatus status;
                            if (statusStr.equalsIgnoreCase("Confirmed")) {
                                status = Booking.BookingStatus.Confirmed;
                            } else if (statusStr.equalsIgnoreCase("Waitlisted")) {
                                status = Booking.BookingStatus.Waitlisted;
                            } else {
                                status = Booking.BookingStatus.Cancelled;
                            }

                            // Create the booking object
                            Booking booking = new Booking(bookingId, userId, eventId, createdAt, status);

                            // Add to ManageBooking directly (skip the normal rule checks)
                            manageBooking.loadBooking(booking);

                            // Collect waitlisted bookings to sort and add to waitlists later
                            if (status == Booking.BookingStatus.Waitlisted) {
                                waitlistedBookings.add(booking);
                            }

                        } catch (Exception rowError) {
                            System.out.println("Skipped bad booking row: " + line);
                            System.out.println("Reason: " + rowError.getMessage());
                        }
                    }
                } // end if not empty

                line = reader.readLine();
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Could not load bookings.csv: " + e.getMessage());
        }

        // Sort waitlisted bookings by createdAt (earliest first = correct FIFO order)
        // Using a simple bubble sort to keep it beginner-friendly
        for (int i = 0; i < waitlistedBookings.size() - 1; i++) {
            for (int j = 0; j < waitlistedBookings.size() - 1 - i; j++) {
                String timeA = waitlistedBookings.get(j).getCreatedAt();
                String timeB = waitlistedBookings.get(j + 1).getCreatedAt();

                // String comparison works here because format is yyyy-MM-ddTHH:mm
                if (timeA.compareTo(timeB) > 0) {
                    Booking temp = waitlistedBookings.get(j);
                    waitlistedBookings.set(j, waitlistedBookings.get(j + 1));
                    waitlistedBookings.set(j + 1, temp);
                }
            }
        }

        // Now add each waitlisted booking to the correct event's waitlist
        for (Booking booking : waitlistedBookings) {
            Waitlist waitlist = waitlistRegistry.getOrCreate(booking.getEventID());
            waitlist.addToWaitlist(booking);
        }
    }
}
