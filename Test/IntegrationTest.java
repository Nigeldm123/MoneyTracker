import entries.PersonEntry;
import entries.TicketEntry;
import database.PersonDatabase;
import database.TicketDatabase;
import iteratorPattern.Container;
import iteratorPattern.DatabaseIterator;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class IntegrationTest {
    @Test
    public void testAddTicketEntryToDatabases() {
        // Arrange
        PersonDatabase personDatabase = PersonDatabase.getInstance();
        TicketDatabase ticketDatabase = TicketDatabase.getInstance();

        // Create some PersonEntry instances
        PersonEntry person1 = new PersonEntry("John");
        PersonEntry person2 = new PersonEntry("Alice");

        // Add PersonEntry instances to PersonDatabase
        personDatabase.addEntry(person1);
        personDatabase.addEntry(person2);

        // Create a TicketEntry with the PersonEntry instances
        Map<PersonEntry, Double> ticketMap = new HashMap<>();
        ticketMap.put(person1, 20.0);
        ticketMap.put(person2, 30.0);

        TicketEntry ticketEntry = new TicketEntry(ticketMap, TicketEntry.eventsEnum.CINEMA, true, person1);

        // Act
        ticketDatabase.addEntry(ticketEntry);

        // Assert
        Assert.assertTrue(hasEntryInDatabase(ticketDatabase, ticketEntry));
        Assert.assertTrue(hasEntryInDatabase(personDatabase, person1));
        Assert.assertTrue(hasEntryInDatabase(personDatabase, person2));
    }

    private boolean hasEntryInDatabase(Container database, Object entry) {
        DatabaseIterator iterator = database.getIterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(entry)) {
                return true;
            }
        }
        return false;
    }
}
