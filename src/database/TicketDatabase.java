package database;

import java.util.Map;

public class TicketDatabase extends Database {
    private static TicketDatabase database;
    private String event;
    private Map<PersonDatabase,Double> ticket;
    private TicketDatabase() {
    }
    public static TicketDatabase getInstance() {
        if (database == null) {
            database = new TicketDatabase();
        }
        return database;
    }
    public void addEntry() {

    }

    public void removeEntry() {

    }

}
