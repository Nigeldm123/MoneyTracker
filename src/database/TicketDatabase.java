package database;

import entries.PersonEntry;
import entries.TicketEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TicketDatabase extends Database<TicketEntry> {
    private static TicketDatabase database;
    private Map<PersonEntry,Double> ticketPerson;
    private ArrayList<Map<PersonEntry,Double>> ticketsGroup;
    private Map<String, ArrayList<Map<PersonEntry,Double>>> ticketEvents;
    private String event;
    private boolean split;

    private TicketDatabase() {
        this.ticketPerson = new HashMap<>();
    }

    public static TicketDatabase getInstance() {
        if (database == null) {
            database = new TicketDatabase();
        }
        return database;
    }
    public void addEntry(TicketEntry ticket) {
        ticketPerson.put(ticket.getPerson(),ticket.getPrice());
        ticketsGroup.add(ticketPerson);
        ticketEvents.put(ticket.getEvent(), ticketsGroup);

        /*if (ticket.isSplit())
            this.ticketPerson.put(ticket.getPerson(),ticket.getPrice());
        else

        this.event = ticket.getEvent();*/
    }

    public void removeEntry(TicketEntry ticket) {
        tickets.remove(ticket.getPerson(),ticket.getPrice());
    }

}
