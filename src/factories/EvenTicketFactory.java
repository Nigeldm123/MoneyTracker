package factories;

import entries.PersonEntry;
import entries.TicketEntry;

import java.util.Map;

public class EvenTicketFactory implements TicketFactory {
    public EvenTicketFactory() {
    }

    public TicketEntry getRestaurantTicket(Map<PersonEntry, Double> map, PersonEntry person) {
        return new TicketEntry(map, TicketEntry.eventsEnum.RESTAURANT,true,person);
    }
    public TicketEntry getCinemaTicket(Map<PersonEntry, Double> map, PersonEntry person) {
        return new TicketEntry(map, TicketEntry.eventsEnum.CINEMA,true,person);
    }

    public TicketEntry getConcertTicket(Map<PersonEntry, Double> map, PersonEntry person) {
        return new TicketEntry(map, TicketEntry.eventsEnum.CONCERT,true,person);
    }

    public TicketEntry getTaxiTicket(Map<PersonEntry, Double> map, PersonEntry person) {
        return new TicketEntry(map, TicketEntry.eventsEnum.TAXI,true,person);
    }

    public TicketEntry getAirplaneTicket(Map<PersonEntry, Double> map, PersonEntry person) {
        return new TicketEntry(map, TicketEntry.eventsEnum.OTHERS,true,person);
    }

    public TicketEntry getBusTicket(Map<PersonEntry, Double> map, PersonEntry person) {
        return new TicketEntry(map, TicketEntry.eventsEnum.BUS,true,person);
    }

    public TicketEntry getTicket(Map<PersonEntry, Double> map, PersonEntry person) {
        return new TicketEntry(map, TicketEntry.eventsEnum.OTHERS,true,person);
    }

}