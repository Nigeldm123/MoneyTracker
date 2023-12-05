import database.Database;
import database.PersonDatabase;
import database.TicketDatabase;
import entries.PersonEntry;
import entries.TicketEntry;
import factories.TicketFactory;
import observers.PersonDatabaseObserver;
import observers.TicketDatabaseObserver;
import view.ViewFrame;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    public Main() {}

    public void run() {
        PersonDatabase p = PersonDatabase.getInstance();
        TicketDatabase t = TicketDatabase.getInstance();
        PersonDatabaseObserver pOberserver = new PersonDatabaseObserver();
        p.addObserver(pOberserver);
        TicketDatabaseObserver tObserver = new TicketDatabaseObserver();
        t.addObserver(tObserver);

        ViewFrame view = new ViewFrame();
        view.initialize();

        Map<PersonEntry, Double> map = new HashMap<>();
        TicketEntry.eventsEnum event1 = TicketEntry.eventsEnum.CINEMA;
        boolean split1 = true;
        PersonEntry person = new PersonEntry("Femmie");
        PersonEntry person2 = new PersonEntry("Nigel");
        PersonEntry person3 = new PersonEntry("Jeff");
        p.addEntry(person);
        p.addEntry(person2);
        p.addEntry(person3);
        map.put(person,10.0);
        map.put(person2,30.0);
        map.put(person3,20.0);

        TicketFactory factory = new TicketFactory();
        TicketEntry ticket1 = factory.getTicket(map,event1,split1,person2);
        t.addEntry(ticket1);
    }

}