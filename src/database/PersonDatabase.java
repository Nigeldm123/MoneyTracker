package database;

import entries.PersonEntry;
import iteratorPattern.PersonDatabaseIterator;
import iteratorPattern.Iterator;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class PersonDatabase extends Database<PersonEntry> {
    private static PersonDatabase database;
    private ArrayList<PersonEntry> group;
    private PropertyChangeSupport support;

    private PersonDatabase() {
        this.support = new PropertyChangeSupport(this);
        this.group = new ArrayList<>();
    }

    public static PersonDatabase getInstance() {
        if (database == null) {
            database = new PersonDatabase();
        }
        return database;
    }

    public void addEntry(PersonEntry person) {
        support.firePropertyChange(person.getName(), group, group.add(person));
    }

    public void removeEntry(PersonEntry person) {
        support.firePropertyChange(person.getName(), group, group.remove(person));
    }

    public void addObserver(PropertyChangeListener pcl){
        support.addPropertyChangeListener(pcl);
    };
    public void removeObserver(PropertyChangeListener pcl){
        support.removePropertyChangeListener(pcl);
    };

    public ArrayList<PersonEntry> getGroup() {
        return group;
    }

    public Iterator getIterator() {
        return new PersonDatabaseIterator();
    }

    public Integer size() {
        return group.size();
    }

}
