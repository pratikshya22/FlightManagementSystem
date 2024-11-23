package bcu.cmp5332.bookingsystem.gui;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A JPanel for displaying bookings in a table format.
 */
public class DisplayBookingsWindow extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new DisplayBookingsWindow.
     */
    public DisplayBookingsWindow() {
        initialize();
    }

    /**
     * Initializes the DisplayBookingsWindow.
     */
    private void initialize() {
        setLayout(new BorderLayout()); 

        String[] columns = {"Booking ID", "Customer Name", "Flight Number", "Booking Date", "Price", "Status"};
        Object[][] data = readBookingsFromFile("resources/data/bookings.txt");

        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER); 
    }

    /**
     * Reads bookings from a file and converts them into a 2D array.
     *
     * @param filename The name of the file containing bookings.
     * @return A 2D array representing bookings data.
     */
    private Object[][] readBookingsFromFile(String filename) {
        List<Object[]> bookingsData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int bookingId = Integer.parseInt(parts[0]);
                String customerName = getCustomerName(Integer.parseInt(parts[1]));
                String flightNumber = getFlightNumber(Integer.parseInt(parts[2]));
                String bookingDate = parts[3];
                double price = Double.parseDouble(parts[4]);
                String status = parts.length > 5 && parts[5].equals("cancelled") ? "Cancelled" : "Active";

                bookingsData.add(new Object[]{bookingId, customerName, flightNumber, bookingDate, price, status});
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading bookings from file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return bookingsData.toArray(new Object[0][]);
    }

    /**
     * Retrieves the name of a customer.
     *
     * @param customerId The ID of the customer.
     * @return The name of the customer.
     */
    private String getCustomerName(int customerId) {
   
        return "Customer " + customerId;
    }

    /**
     * Retrieves the flight number.
     *
     * @param flightId The ID of the flight.
     * @return The flight number.
     */
    private String getFlightNumber(int flightId) {

        return "Flight " + flightId;
    }
}
