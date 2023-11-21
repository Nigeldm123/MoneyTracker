package entries;

import java.util.Map;

public class TicketEntry {
    private final Map<PersonEntry, Double> map;
    private PersonEntry person;
    private Double price;
    public enum events {RESTAURANT, CINEMA, TAXI, CONCERT, AIRPLANE, BUS, OTHERS};
    private events myEvent;
    private events event;
    private boolean split;      // true = evenly split      false = not evenly split
    private String payer;

    public TicketEntry(Map<PersonEntry,Double> map, events event, boolean split, String payer) {
        this.map = map;
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

    public events getEvent() {
        return event;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public Map<PersonEntry, Double> getMap() {
        return map;
    }

/*public String getEvent() {
        String return_val;
        switch (myEvent) {
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
        return return_val;
    }*/

}
