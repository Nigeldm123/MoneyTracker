import database.PersonDatabase;
import database.TicketDatabase;
import observers.PersonDatabaseObserver;
import observers.TicketDatabaseObserver;
import view.ViewFrame;

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

    }

}