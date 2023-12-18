package factories;

import entries.PersonEntry;
import entries.TicketEntry;

import java.util.Map;

public interface TicketFactory {
    TicketEntry getRestaurantTicket(Map<PersonEntry, Double> map, PersonEntry person);
    TicketEntry getCinemaTicket(Map<PersonEntry, Double> map, PersonEntry person);
    TicketEntry getConcertTicket(Map<PersonEntry, Double> map, PersonEntry person);
    TicketEntry getTaxiTicket(Map<PersonEntry, Double> map, PersonEntry person);
    TicketEntry getAirplaneTicket(Map<PersonEntry, Double> map, PersonEntry person);
    TicketEntry getBusTicket(Map<PersonEntry, Double> map, PersonEntry person);
    TicketEntry getTicket(Map<PersonEntry, Double> map, PersonEntry person);

}
