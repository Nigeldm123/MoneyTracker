package database;
import entries.Entry;

public abstract class Database {

    protected Database() {}

    public abstract void addEntry(Entry entry);
    public abstract void removeEntry(Entry entry);
}
