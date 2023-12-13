import billSplitting.BillSplitter;
import billSplitting.GlobalBill;
import controller.PersonController;
import controller.TicketController;
import database.PersonDatabase;
import database.TicketDatabase;
import entries.PersonEntry;
import entries.TicketEntry;
import factories.EvenTicketFactory;
import factories.TicketFactory;
import factories.UnevenTicketFactory;
import observers.PersonDatabaseObserver;
import observers.TicketDatabaseObserver;
import view.ViewFrame;

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

        TicketFactory evenFact = new EvenTicketFactory();
        TicketFactory unevenFact = new UnevenTicketFactory();

        PersonEntry person = new PersonEntry("Femmie");
        PersonEntry person2 = new PersonEntry("Nigel");
        PersonEntry person3 = new PersonEntry("Jeff");
        p.addEntry(person);
        p.addEntry(person2);
        p.addEntry(person3);

        Map<PersonEntry, Double> map = new HashMap<>();
        map.put(person,10.0);
        map.put(person2,30.0);
        map.put(person3,20.0);
        TicketEntry ticket1 = unevenFact.getCinemaTicket(map,person2);
        t.addEntry(ticket1);

        Map<PersonEntry, Double> map2 = new HashMap<>();
        map2.put(person,10.0);
        map2.put(person2,20.0);
        map2.put(person3,30.0);
        TicketEntry ticket2 = unevenFact.getTicket(map2,person2);
        t.addEntry(ticket2);

        GlobalBill bill = new GlobalBill(t);
        BillSplitter splitter = new BillSplitter(bill);
        splitter.payBill();
        for (PersonEntry receiver : splitter.getTotalPayment().keySet()) {
            System.out.println("\nReceiver: "+receiver.getName());
            System.out.println("Payers: ");
            for (PersonEntry payer : splitter.getTotalPayment().get(receiver).keySet()) {
                if (payer != receiver) {
                    System.out.println("          " + payer.getName() + "          " + splitter.getTotalPayment().get(receiver).get(payer));
                }
            }
        }

    }

}