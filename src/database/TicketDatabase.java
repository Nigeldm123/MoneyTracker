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

    private ArrayList<Map<PersonEntry, Map<TicketEntry.eventsEnum, Double>>> totalEqualTicketList;
    private ArrayList<Map<PersonEntry, Map<TicketEntry.eventsEnum, Map<PersonEntry, Double>>>> totalTicketList;
    private Map<PersonEntry, Map<TicketEntry.eventsEnum, Double>> payerEqualMap;
    private Map<PersonEntry, Map<TicketEntry.eventsEnum, Map<PersonEntry, Double>>> payerMap;
    private Map<TicketEntry.eventsEnum, Double> eventEqualMap;
    private Map<TicketEntry.eventsEnum, Map<PersonEntry, Double>> eventMap;

    private TicketEntry t;

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
        ticketList.add(ticket);

        /*ArrayList<PersonEntry> personList = PersonDatabase.getInstance().getGroup();
        Map<PersonEntry, Double> ticketMap = ticket.getMap();
        PersonEntry payer = ticket.getPayer();

        if (ticket.isSplit()) {     // equally split
            for (PersonEntry personEntry : personList) {
                if (Objects.equals(personEntry.getName(), payer.getName())) {
                    for (TicketEntry.eventsEnum elem : TicketEntry.eventsEnum.values()) {
                        if (elem == ticket.getEvent()) {
                            double full_price = 0;
                            for (double price : ticket.getMap().values()) {
                                full_price += price;
                            }
                            eventEqualMap.put(ticket.getEvent(),full_price);
                            payerEqualMap.put(payer, eventEqualMap);
                            totalEqualTicketList.add(payerEqualMap);
                        } else {
                            eventEqualMap = new HashMap<>();
                        }
                    }
                } else {
                    payerEqualMap = new HashMap<>();
                }
            }
        } else {        // not equally split
            for (PersonEntry personEntry : personList) {
                if (Objects.equals(personEntry.getName(), payer.getName())) {
                    for (TicketEntry.eventsEnum elem : TicketEntry.eventsEnum.values()) {
                        if (elem == ticket.getEvent()) {
                            eventMap.put(ticket.getEvent(), ticketMap);
                            payerMap.put(payer, eventMap);
                            totalTicketList.add(payerMap);
                        } else {
                            eventMap = new HashMap<>();
                        }
                    }
                } else {
                    payerMap = new HashMap<>();
                }
            }
        }*/
    }

    public void removeEntry(TicketEntry ticket) {
        ticketList.remove(ticket);
    }

    public void addObserver(PropertyChangeListener pcl){
        support.addPropertyChangeListener(pcl);
    };
    public void removeObserver(PropertyChangeListener pcl){
        support.removePropertyChangeListener(pcl);
    };

}
