package observers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PersonDatabaseObserver implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt.getPropertyName() + " " + evt.getOldValue() + " " + evt.getNewValue());
    }
}
