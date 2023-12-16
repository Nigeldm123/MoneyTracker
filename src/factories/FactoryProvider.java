package factories;

public class FactoryProvider {

    public static TicketFactory EvenTicket() {
        return new EvenTicketFactory();
    }

    public static TicketFactory UnevenTicket() {
        return new UnevenTicketFactory();
    }
}
