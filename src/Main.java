import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        ManageEvent eventList = new ManageEvent();

        LocalDateTime dt = LocalDateTime.parse("2026-02-12T14:30");
        Workshop workshopEvent = new Workshop("E101", "Intro. to Programming", dt, "MACN 113", 100,
                Event.Status.Active, "Memory Allocation");

        eventList.addEvent(workshopEvent);

        System.out.println(eventList);
    }
}
