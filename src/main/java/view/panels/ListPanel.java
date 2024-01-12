package view.panels;

import entries.TicketEntry;

import javax.swing.*;

public class ListPanel extends JPanel {

    private JList<TicketEntry> entryJList;
    private DefaultListModel<TicketEntry> ticket;

    public ListPanel()
    {
        ticket = new DefaultListModel<>();
        entryJList = new JList<>(ticket);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(entryJList);
    }

    public void addEntry(TicketEntry entry)
    {
        this.ticket.addElement(entry);
    }
}
