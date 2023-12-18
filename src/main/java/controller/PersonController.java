package controller;

import database.PersonDatabase;
import entries.PersonEntry;

public class PersonController implements ControllerInterface<PersonEntry> {
    private PersonDatabase db;
    public PersonController(PersonDatabase db){
        this.db = db;
    }

    @Override
    public void addEntry(PersonEntry P) {
        db.addEntry(P);
    }

    @Override
    public void removeEntry(PersonEntry P) {
        db.removeEntry(P);
    }

    public PersonEntry getEntryByName(String name) {
        for (PersonEntry personEntry : db.getGroup()) {
            if (personEntry.getName().equals(name)) {
                return personEntry;
            }
        }
        return null; // Return null if no matching entry is found
    }
}
