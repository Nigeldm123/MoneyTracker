package factories;

import entries.PersonEntry;
import entries.TicketEntry;

import java.util.Map;

public abstract class TicketFactory {
    public abstract TicketEntry getRestaurantTicket(Map<PersonEntry, Double> map, PersonEntry person);
    public abstract TicketEntry getCinemaTicket(Map<PersonEntry, Double> map, PersonEntry person);
    public abstract TicketEntry getTaxiTicket(Map<PersonEntry, Double> map, PersonEntry person);
    public abstract TicketEntry getAirplaneTicket(Map<PersonEntry, Double> map, PersonEntry person);
    public abstract TicketEntry getBusTicket(Map<PersonEntry, Double> map, PersonEntry person);
    public abstract TicketEntry getTicket(Map<PersonEntry, Double> map, PersonEntry person);

}
