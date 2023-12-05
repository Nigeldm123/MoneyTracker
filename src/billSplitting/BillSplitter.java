package billSplitting;

import database.PersonDatabase;
import entries.PersonEntry;
import entries.TicketEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BillSplitter {
    private GlobalBill bill;
    public BillSplitter(GlobalBill bill) {
        this.bill = bill;
    }

    public Map<PersonEntry, Map<Double,PersonEntry>> payBill() {



        for (Map<PersonEntry, Map<TicketEntry.eventsEnum, Map<Double,Integer>>> equalBill : bill.getEqualBill()) {
            for (PersonEntry payer : equalBill.keySet()) {
                for (Map<TicketEntry.eventsEnum,Map<Double,Integer>> eventTicket : equalBill.values()) {
                    for (Map<Double,Integer> ticket : eventTicket.values()) {
                        Pair p = new Pair<Double,Integer>()
                    }
            }
        }

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
}
