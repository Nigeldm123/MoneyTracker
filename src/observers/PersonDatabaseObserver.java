package observers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PersonDatabaseObserver implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Person added: "+evt.getPropertyName());
    }
}
