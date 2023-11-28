package database;

public abstract class Database<T> {
    protected Database() {
    }

    public abstract void addEntry(T entry);
    //public abstract void removeEntry(int entryID);
}
