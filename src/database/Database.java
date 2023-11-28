package database;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class Database<T> {
    protected Database() {
    }

    public abstract void addEntry(T entry);
    public abstract void removeEntry(T entry);
    public abstract void addObserver(PropertyChangeListener pcl);
    public abstract void removeObserver(PropertyChangeListener pcl);
}
