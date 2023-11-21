package entries;

public class TicketEntry extends Entry {
    private PersonEntry person;
    private Double price;

    public TicketEntry(PersonEntry person, Double price) {
        super("TicketEntry");
        this.person = person;
        this.price = price;
    }

    public PersonEntry getPerson() {
        return person;
    }

    public Double getPrice() {
        return price;
    }

}
