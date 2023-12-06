package billSplitting;

import entries.PersonEntry;
import entries.TicketEntry;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.prefs.PreferenceChangeEvent;

public class BillSplitter {
    private GlobalBill bill;
    private Map<PersonEntry,Map<PersonEntry,Double>> totalPayment;
    private Map<PersonEntry,Double> paymentPerPerson;
    public BillSplitter(GlobalBill bill) {
        this.bill = bill;
        totalPayment = new HashMap<>();
    }

    public void payBill() {
        System.out.println("=== Debugging payBill===");
        System.out.println("Number of equal bills: " + bill.getEqualBill().size());
        System.out.println("Number of regular bills: " + bill.getRegularBill().size());


        for (Map<PersonEntry, Map<TicketEntry.eventsEnum, Pair<Double, Set<PersonEntry>>>> equalBill : bill.getEqualBill()) {
            for (PersonEntry receiver : equalBill.keySet()) {
                paymentPerPerson = new HashMap<>();                                                 // for every payer new bill
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
                totalPayment.put(receiver,paymentPerPerson);
            }
        }

        for (Map<PersonEntry, Map<TicketEntry.eventsEnum, Map<PersonEntry, Double>>> unequalBill : bill.getRegularBill()) {
            for (PersonEntry receiver : unequalBill.keySet()) {
                paymentPerPerson = new HashMap<>();                                                 // for every payer new bill
                for (TicketEntry.eventsEnum event : unequalBill.get(receiver).keySet()) {
                    Map<PersonEntry,Double> ticket = unequalBill.get(receiver).get(event);

                    for (PersonEntry payer : ticket.keySet()) {
                        if (paymentPerPerson.containsKey(payer)) {
                            Double new_payment = paymentPerPerson.get(payer) + ticket.get(payer);      // add new payment to already existing payment
                            paymentPerPerson.replace(payer, new_payment);
                        } else {
                            paymentPerPerson.put(payer, ticket.get(payer));
                        }
                    }
                }

                /*if (totalPayment.containsKey(receiver)) {
                    Map<PersonEntry,Double> oldPaymentPerPerson = totalPayment.get(receiver);
                    oldPaymentPerPerson.forEach(
                            (key, value) -> paymentPerPerson.merge(key, value, Double::sum));       // merge two existing maps (old + new)
                    totalPayment.replace(receiver,paymentPerPerson);
                } else {*/
                    totalPayment.put(receiver,paymentPerPerson);
                //}
            }
        }
    }

    public Map<PersonEntry, Map<PersonEntry, Double>> getTotalPayment() {
        System.out.println("Final totalPayment map: " + totalPayment);
        System.out.println("===========================");
        return totalPayment;
    }
}
