package entries;

public class TicketEntry {
    private PersonEntry person;
    private Double price;
    private enum events {RESTAURANT, CINEMA, TAXI, CONCERT, AIRPLANE, BUS, OTHERS};
    private events myEvent;
    private String event;
    private boolean split;      // true = evenly split      false = not evenly split

    public TicketEntry(PersonEntry person, Double price, String event, boolean split) {
        this.person = person;
        this.price = price;
        this.event = event;
        this.split = split;
    }

    public PersonEntry getPerson() {
        return person;
    }

    public Double getPrice() {
        return price;
    }

    public boolean isSplit() {
        return split;
    }

    public String getEvent() {
        return event;
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
