package billSplitting;

import entries.PersonEntry;
import entries.TicketEntry;
import org.javatuples.Pair;

import java.util.Map;

public class BillSplitter {
    private GlobalBill bill;
    public BillSplitter(GlobalBill bill) {
        this.bill = bill;
    }

    public Map<PersonEntry, Map<Double,PersonEntry>> payBill() {


        for (Map<PersonEntry, Map<TicketEntry.eventsEnum, Pair<Double, Integer>>> equalBill : bill.getEqualBill()) {
            for (PersonEntry payer : equalBill.keySet()) {
                for (Map<TicketEntry.eventsEnum, Pair<Double, Integer>> eventTicket : equalBill.values()) {
                    for (Pair<Double, Integer> ticket : eventTicket.values()) {
                        Double pricePerPerson = ticket.getValue0() / ticket.getValue1();
                    }
                }
            }
        }

    }
}
