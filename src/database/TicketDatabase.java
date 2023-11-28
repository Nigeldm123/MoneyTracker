package database;

import entries.PersonEntry;
import entries.TicketEntry;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TicketDatabase extends Database<TicketEntry> {
    private static TicketDatabase database;
    private ArrayList<TicketEntry> ticketList;
    private PropertyChangeSupport support;

    private TicketDatabase() {
        /*this.totalTicketList = new ArrayList<>();
        this.totalEqualTicketList = new ArrayList<>();*/
        this.support = new PropertyChangeSupport(this);
        ticketList = new ArrayList<>();
    }

    public static TicketDatabase getInstance() {
        if (database == null) {
            database = new TicketDatabase();
        }
        return database;
    }
    public void addEntry(TicketEntry ticket) {
        support.firePropertyChange("",ticket, ticketList.add(ticket));
    }

    public void removeEntry(TicketEntry ticket) {
        support.firePropertyChange("",ticket, ticketList.remove(ticket));
    }

    public void addObserver(PropertyChangeListener pcl){
        support.addPropertyChangeListener(pcl);
    };
    public void removeObserver(PropertyChangeListener pcl){
        support.removePropertyChangeListener(pcl);
    };

    public ArrayList<TicketEntry> getTicketList() {
        return ticketList;
    }

}
