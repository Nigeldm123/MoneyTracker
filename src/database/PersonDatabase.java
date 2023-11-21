package database;

import entries.PersonEntry;

import java.util.ArrayList;

public class PersonDatabase extends Database<PersonEntry> {
    private static PersonDatabase database;
    private ArrayList<PersonEntry> group;

    private PersonDatabase() {
        this.group = new ArrayList<>();
    }

    public static PersonDatabase getInstance() {
        if (database == null) {
            database = new PersonDatabase();
        }
        return database;
    }

    public void addEntry(PersonEntry person) {
        group.add(person);
    }

    public void removeEntry(PersonEntry person) {
        group.remove(person);
    }

    public PersonEntry getPerson(PersonEntry person) {
        return person;
    }
}
