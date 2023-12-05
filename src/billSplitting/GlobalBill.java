package billSplitting;

import database.TicketDatabase;
import database.PersonDatabase;
import entries.PersonEntry;
import entries.TicketEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GlobalBill {

    private ArrayList<Map<PersonEntry, Map<TicketEntry.eventsEnum, Double>>> equalBill;
    private ArrayList<Map<PersonEntry, Map<TicketEntry.eventsEnum, Map<PersonEntry, Double>>>> regularBill;
    private Map<PersonEntry, Map<TicketEntry.eventsEnum, Double>> payerEqualMap;
    private Map<PersonEntry, Map<TicketEntry.eventsEnum, Map<PersonEntry, Double>>> payerMap;
    private Map<TicketEntry.eventsEnum, Double> eventEqualMap;
    private Map<TicketEntry.eventsEnum, Map<PersonEntry, Double>> eventMap;

    public GlobalBill(TicketDatabase tb) {
        equalBill = new ArrayList<>();
        regularBill = new ArrayList<>();
        for (TicketEntry ticket : tb.getTicketList()) {
            if (ticket.isSplit()) {
                addEqualBill(ticket);
            } else {
                addRegularBill(ticket);
            }
        }
    }

    public void addEqualBill(TicketEntry ticket) {
        ArrayList<PersonEntry> personList = PersonDatabase.getInstance().getGroup();
        Map<PersonEntry, Double> ticketMap = ticket.getMap();
        PersonEntry payer = ticket.getPayer();

        for (PersonEntry personEntry : personList) {
            if (Objects.equals(personEntry.getName(), payer.getName())) {
                if (payerEqualMap == null) {
                    payerEqualMap = new HashMap<>();
                }
                for (TicketEntry.eventsEnum elem : TicketEntry.eventsEnum.values()) {
                    if (elem == ticket.getEvent()) {
                        double full_price = 0;
                        for (double price : ticketMap.values()) {
                            full_price += price;
                        }
                        if (eventEqualMap == null) {
                            eventEqualMap = new HashMap<>();
                        }
                        eventEqualMap.put(ticket.getEvent(), full_price);
                        payerEqualMap.put(payer, eventEqualMap);
                        equalBill.add(payerEqualMap);
                    }
                }
            }
        }
    }

    public void addRegularBill(TicketEntry ticket) {
        ArrayList<PersonEntry> personList = PersonDatabase.getInstance().getGroup();
        Map<PersonEntry, Double> ticketMap = ticket.getMap();
        PersonEntry payer = ticket.getPayer();

        for (PersonEntry personEntry : personList) {
            if (Objects.equals(personEntry.getName(), payer.getName())) {
                if (payerMap == null) {
                    payerMap = new HashMap<>();
                }
                for (TicketEntry.eventsEnum elem : TicketEntry.eventsEnum.values()) {
                    if (elem == ticket.getEvent()) {
                        if (eventMap == null) {
                            eventMap = new HashMap<>();
                        }
                        eventMap.put(ticket.getEvent(), ticketMap);
                        payerMap.put(payer, eventMap);
                        regularBill.add(payerMap);
                    }
                }
            }
        }
    }

    public ArrayList<Map<PersonEntry, Map<TicketEntry.eventsEnum, Double>>> getEqualBill() {
        return equalBill;
    }

    public ArrayList<Map<PersonEntry, Map<TicketEntry.eventsEnum, Map<PersonEntry, Double>>>> getRegularBill() {
        return regularBill;
    }

    public Map<PersonEntry, Map<TicketEntry.eventsEnum, Double>> getPayerEqualMap() {
        return payerEqualMap;
    }

    public Map<PersonEntry, Map<TicketEntry.eventsEnum, Map<PersonEntry, Double>>> getPayerMap() {
        return payerMap;
    }

    public Map<TicketEntry.eventsEnum, Double> getEventEqualMap() {
        return eventEqualMap;
    }

    public Map<TicketEntry.eventsEnum, Map<PersonEntry, Double>> getEventMap() {
        return eventMap;
    }
}
