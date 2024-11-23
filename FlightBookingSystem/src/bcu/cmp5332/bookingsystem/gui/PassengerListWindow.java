package bcu.cmp5332.bookingsystem.gui;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import bcu.cmp5332.bookingsystem.model.Customer;

/**
 * A JFrame for displaying the list of passengers for a flight.
 */
public class PassengerListWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new PassengerListWindow.
     *
     * @param passengers   The list of passengers.
     * @param flightNumber The flight number.
     */
    public PassengerListWindow(List<Customer> passengers, String flightNumber) {
        setTitle("Passenger List for Flight " + flightNumber);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(173, 216, 230));

        String[] columnNames = {"ID", "Name", "Phone", "Email"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Filter out cancelled bookings from the list of passengers
        List<Customer> activePassengers = passengers.stream()
                .filter(customer -> !customer.isCancelled())
                .collect(Collectors.toList());

        for (Customer passenger : activePassengers) {
            Object[] rowData = {passenger.getId(), passenger.getName(), passenger.getPhone(), passenger.getEmail()};
            model.addRow(rowData);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
