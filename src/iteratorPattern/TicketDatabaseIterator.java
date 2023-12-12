package iteratorPattern;

import database.TicketDatabase;

public class TicketDatabaseIterator implements Iterator {
    int index;
    TicketDatabase t = TicketDatabase.getInstance();
    @Override
    public boolean hasNext() {
        if (index < t.size()) {
            return true;
        }
        return false;
    }

    @Override
    public Object next() {
        if (this.hasNext()) {
            return t.getTicketList().get(index++);
        }
        return null;
    }

}
