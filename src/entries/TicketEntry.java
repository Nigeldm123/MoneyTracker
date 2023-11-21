package entries;

public class TicketEntry<T> extends Entry<T> {
    private PersonEntry person;
    private Double price;

    public TicketEntry(T template, PersonEntry person, Double price) {
        super(template);
        this.person = person;
        this.price = price;
    }

    public PersonEntry<T> getPerson() {
        return person;
    }

    public Double getPrice() {
        return price;
    }

}
