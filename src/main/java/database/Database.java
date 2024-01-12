package database;

import iteratorPattern.DatabaseIterator;

import java.beans.PropertyChangeListener;

public abstract class Database<T> {
    protected Database() {}

    public abstract void addEntry(T entry);
    public abstract void removeEntry(T entry);
    public abstract DatabaseIterator getIterator();
    public abstract void addObserver(PropertyChangeListener pcl);
    public abstract void removeObserver(PropertyChangeListener pcl);
}
