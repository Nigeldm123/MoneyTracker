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
        TicketEntry ticket = new TicketEntry(map,event1,split1,person2);
        print(ticket);
        t.addEntry(ticket);
        /*t.removeEntry(ticket);
        print(ticket,person);*/
    }

    public void print(TicketEntry t)
    {
        System.out.println("Equal (true) or not equal (false) split: " + t.isSplit()
                + "\nPayer: " + t.getPayer().getName()
                + "\nEvent: " + t.getEvent()
                + "\nPrice per person: ");
                for (PersonEntry person : t.getMap().keySet()) {
                    double price = t.getMap().get(person);
                    System.out.println(person.getName()+" - EUR "+price);
                }
                System.out.println();
    }
}