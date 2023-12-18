import entries.PersonEntry;
import entries.TicketEntry;
import factories.EvenTicketFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class UTest_EvenTicketFactory {
    EvenTicketFactory ticketFactory;
    Map<PersonEntry, Double> ticketMap;
    PersonEntry person;
    TicketEntry ticket;
    @Before
    public void initialize() {
        ticketFactory = new EvenTicketFactory();
        ticketMap = createMockTicketMap();
    }

    @Test
    public void testGetRestaurantTicket() {
        person = new PersonEntry("John");
        ticket = ticketFactory.getRestaurantTicket(ticketMap, person);

        Assert.assertEquals(TicketEntry.eventsEnum.RESTAURANT, ticket.getEvent());
        Assert.assertTrue(ticket.isSplit());
        Assert.assertEquals(person, ticket.getPayer());
        Assert.assertEquals(ticketMap, ticket.getMap());
    }

    @Test
    public void testGetCinemaTicket() {
        person = new PersonEntry("Alice");
        ticket = ticketFactory.getCinemaTicket(ticketMap, person);

        Assert.assertEquals(TicketEntry.eventsEnum.CINEMA, ticket.getEvent());
        Assert.assertTrue(ticket.isSplit());
        Assert.assertEquals(person, ticket.getPayer());
        Assert.assertEquals(ticketMap, ticket.getMap());
    }

    @Test
    public void testGetTaxiTicket() {
        person = new PersonEntry("Bob");
        ticket = ticketFactory.getTaxiTicket(ticketMap, person);

        Assert.assertEquals(TicketEntry.eventsEnum.TAXI, ticket.getEvent());
        Assert.assertTrue(ticket.isSplit());
        Assert.assertEquals(person, ticket.getPayer());
        Assert.assertEquals(ticketMap, ticket.getMap());
    }

    @Test
    public void testGetConcertTicket() {
        person = new PersonEntry("Bob");
        ticket = ticketFactory.getConcertTicket(ticketMap, person);

        Assert.assertEquals(TicketEntry.eventsEnum.CONCERT, ticket.getEvent());
        Assert.assertTrue(ticket.isSplit());
        Assert.assertEquals(person, ticket.getPayer());
        Assert.assertEquals(ticketMap, ticket.getMap());
    }

    @Test
    public void testGetAirplaneTicket() {
        person = new PersonEntry("Jess");
        ticket = ticketFactory.getAirplaneTicket(ticketMap, person);

        Assert.assertEquals(TicketEntry.eventsEnum.AIRPLANE, ticket.getEvent());
        Assert.assertTrue(ticket.isSplit());
        Assert.assertEquals(person, ticket.getPayer());
        Assert.assertEquals(ticketMap, ticket.getMap());
    }

    @Test
    public void testGetBusTicket() {
        person = new PersonEntry("Max");
        ticket = ticketFactory.getBusTicket(ticketMap, person);

        Assert.assertEquals(TicketEntry.eventsEnum.BUS, ticket.getEvent());
        Assert.assertTrue(ticket.isSplit());
        Assert.assertEquals(person, ticket.getPayer());
        Assert.assertEquals(ticketMap, ticket.getMap());
    }

    @Test
    public void testGetOtherTicket() {
        person = new PersonEntry("Sophie");
        ticket = ticketFactory.getTicket(ticketMap, person);

        Assert.assertEquals(TicketEntry.eventsEnum.OTHERS, ticket.getEvent());
        Assert.assertTrue(ticket.isSplit());
        Assert.assertEquals(person, ticket.getPayer());
        Assert.assertEquals(ticketMap, ticket.getMap());
    }

    private Map<PersonEntry, Double> createMockTicketMap() {
        Map<PersonEntry, Double> ticketMap = new HashMap<>();
        ticketMap.put(new PersonEntry("Friend1"), 20.0);
        ticketMap.put(new PersonEntry("Friend2"), 30.0);
        // Add more main.entries as needed
        return ticketMap;
    }
}
