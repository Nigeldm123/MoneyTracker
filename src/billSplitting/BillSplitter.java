package billSplitting;

import entries.PersonEntry;
import entries.TicketEntry;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BillSplitter {
    private GlobalBill bill;
    private Map<PersonEntry,Map<PersonEntry,ArrayList<PersonEntry>>> totalPayment;
    private Map<PersonEntry,ArrayList<PersonEntry>> paymentPerPerson;
    public BillSplitter(GlobalBill bill) {
        this.bill = bill;
        totalPayment = new HashMap<>();
    }

    public void payBill() {

        for (Map<PersonEntry, Map<TicketEntry.eventsEnum, Pair<Double, Set<PersonEntry>>>> equalBill : bill.getEqualBill()) {
            for (PersonEntry payer : equalBill.keySet()) {
                for (TicketEntry.eventsEnum event : equalBill.get(payer).keySet()) {
                    Pair<Double, Set<PersonEntry>> ticket = equalBill.get(payer).get(event);
                    Integer amount_of_people = ticket.getValue1().size();
                    Double pricePerPerson = ticket.getValue0() / amount_of_people;
                    paymentPerPerson = new HashMap<>();
                    paymentPerPerson.put()
                }
            }
        }

    }
}
