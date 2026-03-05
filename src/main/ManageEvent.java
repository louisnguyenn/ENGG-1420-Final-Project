package com.guelph.engg1420finalprojectjavafx;

import java.util.ArrayList;

public class ManageEvent {
    private ArrayList<Event> eventList; //

    public ManageEvent(ArrayList<Event> events) {
        this.eventList = new ArrayList<Event>();
    }

    // Get events in the array list, and will return an updated arraylist
    public ArrayList<Event> getEvents() {
        return new ArrayList<>();
    }

    public void addEvents(Event event) {
        this.eventList.add(event);
    }

    //Search for the events using for loop. it loops through event list
    //Checks the title, if it finds a match with the search it will return the event
    //No match will return null
    public Event searchEvent(String title) {
        for (Event event : this.eventList) {
            if (event.getTitle().equalsIgnoreCase(title)) {
                return event;
            }
        }
        return null;
    }

}
