package view.panels;

import billSplitting.BillSplitter;
import billSplitting.GlobalBill;
import controller.PersonController;
import controller.TicketController;
import entries.PersonEntry;
import entries.TicketEntry;
import factories.TicketFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class GUI extends JPanel {
    private JButton addPerson;
    private JButton done;
    private JTextField nameP;
    private JButton addTicket;
    private JTextField personSpend;
    private JTextField priceSpendPP;
    private JTextField event1;
    private JButton split1;
    private Double price;
    private JButton calculate; // calculates bill and deletes ticket
    private PersonEntry p;
    //public TicketEntry t;
    private TicketEntry.eventsEnum event = TicketEntry.eventsEnum.CINEMA; //?
    private Boolean split = true;

    // Get controllers in private fields
    private PersonController controllerP;
    private TicketController controllerT;
    private int state = 0;
    private ArrayList<String> nameList = new ArrayList<>();
    private boolean mapEmpty = true;
    private JButton payer1;
    private PersonEntry payer;
    Map<PersonEntry, Double> map = new HashMap<>(); //temp!!
    Map<PersonEntry, Map<PersonEntry, Double>> mapPayment = new HashMap<>();
    private JButton extraTicket;
    private JLabel paymentLabel;
    private StringBuilder labelText;
    private double amount;
    private PersonEntry otherPerson;


    public GUI(PersonController controllerP, TicketController controllerT){
        this.controllerP = controllerP;
        this.controllerT = controllerT;

        setLayout(new GridLayout(4, 2, 10, 10));

        //JLabel label = new JLabel("Draft Buttons");
        /*this.addPerson = new JButton("add a Person");
        this.nameP = new JTextField(16);
        this.done = new JButton("Group complete");

        this.add(addPerson);
        this.add(nameP);
        this.add(done);

        addPersonButtonActionListener();
        addGroupCompleteButtonActionListener();*/

        initializeGUI();
    }

    private void initializeGUI() {
        this.addPerson = new JButton("add a Person");
        this.nameP = new JTextField(16);
        this.done = new JButton("Group complete");

        this.add(addPerson);
        this.add(nameP);
        this.add(done);

        addPersonButtonActionListener();
        addGroupCompleteButtonActionListener();
    }

    public void addPersonButtonActionListener(){
        JLabel l = new JLabel("Please enter name");
        this.addPerson.addActionListener(listenerList -> {
            if(!Objects.equals(nameP.getText().trim(), "")) {
                this.remove(l);
                p = new PersonEntry(nameP.getText().trim());
                controllerP.addEntry(p);
                nameList.add(nameP.getText().trim());
                nameP.setText("");
            }
            else{
                this.add(l);
            }
            revalidate();
            repaint();
        });
    }

    public void addGroupCompleteButtonActionListener(){
        this.done.addActionListener(listenerList -> {
            clearFrame();
            //setLayout(new GridLayout(nameList.size(), 2, 10, 10));
            for(String person : nameList){
                 JButton button = new JButton(person);
                 JTextField moneySpend = new JTextField(3);
                 this.add(button);
                 this.add(moneySpend);
                 button.addActionListener(new ActionListener() {
                     private TicketEntry t;
                     @Override
                     public void actionPerformed(ActionEvent e) {
                         p = new PersonEntry(person);
                         price = Double.parseDouble(moneySpend.getText().trim());
                         map.put(p, price);
                         moneySpend.setText("");
                         mapEmpty = false;
                     }
                 });
            }
            this.done = new JButton("Ticket is done");
            this.add(done);
            addTicketCompleteButtonActionListener();
        });
    }

    public void addTicketCompleteButtonActionListener(){
        JLabel l = new JLabel("no one added!!!");

        this.done.addActionListener(listenerList -> {
            clearFrame();
            if(!mapEmpty){
                JLabel ev = new JLabel("event");
                event1 = new JTextField(16);
                split1 = new JButton("split method");
                payer1 = new JButton("choose payer");
                this.add(ev);
                this.add(event1);
                this.add(split1);
                this.add(payer1);
                addSplitButtonActionListener();
                addPayerButtonActionListener();
            }
            /*else{
                this.add(l);
            }*/
        });
    }

    public void addSplitButtonActionListener() {
        this.split1.addActionListener(listenerList -> {
            // Remove previous components (if any)
            Component[] components = this.getComponents();
            for (Component component : components) {
                if (component instanceof JLabel) {
                    this.remove(component);
                }
            }

            // Add the new label based on the updated split value
            JLabel splLabel = new JLabel(split ? "false" : "true");
            this.add(splLabel);

            // Toggle the split value
            split = !split;

            // Revalidate and repaint the JFrame
            revalidate();
            repaint();
        });
    }

    public void addPayerButtonActionListener() {
        this.payer1.addActionListener(listenerList -> {
            clearFrame();
            for (String person : nameList) {
                JButton button = new JButton(person);
                this.add(button);
                button.addActionListener(new ActionListener() {
                    private TicketEntry t;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        p = new PersonEntry(person);
                        payer = p;
                        TicketFactory factory = new TicketFactory();
                        this.t = factory.getTicket(map, event, split, payer);
                        controllerT.addEntry(t);
                        clearFrame();
                        endScreen(); //?
                    }
                });
            }
        });
    }

    public void endScreen(){
        calculate = new JButton("calculate bill");
        extraTicket = new JButton("add another ticket");
        this.add(calculate);
        this.add(extraTicket);
        addCalculateButtonListener();
        addExtraTicketButtonListener();
    }

    public void addCalculateButtonListener(){
        calculate.addActionListener(listenerList -> {
            GlobalBill bill = new GlobalBill(controllerT.getDatabase());
            BillSplitter billSplitter = new BillSplitter(bill);
            billSplitter.payBill();
            mapPayment = billSplitter.getTotalPayment();
            //System.out.println(mapPayment.values());
            clearFrame();
            finalBill(mapPayment);
        });
    }

    public void addExtraTicketButtonListener() {
        extraTicket.addActionListener(listenerList -> {
            clearFrame();
            resetTicketVariables();
        });
    }

    private void resetTicketVariables() {
        // Reset ticket-related variables and components
        //map.clear();
        //mapPayment.clear();
        payer = null;
        event = TicketEntry.eventsEnum.CINEMA; // Reset the event
        split = true; // Reset the split value

        // Clear the list of names
        nameList.clear();

        extraTicket.setEnabled(true); // Enable the extraTicket button

        // Reset ticket-related variables and components
        clearValues();

        // Reinitialize components
        removeAll();
        revalidate();
        repaint();

        initializeGUI(); // Restart the process
    }

    public void finalBill(Map<PersonEntry, Map<PersonEntry, Double>> mapPayment) {

        Set<String> includedPersons = new HashSet<>(); // Use a set to keep track of included persons

        // Add JLabels for each person and their corresponding amount
        for (Map.Entry<PersonEntry, Map<PersonEntry, Double>> entry : mapPayment.entrySet()) {
            PersonEntry person = entry.getKey();
            Map<PersonEntry, Double> amounts = entry.getValue();

            String payerName = person.getName();
            //System.out.println(payerName);

            StringBuilder labelText = new StringBuilder(payerName + ": ");

            // Exclude the payer from the label
            for (Map.Entry<PersonEntry, Double> amountEntry : amounts.entrySet()) {
                otherPerson = amountEntry.getKey();
                amount = amountEntry.getValue();
                //System.out.println(otherPerson.getName());
                //System.out.println(amount);

                if (!payerName.equals(otherPerson.getName()) && !includedPersons.contains(otherPerson.getName())) {
                    // Format the double value with two decimal places
                    String formattedAmount = String.format("%.2f", amount);
                    labelText.append(otherPerson.getName()).append(" pays ").append(formattedAmount).append(", ");

                    includedPersons.add(otherPerson.getName());
                }
            }

            // Remove the trailing ", " from the label text
            if (labelText.length() > 2) {
                labelText.delete(labelText.length() - 2, labelText.length());
            }

            JLabel paymentLabel = new JLabel(labelText.toString());
            this.add(paymentLabel);
        }
        // Ensure names are not overwritten
        clearValues();
    }




    public void clearFrame() {
        removeAll();
        revalidate();
        repaint();
    }

    public void clearValues() {
        nameP.setText("");
        event1.setText("");
        //map.clear(); //???
        payer = null;
        split = true;
        nameList.clear();  // Clear the list of names
    }
}