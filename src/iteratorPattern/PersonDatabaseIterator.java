package iteratorPattern;

import database.PersonDatabase;

public class PersonDatabaseIterator implements DatabaseIterator {
    int index;
    PersonDatabase p = PersonDatabase.getInstance();
    @Override
    public boolean hasNext() {
        if (index < p.size()) {
            return true;
        }
        return false;
    }

    @Override
    public Object next() {
        if (this.hasNext()) {
            return p.getGroup().get(index++);
        }
        return null;
    }

}
