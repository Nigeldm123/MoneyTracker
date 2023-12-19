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

}
