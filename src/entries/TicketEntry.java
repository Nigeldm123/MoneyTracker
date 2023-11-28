package entries;

import database.PersonDatabase;

import java.util.Map;

public class TicketEntry {
    private final Map<PersonEntry, Double> ticketMap;
    private PersonEntry person;
    private Double price;

    public enum eventsEnum {RESTAURANT, CINEMA, TAXI, CONCERT, AIRPLANE, BUS, OTHERS}

    ;
    private eventsEnum myEvent;
    private eventsEnum event;
    private boolean split;      // true = evenly split      false = not evenly split
    private PersonEntry payer;

    public TicketEntry(Map<PersonEntry, Double> map, eventsEnum event, boolean split, PersonEntry payer) {
        this.ticketMap = map;
        this.event = event;
        this.split = split;
        this.payer = payer;     // check person who payed in list of people in group
    }

    public PersonEntry getPerson() {
        return person;
    }

    public Double getPrice() {
        //this.price = map.get(price);
        return price;
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

    public void setPayer(PersonEntry payer) {
        this.payer = payer;
    }

    public Map<PersonEntry, Double> getMap() {
        return ticketMap;
    }

    public void clear() {
        ticketMap.clear();
    }

}