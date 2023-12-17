package factories;

import entries.PersonEntry;
import entries.TicketEntry;

import java.util.Map;

public class UnevenTicketFactory implements TicketFactory {
    public UnevenTicketFactory() {
    }

    public TicketEntry getRestaurantTicket(Map<PersonEntry, Double> map, PersonEntry person) {
        return new TicketEntry(map, TicketEntry.eventsEnum.RESTAURANT,false,person);
    }
    public TicketEntry getCinemaTicket(Map<PersonEntry, Double> map, PersonEntry person) {
        return new TicketEntry(map, TicketEntry.eventsEnum.CINEMA,false,person);
    }

    public TicketEntry getConcertTicket(Map<PersonEntry, Double> map, PersonEntry person) {
        return new TicketEntry(map, TicketEntry.eventsEnum.CONCERT,true,person);
    }

    public TicketEntry getTaxiTicket(Map<PersonEntry, Double> map, PersonEntry person) {
        return new TicketEntry(map, TicketEntry.eventsEnum.TAXI,false,person);
    }

    public TicketEntry getAirplaneTicket(Map<PersonEntry, Double> map, PersonEntry person) {
        return new TicketEntry(map, TicketEntry.eventsEnum.OTHERS,false,person);
    }

    public TicketEntry getBusTicket(Map<PersonEntry, Double> map, PersonEntry person) {
        return new TicketEntry(map, TicketEntry.eventsEnum.BUS,false,person);
    }

    public TicketEntry getTicket(Map<PersonEntry, Double> map, PersonEntry person) {
        return new TicketEntry(map, TicketEntry.eventsEnum.OTHERS,false,person);
    }
}
