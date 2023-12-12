package billSplitting;

import database.PersonDatabase;
import database.TicketDatabase;
import entries.PersonEntry;
import entries.TicketEntry;
import org.javatuples.Pair;

import java.util.*;

public class GlobalBill {

    private ArrayList<Map<PersonEntry, Map<TicketEntry.eventsEnum, Pair<Double,Set<PersonEntry>>>>> equalBill;
    private ArrayList<Map<PersonEntry, Map<TicketEntry.eventsEnum, Map<PersonEntry, Double>>>> regularBill;
    private Map<PersonEntry, Map<TicketEntry.eventsEnum, Pair<Double,Set<PersonEntry>>>> payerEqualMap;
    private Map<PersonEntry, Map<TicketEntry.eventsEnum, Map<PersonEntry, Double>>> payerMap;
    private Map<TicketEntry.eventsEnum, Pair<Double,Set<PersonEntry>>> eventEqualMap;
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
                payerEqualMap = new HashMap<>();
                for (TicketEntry.eventsEnum elem : TicketEntry.eventsEnum.values()) {
                    if (elem == ticket.getEvent()) {
                        eventEqualMap = new HashMap<>();
                        double full_price = 0;
                        for (double price : ticketMap.values()) {
                            full_price += price;
                        }
                        Pair<Double,Set<PersonEntry>> ticketPrice = new Pair<>(full_price,ticketMap.keySet());
                        eventEqualMap.put(ticket.getEvent(), ticketPrice);
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
                payerMap = new HashMap<>();
                for (TicketEntry.eventsEnum elem : TicketEntry.eventsEnum.values()) {
                    if (elem == ticket.getEvent()) {
                        eventMap = new HashMap<>();
                        eventMap.put(ticket.getEvent(), ticketMap);
                        payerMap.put(payer, eventMap);
                        regularBill.add(payerMap);
                    }
                }
            }
        }
    }

    public ArrayList<Map<PersonEntry, Map<TicketEntry.eventsEnum, Pair<Double,Set<PersonEntry>>>>> getEqualBill() {
        return equalBill;
    }

    public ArrayList<Map<PersonEntry, Map<TicketEntry.eventsEnum, Map<PersonEntry, Double>>>> getRegularBill() {
        return regularBill;
    }
}