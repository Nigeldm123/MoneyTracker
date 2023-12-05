package observers;

import entries.PersonEntry;
import entries.TicketEntry;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TicketDatabaseObserver implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        TicketEntry t = (TicketEntry) evt.getOldValue();
        System.out.println();
        System.out.println("Equal (true) or not equal (false) split: " + t.isSplit()
                + "\nPayer: " + t.getPayer().getName()
                + "\nEvent: " + t.getEvent()
                + "\nPrice per person: ");
        for (PersonEntry person : t.getMap().keySet()) {
            double price = t.getMap().get(person);
            System.out.println(person.getName()+" - EUR "+price);
        }
        System.out.println();
    }

}
