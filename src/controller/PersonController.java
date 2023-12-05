package controller;

import database.PersonDatabase;
import database.TicketDatabase;
import entries.PersonEntry;
import entries.TicketEntry;

public class PersonController implements ControllerP {
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
