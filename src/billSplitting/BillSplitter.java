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
    private Map<PersonEntry,Map<PersonEntry,Double>> totalPayment;
    private Map<PersonEntry,Double> paymentPerPerson;
    public BillSplitter(GlobalBill bill) {
        this.bill = bill;
        totalPayment = new HashMap<>();
    }

    public void payBill() {
        for (Map<PersonEntry, Map<TicketEntry.eventsEnum, Pair<Double, Set<PersonEntry>>>> equalBill : bill.getEqualBill()) {
            for (PersonEntry receiver : equalBill.keySet()) {
                    paymentPerPerson = new HashMap<>();                                                 // for every receiver new bill
                    for (TicketEntry.eventsEnum event : equalBill.get(receiver).keySet()) {
                        Pair<Double, Set<PersonEntry>> ticket = equalBill.get(receiver).get(event);
                        Integer amount_of_people = ticket.getValue1().size();
                        Double pricePerPerson = ticket.getValue0() / amount_of_people;

                        for (PersonEntry payer : ticket.getValue1()) {
                            if (paymentPerPerson.containsKey(payer)) {
                                Double new_payment = paymentPerPerson.get(payer) + pricePerPerson;      // add new payment to already existing payment
                                paymentPerPerson.replace(payer, new_payment);
                            } else {
                                paymentPerPerson.put(payer, pricePerPerson);
                            }
                        }
                    }
                }
            }
        }


    }
}
