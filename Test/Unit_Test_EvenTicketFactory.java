import entries.PersonEntry;
import entries.TicketEntry;
import factories.EvenTicketFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class Unit_Test_EvenTicketFactory {

    @Test
    public void testGetRestaurantTicket() {
        // Arrange
        EvenTicketFactory ticketFactory = new EvenTicketFactory();
        Map<PersonEntry, Double> ticketMap = createMockTicketMap();
        PersonEntry person = new PersonEntry("John");

        // Act
        TicketEntry restaurantTicket = ticketFactory.getRestaurantTicket(ticketMap, person);

        // Assert
        Assert.assertEquals(TicketEntry.eventsEnum.RESTAURANT, restaurantTicket.getEvent());
        Assert.assertEquals(true, restaurantTicket.isSplit());
        Assert.assertEquals(person, restaurantTicket.getPayer());
        Assert.assertEquals(ticketMap, restaurantTicket.getMap());
    }

    @Test
    public void testGetCinemaTicket() {
        // Arrange
        EvenTicketFactory ticketFactory = new EvenTicketFactory();
        Map<PersonEntry, Double> ticketMap = createMockTicketMap();
        PersonEntry person = new PersonEntry("Alice");

        // Act
        TicketEntry cinemaTicket = ticketFactory.getCinemaTicket(ticketMap, person);

        // Assert
        Assert.assertEquals(TicketEntry.eventsEnum.CINEMA, cinemaTicket.getEvent());
        Assert.assertEquals(true, cinemaTicket.isSplit());
        Assert.assertEquals(person, cinemaTicket.getPayer());
        Assert.assertEquals(ticketMap, cinemaTicket.getMap());
    }

    @Test
    public void testGetTaxiTicket() {
        // Arrange
        EvenTicketFactory ticketFactory = new EvenTicketFactory();
        Map<PersonEntry, Double> ticketMap = createMockTicketMap();
        PersonEntry person = new PersonEntry("Bob");

        // Act
        TicketEntry taxiTicket = ticketFactory.getTaxiTicket(ticketMap, person);

        // Assert
        Assert.assertEquals(TicketEntry.eventsEnum.TAXI, taxiTicket.getEvent());
        Assert.assertEquals(true, taxiTicket.isSplit());
        Assert.assertEquals(person, taxiTicket.getPayer());
        Assert.assertEquals(ticketMap, taxiTicket.getMap());
    }

    private Map<PersonEntry, Double> createMockTicketMap() {
        Map<PersonEntry, Double> ticketMap = new HashMap<>();
        ticketMap.put(new PersonEntry("Friend1"), 20.0);
        ticketMap.put(new PersonEntry("Friend2"), 30.0);
        // Add more entries as needed
        return ticketMap;
    }
}
