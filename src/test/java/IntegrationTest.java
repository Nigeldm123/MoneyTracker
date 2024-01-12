import billSplitting.BillSplitter;
import billSplitting.GlobalBill;
import database.Database;
import entries.PersonEntry;
import entries.TicketEntry;
import database.PersonDatabase;
import database.TicketDatabase;
import factories.FactoryProvider;
import factories.TicketFactory;
import iteratorPattern.DatabaseIterator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class IntegrationTest {
    PersonDatabase personDatabase;
    TicketDatabase ticketDatabase;
    TicketFactory evenFact, unevenFact;
    PersonEntry person1,person2,person3,person4,person5;
    TicketEntry ticketEntry1,ticketEntry2,ticketEntry3;

    @Before
    public void initialize() {
        System.out.println("Initializing integration test");

        personDatabase = PersonDatabase.getInstance();
        ticketDatabase = TicketDatabase.getInstance();
        evenFact = FactoryProvider.EvenTicket();
        unevenFact = FactoryProvider.UnevenTicket();

        // Create some PersonEntry instances
        person1 = new PersonEntry("John");
        person2 = new PersonEntry("Alice");
        person3 = new PersonEntry("Max");
        person4 = new PersonEntry("Sophie");
        person5 = new PersonEntry("Luna");

        // Add PersonEntry instances to PersonDatabase
        personDatabase.addEntry(person1);
        personDatabase.addEntry(person2);
        personDatabase.addEntry(person3);
        personDatabase.addEntry(person4);
        personDatabase.addEntry(person5);

        // Create a TicketEntries with the PersonEntry instances
        Map<PersonEntry, Double> ticketMap1 = new HashMap<>();
        ticketMap1.put(person1, 20.0);
        ticketMap1.put(person2, 30.0);
        ticketMap1.put(person3, 40.0);

        Map<PersonEntry, Double> ticketMap2 = new HashMap<>();
        ticketMap2.put(person1, 10.0);
        ticketMap2.put(person3, 10.0);
        ticketMap2.put(person5, 40.0);
        ticketMap2.put(person2, 40.0);

        Map<PersonEntry, Double> ticketMap3 = new HashMap<>();
        ticketMap3.put(person1, 90.0);
        ticketMap3.put(person5, 0.0);
        ticketMap3.put(person2, 10.0);
        ticketMap3.put(person4, 25.0);

        // Create cinema ticket, which is evenly split
        ticketEntry1 = evenFact.getCinemaTicket(ticketMap1,person1);
        // Create restaurant ticket, which is unevenly split
        ticketEntry2 = unevenFact.getRestaurantTicket(ticketMap2,person1);
        // Create cinema ticket, which is unevenly split
        ticketEntry3 = unevenFact.getCinemaTicket(ticketMap3,person5);

        // Add Tickets to database
        ticketDatabase.addEntry(ticketEntry1);
        ticketDatabase.addEntry(ticketEntry2);
        ticketDatabase.addEntry(ticketEntry3);
    }


    private boolean hasEntryInDatabase(Database database, Object entry) {
        DatabaseIterator iterator = database.getIterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(entry)) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void testBillSplitting () {
        // Check if entries OK
        Assert.assertTrue(hasEntryInDatabase(ticketDatabase, ticketEntry1));
        Assert.assertTrue(hasEntryInDatabase(personDatabase, person1));
        Assert.assertTrue(hasEntryInDatabase(personDatabase, person5));

        GlobalBill bill = new GlobalBill(ticketDatabase);
        BillSplitter splitter = new BillSplitter(bill);
        splitter.calculateBill();

        Map<PersonEntry, Map<PersonEntry, Double>> totalPayment = splitter.getTotalPayment();

        // Test if payers are correct
        Set<PersonEntry> payers = totalPayment.keySet();
        Set<PersonEntry> realPayers = new HashSet<>();
        realPayers.add(person1); realPayers.add(person5);
        Assert.assertEquals(payers, realPayers);

        // Test if prices are correct
        Map<PersonEntry, Double> paymenPerson1 = totalPayment.get(person1);
        Assert.assertEquals("p2 -> p1",70.0, (double) paymenPerson1.get(person2),0.0);
        Assert.assertEquals("p3 -> p1",40.0, (double) paymenPerson1.get(person3), 0.0);
        Assert.assertNull("p4 -> p1", paymenPerson1.get(person4));
        Assert.assertNull("p5 -> p1",paymenPerson1.get(person5));

        Map<PersonEntry, Double> paymenPerson5 = totalPayment.get(person5);
        Assert.assertEquals("p1 -> p5",50.0, (double) paymenPerson5.get(person1), 0.0);
        Assert.assertEquals("p2 -> p5",10.0, (double) paymenPerson5.get(person2), 0.0);
        Assert.assertNull("p3 -> p5", paymenPerson5.get(person3));
        Assert.assertEquals("p4 -> p5",25.0, (double) paymenPerson5.get(person4), 0.0);
    }
}
