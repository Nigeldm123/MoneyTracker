package controller;

import database.TicketDatabase;
import entries.TicketEntry;

public class TicketController implements ControllerT{

    private TicketDatabase db;
    public TicketController(TicketDatabase db){
        this.db = db;
    }

    @Override
    public void addEntry(TicketEntry T) {
        db.addEntry(T);
    }

    @Override
    public void removeEntry(TicketEntry T) {
        db.removeEntry(T);
    }

    public TicketDatabase getDatabase(){
        return db;
    }
}

