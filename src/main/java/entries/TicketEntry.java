package entries;

import java.util.Map;

public class TicketEntry {
    private final Map<PersonEntry, Double> ticketMap;
    public enum eventsEnum {RESTAURANT, CINEMA, TAXI, CONCERT, AIRPLANE, BUS, OTHERS}
    private eventsEnum event;
    private boolean split;      // true = evenly split      false = not evenly split
    private PersonEntry payer;

    public TicketEntry(Map<PersonEntry, Double> map, eventsEnum event, boolean split, PersonEntry payer) {
        this.ticketMap = map;
        this.event = event;
        this.split = split;
        this.payer = payer;     // check person who payed in list of people in group

    }

    public boolean isSplit() {
        return split;
    }

    public eventsEnum getEvent() {
        return event;
    }

    public PersonEntry getPayer() {
        return payer;
    }

    public Map<PersonEntry, Double> getMap() {
        return ticketMap;
    }

}