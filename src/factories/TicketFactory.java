package factories;

import entries.PersonEntry;
import entries.TicketEntry;

import java.util.Map;

public interface TicketFactory {
    public TicketEntry getRestaurantTicket(Map<PersonEntry, Double> map, PersonEntry person);
    public TicketEntry getCinemaTicket(Map<PersonEntry, Double> map, PersonEntry person);
    public TicketEntry getTaxiTicket(Map<PersonEntry, Double> map, PersonEntry person);
    public TicketEntry getAirplaneTicket(Map<PersonEntry, Double> map, PersonEntry person);
    public TicketEntry getBusTicket(Map<PersonEntry, Double> map, PersonEntry person);
    public TicketEntry getTicket(Map<PersonEntry, Double> map, PersonEntry person);

}
