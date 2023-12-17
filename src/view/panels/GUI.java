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
    private Double price;
    private JButton calculate; // calculates bill and deletes ticket
    private PersonEntry p;
    private TicketEntry.eventsEnum eventsEnum; // {RESTAURANT, CINEMA, TAXI, CONCERT, AIRPLANE, BUS, OTHERS} values
    private Boolean split = false;

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
    private ArrayList<TicketEntry.eventsEnum> eventList;
    private List<PersonEntry> existingPersons = new ArrayList<>();
    private JButton giveList;
    private ArrayList<TicketEntry> ticketList2;
    private JButton end;
    private JComboBox event;
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
        JLabel l = new JLabel("Please enter name", SwingConstants.CENTER);
        this.addPerson.addActionListener(listenerList -> {
            if(!Objects.equals(nameP.getText().trim(), "")) {       // check for empty string
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
            setLayout(new GridLayout(0,2,10,20));
            this.done = new JButton("Ticket is done");
            for(PersonEntry person : existingPersons){
                JLabel name = new JLabel(person.getName(),SwingConstants.CENTER);
                JTextField moneySpend = new JTextField(7);
                this.add(name);
                this.add(moneySpend);

                this.done.addActionListener(new ActionListener() {
                    private TicketEntry t;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (price == null | Objects.equals(moneySpend.getText(), "")) {
                            price = 0.0;
                        } else {
                            price = Double.parseDouble(moneySpend.getText().trim());
                        }
                        map.put(person, price);
                        moneySpend.setText("");
                        mapEmpty = false;
                    }
                });
            }
            this.add(done);
            done = new JButton("Next");
            this.add(done);

            addTicketCompleteButtonActionListener();
        });
    }

    public void addTicketCompleteButtonActionListener(){
        this.done.addActionListener(listenerList -> {
            if(!mapEmpty) {
                clearFrame();
                JLabel ev = new JLabel("Select event: ",SwingConstants.RIGHT);
                event = new JComboBox(TicketEntry.eventsEnum.values());
                splitMethod = new JCheckBox("Split evenly");
                payerSelected = new JComboBox((ComboBoxModel) existingPersons);
                this.add(ev);
                this.add(event);
                this.add(splitMethod);
                this.add(payerSelected);
                addSplitButtonActionListener();
                //addPayerButtonActionListener();
                addEventButtonActionListener();
            }
            else{
                JLabel l = new JLabel("There is no person in group!");
                this.add(l);
            }
        });
    }

    public void addSplitButtonActionListener() {
        this.splitMethod.addItemListener(listenerList -> {
            this.split = !(this.split);
            System.out.println(this.split);
        });
    }


    /*public void addPayerButtonActionListener() {
        this.payer1.addActionListener(listenerList -> {
            clearFrame();
            String eventString = eventMethod.getText().trim();
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
    }*/

    public void addEventButtonActionListener() {
        this.event.addActionListener(listenerList -> {
            Map<PersonEntry, Double> currentMap = new HashMap<>(map);
            String selectedItem = event.getSelectedItem().toString();
            TicketEntry t;
            if (split) {
                TicketFactory evenFact = FactoryProvider.EvenTicket();
                switch (selectedItem) {
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
                switch (selectedItem) {
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
        eventsEnum = TicketEntry.eventsEnum.CINEMA; // Reset the event
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
    }
}