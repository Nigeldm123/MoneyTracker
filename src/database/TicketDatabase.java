package database;

import entries.PersonEntry;
import entries.TicketEntry;

import java.util.HashMap;
import java.util.Map;

public class TicketDatabase extends Database {
    private static TicketDatabase database;
    private String event;
    private Map<PersonEntry,Double> tickets;
    private TicketDatabase() {
        this.tickets = new HashMap<>();
    }

    public static TicketDatabase getInstance() {
        if (database == null) {
            database = new TicketDatabase();
        }
        return database;
    }
    public void addEntry(TicketEntry ticket) {
        tickets.put(ticket.getPerson(),ticket.getPrice());
    }

    public void removeEntry(TicketEntry ticket) {
        tickets.remove(ticket.getPerson(),ticket.getPrice());
    }

}
