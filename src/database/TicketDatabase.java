package database;

import entries.PersonEntry;
import entries.TicketEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TicketDatabase extends Database<TicketEntry> {
    private static TicketDatabase database;
    private final HashMap<Object, Object> ticketPerson;
    private ArrayList<Map<String, Map<String, Map<String, Double>>>> finalTicketList;
    //private Map<String, ArrayList<Map<PersonEntry,Double>>> finalTicket;
    //private Map<String, Double> eventMap;
    private Map<String, Double> ticketMap;
    private Map<String, Map<String, Double>> eventMap;
    private Map<String, Map<String, Map<String, Double>>> payerMap;
    private String event;
    private boolean split;
    private PersonDatabase p;
    private String payer;
    private TicketEntry t;
    public enum events {RESTAURANT, CINEMA, TAXI, CONCERT, AIRPLANE, BUS, OTHERS};
    private TicketDatabase.events myEvent;
    private double full_price = 0.0;

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
        /*ticketPerson.put(ticket.getPerson(),ticket.getPrice());
        ticketsGroup.add(ticketPerson);*/
        //finalTicket.put(ticket.getEvent(), ticketsGroup); CHECK LATER

        /*if (ticket.isSplit())
            this.ticketPerson.put(ticket.getPerson(),ticket.getPrice());
        else
            this.event = ticket.getEvent();*/
        ArrayList<PersonEntry> personList = p.getInstance().getGroup();
        payer = ticket.getPayer();
        for(PersonEntry personEntry : personList){
            if(Objects.equals(personEntry.getName(), payer)){
                String return_val = "null";
                switch (ticket.getEvent()) {
                    case RESTAURANT:
                        return_val = "restaurant";
                        break;
                    case CINEMA:
                        return_val = "cinema";
                        break;
                    case TAXI:
                        return_val = "taxi";
                        break;
                    case CONCERT:
                        return_val = "concert";
                        break;
                    case AIRPLANE:
                        return_val = "airplane";
                        break;
                    case BUS:
                        return_val = "bus";
                        break;
                    case OTHERS:
                        return_val = this.event;
                        break;
                }
                event = return_val;
                if(Objects.equals(event, ticket.getEvent())){
                    if(t.isSplit()){
                        //full_price = full_price + t.getPrice();
                        eventMap.put(event,ticketMap);
                        payerMap.put(payer,eventMap);
                        finalTicketList.add(payerMap);
                    }
                    else {
                        eventMap.put(event,ticketMap);
                        payerMap.put(payer,eventMap);
                        finalTicketList.add(payerMap);
                    }
                }
            }
        }
    }

    public void removeEntry(TicketEntry ticket) {
        t.clear();
    }

}
