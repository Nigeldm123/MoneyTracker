package entries;

public class TicketEntry {
    private PersonEntry person;
    private Double price;

    public TicketEntry(PersonEntry person, Double price) {
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
