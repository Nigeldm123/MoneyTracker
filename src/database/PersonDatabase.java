package database;

import entries.PersonEntry;

public class PersonDatabase extends Database {
    private static PersonDatabase database;
    private ArrayList<PersonEntry> group;
    private String name;
    private PersonDatabase() {
    }
    public static PersonDatabase getInstance() {
        if (database == null) {
            database = new PersonDatabase();
        }
        return database;
    }
    public void addEntry() {

    }

    public void removeEntry() {

    }

    public String getPerson() {
        return name;
    }
}
