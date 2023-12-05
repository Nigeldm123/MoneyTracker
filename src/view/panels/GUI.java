package view.panels;

import controller.PersonController;
import controller.TicketController;
import entries.PersonEntry;
import entries.TicketEntry;
import factories.TicketFactory;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class GUI extends JPanel {
    private JButton addPerson;
    private JButton addTicket;
    private JButton calculate; // calculates bill and deletes ticket
    private PersonEntry p = new PersonEntry("blabla");
    private TicketEntry t;
    //private TicketEntry ticket;
    private TicketEntry.eventsEnum event = TicketEntry.eventsEnum.CINEMA;
    private Boolean split = true;

    // Get controllers in private field
    private PersonController controllerP;
    private TicketController controllerT;


    public GUI(PersonController controllerP, TicketController controllerT){
        this.controllerP = controllerP;
        this.controllerT = controllerT;
        JLabel label = new JLabel("Draft Buttons");
        this.addPerson = new JButton("add a Person");
        this.addTicket = new JButton("add a ticket");
        this.calculate = new JButton("clalculate bill");
        addPersonButtonActionListener();
        addTicketButtonActionListener();
        this.add(label);
        this.add(addPerson);
        this.add(addTicket);
    }

    public void addPersonButtonActionListener(){
        this.addPerson.addActionListener(listenerList -> {
            controllerP.addEntry(p);
        });
    }

    public void addTicketButtonActionListener(){
        this.addTicket.addActionListener(listenerList -> {
            Map<PersonEntry,Double> map = new HashMap<>(); //temp!!
            map.put(p,10.0); //temp!!!
            TicketFactory factory = new TicketFactory();
            this.t = factory.getTicket(map,event,split,p);
            controllerT.addEntry(t);
        });
    }
}
