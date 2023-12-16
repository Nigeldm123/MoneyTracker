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
    private TicketEntry.eventsEnum event; // {RESTAURANT, CINEMA, TAXI, CONCERT, AIRPLANE, BUS, OTHERS} values
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
    //Map<PaymentKey, Map<PersonEntry, Double>> mapPayment = new HashMap<>();
    private JButton extraTicket;
    private JLabel paymentLabel;
    private StringBuilder labelText;
    private double amount;
    private PersonEntry otherPerson;
    private ArrayList<TicketEntry.eventsEnum> eventList;
    private List<PersonEntry> existingPersons = new ArrayList<>();
    private JButton giveList;
    private ArrayList<TicketEntry> ticketList2;
    private JButton end;

    public GUI(PersonController controllerP, TicketController controllerT){
        this.controllerP = controllerP;
        this.controllerT = controllerT;

        setLayout(new GridLayout(4, 2, 10, 10));
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
                existingPersons.add(p);
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
            for(PersonEntry person : existingPersons){
                JButton button = new JButton(person.getName());
                JTextField moneySpend = new JTextField(3);
                this.add(button);
                this.add(moneySpend);
                button.addActionListener(new ActionListener() {
                    private TicketEntry t;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        price = Double.parseDouble(moneySpend.getText().trim());
                        map.put(person, price);
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

        this.done.addActionListener(listenerList -> {
            if(!mapEmpty){
                clearFrame();
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
            else{
                JLabel l = new JLabel("no one added!!!");
                this.add(l);
            }
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
            JLabel splLabel = new JLabel(split ? "Split Regular" : "Split Even");
            this.add(splLabel);

            // Toggle the split value
            split = !split;

            // Revalidate and repaint the JFrame
            revalidate();
            repaint();
        });
    }


    private TicketEntry.eventsEnum getEventEnum(String eventString) {
        // Convert the event string to uppercase for case-insensitive comparison
        eventString = eventString.toUpperCase();

        // Determine the corresponding eventsEnum value
        String finalEventString = eventString;
        return Arrays.stream(TicketEntry.eventsEnum.values())
                .filter(value -> value.name().equals(finalEventString))
                .findFirst()
                .orElse(TicketEntry.eventsEnum.OTHERS);
    }


    public void addPayerButtonActionListener() {
        this.payer1.addActionListener(listenerList -> {
            clearFrame();
            String eventString = event1.getText().trim();
            System.out.println(eventString);
            //TicketEntry.eventsEnum selectedEvent = getEventEnum(eventString);

            for (String person : nameList) {
                JButton button = new JButton(person);
                this.add(button);
                button.addActionListener(new ActionListener() {
                    private TicketEntry t;
                    private PersonEntry selectedPerson;

                    public ActionListener init(PersonEntry personEntry) {
                        this.selectedPerson = personEntry;
                        return this;
                    }

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        payer = selectedPerson;
                        Map<PersonEntry, Double> currentMap = new HashMap<>(map);
                        if(split) {
                            TicketFactory evenFact = FactoryProvider.EvenTicket();
                            switch (eventString) {
                                case "cinema":
                                    this.t = evenFact.getCinemaTicket(currentMap, payer);
                                    break;
                                case "restaurant":
                                    this.t = evenFact.getRestaurantTicket(currentMap, payer);
                                    break;
                                case "taxi":
                                    this.t = evenFact.getTaxiTicket(currentMap, payer);
                                    break;
                                case "airplane":
                                    this.t = evenFact.getAirplaneTicket(currentMap, payer);
                                    break;
                                case "bus":
                                    this.t = evenFact.getBusTicket(currentMap, payer);
                                    break;
                                default:
                                    this.t = evenFact.getTicket(currentMap, payer);
                                    break;
                            }
                        }
                        else {
                            TicketFactory unevenFact = FactoryProvider.UnevenTicket();
                            switch (eventString) {
                                case "cinema":
                                    this.t = unevenFact.getCinemaTicket(currentMap, payer);
                                    break;
                                case "restaurant":
                                    this.t = unevenFact.getRestaurantTicket(currentMap, payer);
                                    break;
                                case "taxi":
                                    this.t = unevenFact.getTaxiTicket(currentMap, payer);
                                    break;
                                case "airplane":
                                    this.t = unevenFact.getAirplaneTicket(currentMap, payer);
                                    break;
                                case "bus":
                                    this.t = unevenFact.getBusTicket(currentMap, payer);
                                    break;
                                default:
                                    this.t = unevenFact.getTicket(currentMap, payer);
                                    break;
                            }
                        }
                        // Create a new map for each TicketEntry
                        controllerT.addEntry(t);
                        System.out.println("Entry we add to dataBase:" + t.getMap() + " " + t.getPayer() + " " + t.isSplit());
                        clearFrame();
                        endScreen();
                    }
                }.init(controllerP.getEntryByName(person)));
            }
        });
    }

    public void endScreen(){
        calculate = new JButton("calculate bill");
        extraTicket = new JButton("add another ticket");
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
            System.out.println("bill: " + bill.getRegularBill());
            BillSplitter billSplitter = new BillSplitter(bill);
            billSplitter.payBill();
            mapPayment = billSplitter.getTotalPayment();
            //eventList = billSplitter.getEventList();
            System.out.println("event: " + eventList);
            System.out.println("hello GUI here: " + mapPayment.values());
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
            JLabel ticketListGUI = new JLabel("Working on it");
            clearFrame();
            //use iterator to iterate database and extract all tickets!
            ticketList2 = controllerT.getDatabase().getTicketList();
            System.out.println(ticketList2);
            System.out.println("printing full ticket list");
            for (TicketEntry entry : ticketList2) {
                StringBuilder labelText = new StringBuilder();
                labelText.append("Equal (true) or not equal (false) split: " + entry.isSplit()
                        + " Payer: " + entry.getPayer().getName()
                        + " Event: " + entry.getEvent()
                        + " Price per person: ");
                for (PersonEntry person : entry.getMap().keySet()) {
                    double price = entry.getMap().get(person);
                    //System.out.println(person.getName() + " - EUR " + price);
                    if(person.getName() != "") {
                        labelText.append(" " + person.getName() + " - EUR " + price);
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
        // Reset ticket-related variables and components
        map.clear();
        payer = null;
        event = TicketEntry.eventsEnum.CINEMA; // Reset the event
        split = true; // Reset the split value

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
        //clearValues(); //Not needed?
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
        event1.setText("");
    }
}