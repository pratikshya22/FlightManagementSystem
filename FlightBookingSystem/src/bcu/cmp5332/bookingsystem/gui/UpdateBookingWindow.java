package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.BookingDataManager;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;

import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class UpdateBookingWindow extends JFrame implements ActionListener {

    /**
	 * A JFrame for updating bookings.
	 */
	private static final long serialVersionUID = 1L;
	private JLabel bookingIdLabel;
    private JTextField bookingIdField;
    private JLabel currentFlightIdLabel;
    private JTextField currentFlightIdField;
    private JLabel newFlightIdLabel;
    private JTextField newFlightIdField;
    private JButton updateButton;

    private BookingDataManager bookingDataManager;
    private FlightBookingSystem fbs;
    
    /**
     * Constructs a new UpdateBookingWindow.
     *
     * @param bookingDataManager The BookingDataManager to use.
     * @param fbs                The FlightBookingSystem to use.
     */
    public UpdateBookingWindow(BookingDataManager bookingDataManager, FlightBookingSystem fbs) {
        this.bookingDataManager = bookingDataManager;
        this.fbs = fbs;
        initialize();
        setTitle("Update Booking");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initialize() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        bookingIdLabel = new JLabel("Booking ID:");
        bookingIdField = new JTextField();
        currentFlightIdLabel = new JLabel("Current Flight ID:");
        currentFlightIdField = new JTextField();
        newFlightIdLabel = new JLabel("New Flight ID:");
        newFlightIdField = new JTextField();
        updateButton = new JButton("Update");
        updateButton.addActionListener(this);

        panel.add(bookingIdLabel);
        panel.add(bookingIdField);
        panel.add(currentFlightIdLabel);
        panel.add(currentFlightIdField);
        panel.add(newFlightIdLabel);
        panel.add(newFlightIdField);
        panel.add(new JLabel());
        panel.add(updateButton);

        add(panel);
    }
    /**
     * Handles button click events.
     *
     * @param e The ActionEvent.
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateButton) {
            updateBooking();
        }
    }
    /**
     * Updates the booking.
     */
    private void updateBooking() {
        try {
            int bookingId = Integer.parseInt(bookingIdField.getText());
            int currentFlightId = Integer.parseInt(currentFlightIdField.getText());
            int newFlightId = Integer.parseInt(newFlightIdField.getText());

            Booking booking = fbs.getBookingByID(bookingId);
            if (booking != null && booking.getFlight().getId() == currentFlightId && !booking.isCancelled()) {
                Flight newFlight = fbs.getFlightByID(newFlightId);
                if (newFlight != null) {
                    booking.setFlight(newFlight);
                    bookingDataManager.storeData(fbs);
                    JOptionPane.showMessageDialog(this, "Booking updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "New Flight ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Booking not found, is cancelled, or current flight ID is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException | IOException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid booking ID, current flight ID, and new flight ID.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        BookingDataManager bookingDataManager = new BookingDataManager(); 
        FlightBookingSystem fbs = new FlightBookingSystem(); 
        new UpdateBookingWindow(bookingDataManager, fbs);
    }
}
