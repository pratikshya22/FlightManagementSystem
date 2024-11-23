package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.commands.CancelBooking;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The CancelBookingWindow class represents a graphical user interface window for canceling a booking
 * in the Flight Booking System application.
 * 
 * It allows the user to input a customer ID and a flight ID to cancel the booking for that specific
 * customer and flight. Upon cancellation, the booking is removed from the system.
 * 
 * This class extends the JFrame class and implements the ActionListener interface to handle button clicks.
 * 

 */
public class CancelBookingWindow extends JFrame implements ActionListener {

    /** The serialVersionUID for serializable classes. */
    private static final long serialVersionUID = 1L;

    /** The field for entering the customer ID. */
    private JTextField customerIdField;

    /** The field for entering the flight ID. */
    private JTextField flightIdField;

    /** The button for canceling the booking. */
    private JButton cancelButton;

    /** The FlightBookingSystem instance. */
    private FlightBookingSystem fbs;

    /**
     * Constructs a new CancelBookingWindow.
     * 
     * @param mw the MainWindow instance
     */
    public CancelBookingWindow(MainWindow mw) {
        this.fbs = mw.getFlightBookingSystem();
        setTitle("Cancel Booking");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    /**
     * Places components on the panel.
     * 
     * @param panel the JPanel instance
     */
    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel customerIdLabel = new JLabel("Customer ID:");
        customerIdLabel.setBounds(10, 20, 80, 25);
        panel.add(customerIdLabel);

        customerIdField = new JTextField(20);
        customerIdField.setBounds(100, 20, 165, 25);
        panel.add(customerIdField);

        JLabel flightIdLabel = new JLabel("Flight ID:");
        flightIdLabel.setBounds(10, 50, 80, 25);
        panel.add(flightIdLabel);

        flightIdField = new JTextField(20);
        flightIdField.setBounds(100, 50, 165, 25);
        panel.add(flightIdField);

        cancelButton = new JButton("Cancel Booking");
        cancelButton.setBounds(10, 80, 150, 25);
        cancelButton.addActionListener(this);
        panel.add(cancelButton);
    }

    /**
     * Handles the action performed event.
     * 
     * @param e the ActionEvent instance
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelButton) {
            String customerIdInput = customerIdField.getText();
            String flightIdInput = flightIdField.getText();
            if (!customerIdInput.isEmpty() && !flightIdInput.isEmpty()) {
                try {
                    int customerId = Integer.parseInt(customerIdInput);
                    int flightId = Integer.parseInt(flightIdInput);
                    
                    // Create and execute CancelBooking command
                    CancelBooking cancelBooking = new CancelBooking(customerId, flightId);
                    cancelBooking.execute(fbs);

                    // Update the UI to reflect the cancellation
                    JOptionPane.showMessageDialog(this, "Booking successfully canceled for customer ID: " 
                            + customerId + " and flight ID: " + flightId);

                    // Close the window
                    dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter valid numeric IDs.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (FlightBookingSystemException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter valid customer ID and flight ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
