package view.panels;

import billSplitting.BillSplitter;
import billSplitting.GlobalBill;
import controller.PersonController;
import controller.TicketController;
import entries.PersonEntry;
import entries.TicketEntry;
import factories.EvenTicketFactory;
import factories.FactoryProvider;
import factories.TicketFactory;
import factories.UnevenTicketFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class GUI extends JPanel {
    // JButtons
    private JButton addPerson;
    private JButton done;
    private JButton calculate; // calculates bill and deletes ticket

    // JTextFields
    private JTextField nameP;

    // JLabels

    // JComboBox

    // JCheckBox


    private Double price;

    private PersonEntry p;
    private Boolean split;

    // Get controllers in private fields
    private PersonController controllerP;
    private TicketController controllerT;
    private ArrayList<String> nameList = new ArrayList<>();
    private boolean mapEmpty = true;
    private JButton payer1;
    private String payerName;
    private PersonEntry payer;
    private TicketEntry.eventsEnum event = TicketEntry.eventsEnum.RESTAURANT;
    Map<PersonEntry, Double> map = new HashMap<>(); //temp!!
    Map<PersonEntry, Map<PersonEntry, Double>> mapPayment = new HashMap<>();
    private JButton extraTicket;
    private ArrayList<TicketEntry.eventsEnum> eventList;
    private List<PersonEntry> existingPersons = new ArrayList<>();
    private JButton giveList;
    private ArrayList<TicketEntry> ticketList2;
    private JButton end;
    private JComboBox eventSelected;
    private JCheckBox splitMethod;
    private JComboBox payerSelected;

    public GUI(PersonController controllerP, TicketController controllerT) {
        this.controllerP = controllerP;
        this.controllerT = controllerT;

        initializeGUI();
    }

    private void initializeGUI() {
        setLayout(new GridLayout(2,3,5,20));
        JLabel label = new JLabel("Enter name: ",SwingConstants.RIGHT);
        this.nameP = new JTextField(15);
        this.addPerson = new JButton("add");
        this.add(label);
        this.add(nameP);
        this.add(addPerson);

        this.done = new JButton("Group complete");
        this.add(new JLabel());
        this.add(done);

        addPersonButtonActionListener();
        addGroupCompleteButtonActionListener();
    }

    public void addPersonButtonActionListener(){
        JLabel errorEmpty = new JLabel("Please enter name", SwingConstants.CENTER);
        JLabel errorDublicate = new JLabel("Person already exists", SwingConstants.CENTER);
        this.addPerson.addActionListener(listenerList -> {
            if (Objects.equals(nameP.getText().trim(),"")) {
                this.remove(errorDublicate);
                this.add(errorEmpty);
            } else if (nameList.contains(nameP.getText().trim())) {
                this.remove(errorEmpty);
                this.add(errorDublicate);
            } else {
                this.remove(errorEmpty);
                this.remove(errorDublicate);
                p = new PersonEntry(nameP.getText().trim());
                existingPersons.add(p);
                controllerP.addEntry(p);
                nameList.add(nameP.getText().trim());
                nameP.setText("");
            }

            revalidate();
            repaint();
        });
    }

    public void addGroupCompleteButtonActionListener(){
        this.done.addActionListener(listenerList -> {
            if (nameList.size() >= 2) { //maybe add error but how are we removing the previous one?
                clearFrame();
                setLayout(new GridLayout(0, 2, 10, 10));
                this.done = new JButton("Ticket is done");
                JLabel pricePayed = new JLabel("Money spend", SwingConstants.CENTER);
                this.add(new JLabel());
                this.add(pricePayed);
                for (PersonEntry person : existingPersons) {
                    JLabel name = new JLabel(person.getName(), SwingConstants.CENTER);
                    JTextField moneySpend = new JTextField(7);
                    this.add(name);
                    this.add(moneySpend);

                    this.done.addActionListener(new ActionListener() {
                        private TicketEntry t;

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (Objects.equals(moneySpend.getText(), "")) {
                                price = 0.0;
                            } else {
                                price = Double.parseDouble(moneySpend.getText().trim());
                            }
                            if (price != 0) {
                                map.put(person, price);
                                moneySpend.setText("");
                                mapEmpty = false;
                            }
                        }
                    });
                }
                this.add(new JLabel());
                this.add(done);
                done = new JButton("Next");
                this.add(new JLabel());
                this.add(new JLabel());
                this.add(new JLabel());
                this.add(done);

                addTicketCompleteButtonActionListener();
            }
        });
    }

    public void addTicketCompleteButtonActionListener(){
        this.done.addActionListener(listenerList -> {
            if(!mapEmpty) {
                clearFrame();
                JLabel chooseEvent = new JLabel("Select event: ",SwingConstants.RIGHT);
                eventSelected = new JComboBox(TicketEntry.eventsEnum.values());
                JLabel choosePayer = new JLabel("Select a payer: ",SwingConstants.RIGHT);
                payerSelected = new JComboBox();
                for (PersonEntry person : existingPersons) {
                    payerSelected.addItem(person.getName());
                }
                splitMethod = new JCheckBox("Split evenly");
                done = new JButton("Create ticket");
                this.add(chooseEvent);
                this.add(eventSelected);
                this.add(choosePayer);
                this.add(payerSelected);
                this.add(new JLabel());
                this.add(splitMethod);
                this.add(new JLabel());
                this.add(new JLabel());
                this.add(new JLabel());
                this.add(done);
                addSplitButtonActionListener();
                addPayerButtonActionListener();
                addEventButtonActionListener();
                addCreateTicketButtonActionListener();
            }
            else{
                JLabel l = new JLabel("There is no person in group!"); // doesn't work??
                this.add(l);
            }
        });
    }

    public void addSplitButtonActionListener() {
        this.split = false;     // default value
        this.splitMethod.addItemListener(listenerList -> {
            this.split = !(this.split);
        });
    }

    public void addPayerButtonActionListener() {
        this.payer = existingPersons.get(0);        // default value
        this.payerSelected.addActionListener(listenerList -> {
            this.payerName = (String) payerSelected.getSelectedItem();
            for (PersonEntry person : existingPersons) {
                if (Objects.equals(payerName, person.getName())) {
                    this.payer = person;
                }
            }
        });
    }

    public void addEventButtonActionListener() {
        this.eventSelected.addActionListener(listenerList -> {
            event = (TicketEntry.eventsEnum) eventSelected.getSelectedItem();
        });
    }

    public void addCreateTicketButtonActionListener() {
        this.done.addActionListener(listenerList -> {
            clearFrame();
            String eventString = event.toString();
            System.out.println(eventString);
            Map<PersonEntry, Double> currentMap = new HashMap<>(map);
            TicketEntry t;
            if (split) {
                TicketFactory evenFact = FactoryProvider.EvenTicket();
                switch (eventString) {
                    case "CINEMA":
                        t = evenFact.getCinemaTicket(currentMap, payer);
                        break;
                    case "RESTAURANT":
                        t = evenFact.getRestaurantTicket(currentMap, payer);
                        break;
                    case "CONCERT" :
                        t = evenFact.getConcertTicket(currentMap,payer);
                        break;
                    case "TAXI":
                        t = evenFact.getTaxiTicket(currentMap, payer);
                        break;
                    case "AIRPLANE":
                        t = evenFact.getAirplaneTicket(currentMap, payer);
                        break;
                    case "BUS":
                        t = evenFact.getBusTicket(currentMap, payer);
                        break;
                    default:
                        t = evenFact.getTicket(currentMap, payer);
                        break;
                }
            }
            else {
                TicketFactory unevenFact = FactoryProvider.UnevenTicket();
                switch (eventString) {
                    case "CINEMA":
                        t = unevenFact.getCinemaTicket(currentMap, payer);
                        break;
                    case "RESTAURANT":
                        t = unevenFact.getRestaurantTicket(currentMap, payer);
                        break;
                    case "CONCERT" :
                        t = unevenFact.getConcertTicket(currentMap,payer);
                        break;
                    case "TAXI":
                        t = unevenFact.getTaxiTicket(currentMap, payer);
                        break;
                    case "AIRPLANE":
                        t = unevenFact.getAirplaneTicket(currentMap, payer);
                        break;
                    case "BUS":
                        t = unevenFact.getBusTicket(currentMap, payer);
                        break;
                    default:
                        t = unevenFact.getTicket(currentMap, payer);
                        break;
                }
            }
            // Create a new map for each TicketEntry
            controllerT.addEntry(t);
            System.out.println("Entry we add to database:" + t.getMap() + " " + t.getPayer() + " " + t.isSplit());
            clearFrame();
            endScreen();

        });
    }

    public void endScreen(){
        setLayout(new GridLayout(3,1,5,30));
        calculate = new JButton("Calculate bill");
        extraTicket = new JButton("Add another ticket");
        giveList = new JButton("Print list of tickets");
        this.add(calculate);
        this.add(extraTicket);
        this.add(giveList);
        addCalculateButtonListener();
        addExtraTicketButtonListener();
        addGiveListButtonListener();
    }

    public void addCalculateButtonListener(){
        calculate.addActionListener(listenerList -> {
            GlobalBill bill = new GlobalBill(controllerT.getDatabase());
            //System.out.println("Regular bill: " + bill.getRegularBill());
            //System.out.println("Even bill: " + bill.getEqualBill());
            BillSplitter billSplitter = new BillSplitter(bill);
            billSplitter.payBill();
            mapPayment = billSplitter.getTotalPayment();
            //System.out.println("Total bill: " + mapPayment);
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

    public void addGiveListButtonListener() {
        giveList.addActionListener(listenerList -> {
            setLayout(new GridLayout(0,1,10,10));
            JLabel ticketListGUI = new JLabel("Working on it");
            clearFrame();
            //use iterator to iterate database and extract all tickets!
            ticketList2 = controllerT.getDatabase().getTicketList();
            System.out.println(ticketList2);
            System.out.println("printing full ticket list");
            for (TicketEntry entry : ticketList2) {
                StringBuilder labelText = new StringBuilder();
                labelText.append("Evenly split: " + entry.isSplit()
                        + "       Payer: " + entry.getPayer().getName()
                        + "       Event: " + entry.getEvent()
                        + "       Price per person: ");
                for (PersonEntry person : entry.getMap().keySet()) {
                    double price = entry.getMap().get(person);
                    if(person.getName() != "") {
                        labelText.append(" " + person.getName() + " - EUR " + price + "   ");
                    }
                }
                ticketListGUI = new JLabel(labelText.toString());
                add(ticketListGUI);
            }
            add(extraTicket); //listener still active?
            add(calculate);
        });
    }

    private void resetTicketVariables() {
        map.clear();
        // default values
        payer = existingPersons.get(0);
        split = false;
        event = TicketEntry.eventsEnum.RESTAURANT;

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
        setLayout(new GridLayout(0,1,10,10));
        Set<String> includedPersons = new HashSet<>();

        end = new JButton("Terminate application");

        for (Map.Entry<PersonEntry, Map<PersonEntry, Double>> entry : mapPayment.entrySet()) {
            PersonEntry receiver = entry.getKey();
            Map<PersonEntry, Double> amounts = entry.getValue();

            StringBuilder labelText = new StringBuilder(receiver.getName() + ": ");

            for (Map.Entry<PersonEntry, Double> amountEntry : amounts.entrySet()) {
                PersonEntry currentPerson = amountEntry.getKey();
                double currentAmount = amountEntry.getValue();

                if (!receiver.getName().equals(currentPerson.getName()) && !includedPersons.contains(currentPerson.getName())) {
                    String formattedAmount = String.format("%.2f", currentAmount);
                    labelText.append(currentPerson.getName()).append(" pays ").append(formattedAmount).append(", ");
                    includedPersons.add(currentPerson.getName());
                }
            }

            includedPersons.clear();
            if (labelText.length() > 2) {
                labelText.delete(labelText.length() - 2, labelText.length());
            }

            JLabel paymentLabel = new JLabel(labelText.toString());
            this.add(paymentLabel);
        }
        add(giveList);
        add(end);
        addEndButtonListener();
    }

    public void addEndButtonListener() {
        end.addActionListener(listenerList -> {
            //empty to stop app
            System.exit(0);
        });
    }

    public void clearFrame() {
        removeAll();
        revalidate();
        repaint();
    }

    public void clearValues() {
        nameP.setText("");
    }
}