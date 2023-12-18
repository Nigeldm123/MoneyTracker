package billSplitting;

import entries.PersonEntry;
import entries.TicketEntry;
import org.javatuples.Pair;

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

                if (totalPayment.containsKey(receiver)) {
                    Map<PersonEntry,Double> oldPaymentPerPerson = totalPayment.get(receiver);
                    oldPaymentPerPerson.forEach(
                            (key, value) -> paymentPerPerson.merge(key, value, Double::sum));       // merge two existing maps (old + new)
                    totalPayment.replace(receiver,paymentPerPerson);
                } else {
                    totalPayment.put(receiver,paymentPerPerson);
                }
            }
        }
    }

    public Map<PersonEntry, Map<PersonEntry, Double>> getTotalPayment() {
        //check if not just one ticket in database!
        if (totalPayment.size() <= 1) {
            //System.out.println("Not enough tickets in the database for reduction.");
            return totalPayment; // Return an empty map or handle accordingly
        }

        Map<PersonEntry, Map<PersonEntry, Double>> reducedPayments = new HashMap<>();

        for (Map.Entry<PersonEntry, Map<PersonEntry, Double>> entry : totalPayment.entrySet()) {
            PersonEntry payer = entry.getKey();
            Map<PersonEntry, Double> amounts = entry.getValue();

            for (Map.Entry<PersonEntry, Double> amountEntry : amounts.entrySet()) {
                PersonEntry payee = amountEntry.getKey();
                double currentAmount = amountEntry.getValue();

                // Check if there is a reciprocal payment
                if (totalPayment.containsKey(payee) && totalPayment.get(payee).containsKey(payer)) {
                    double reciprocalAmount = totalPayment.get(payee).get(payer);

                    // Calculate the net amount
                    double netAmount = currentAmount - reciprocalAmount;

                    // Only include positive net amounts
                    if (netAmount > 0) {
                        reducedPayments
                                .computeIfAbsent(payer, k -> new HashMap<>())
                                .put(payee, netAmount);
                    }
                }
                else{
                    reducedPayments
                            .computeIfAbsent(payer, k -> new HashMap<>())
                            .put(payee, currentAmount);;
                }
            }
        }
        //System.out.println("reduced payments: "+reducedPayments);
        return reducedPayments;
    }
}
