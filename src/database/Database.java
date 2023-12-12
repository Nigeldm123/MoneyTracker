package database;

import iteratorPattern.Container;
import iteratorPattern.Iterator;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class Database<T> implements Container {
    protected Database() {}

    public abstract void addEntry(T entry);
    public abstract void removeEntry(T entry);
    public abstract void addObserver(PropertyChangeListener pcl);
    public abstract void removeObserver(PropertyChangeListener pcl);
}
