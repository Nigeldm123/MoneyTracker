package factories;

import entries.PersonEntry;
import entries.TicketEntry;

import java.util.Map;

public class TicketFactory {
    public TicketEntry getTicket(Map<PersonEntry, Double> map,TicketEntry.eventsEnum event, Boolean split, PersonEntry person){
        return new TicketEntry(map,event,split,person);
    }
}