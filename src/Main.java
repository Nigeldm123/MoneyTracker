import database.Database;
import database.PersonDatabase;
import database.TicketDatabase;
import entries.PersonEntry;
import entries.TicketEntry;

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
        Map<PersonEntry, Double> map = new HashMap<>();
        TicketEntry.events event1 = TicketEntry.events.CINEMA;
        boolean split1 = true;
        String payer1 = "Nigel";
        PersonEntry person = new PersonEntry("Femmie");
        PersonEntry person2 = new PersonEntry("Nigel");
        PersonEntry person3 = new PersonEntry("Jeff");
        p.addEntry(person);
        p.addEntry(person2);
        p.addEntry(person3);
        map.put(person,10.0);
        map.put(person2,30.0);
        map.put(person3,20.0);
        TicketEntry ticket = new TicketEntry(map,event1,split1,payer1);
        print(ticket,person);
        t.addEntry(ticket);
    }

    public void print(TicketEntry t, PersonEntry p)
    {
        System.out.println(p.getName() +
                " " + t.getEvent() + " " + t.getPayer() + " " + t.isSplit() + " " + t.getPrice() + " " + t.getPerson() + " " + " " + t.getMap().values()
        );
    }
}