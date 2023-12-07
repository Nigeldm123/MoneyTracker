package view;

import controller.PersonController;
import controller.TicketController;
import database.PersonDatabase;
import database.TicketDatabase;
import view.panels.ListPanel;
import view.panels.GUI;

import javax.swing.*;
import java.awt.*;

public class ViewFrame extends JFrame {
    PersonController personController;
    TicketController ticketController;
    ListPanel panel;
    GUI buttons;

    public ViewFrame(){
        super("GUI");
        personController = new PersonController(PersonDatabase.getInstance());
        ticketController = new TicketController(TicketDatabase.getInstance());
    }
    public void initialize() {
        this.setSize(1000, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        buttons = new GUI(personController,ticketController);
        panel = new ListPanel();

        this.add(panel);
        this.add(buttons);
        this.setVisible(true);
    }
}
