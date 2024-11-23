package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * The ShowCustomer class represents a command to display details of a specific customer.
 */
public class ShowCustomer implements Command {

    private final int customerId;

    /**
     * Constructs a new ShowCustomer object with the specified customer ID.
     *
     * @param customerId The ID of the customer to display details for.
     */
    public ShowCustomer(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Executes the command to display details of the specified customer.
     *
     * @param fbs The FlightBookingSystem object.
     * @throws FlightBookingSystemException If there is an error displaying customer details.
     */
    @Override
    public void execute(FlightBookingSystem fbs) throws FlightBookingSystemException {
        Customer customer = fbs.getCustomerByID(customerId);
        if (customer == null) {
            throw new FlightBookingSystemException("Customer with ID " + customerId + " not found.");
        }

        System.out.println("Customer ID: " + customer.getId());
        System.out.println("Name: " + customer.getName());
        System.out.println("Phone: " + customer.getPhone());
        System.out.println("Email: " + customer.getEmail());

        List<Booking> bookings = customer.getActiveBookings(); // Get only active bookings
        if (bookings.isEmpty()) {
            System.out.println("This customer has not made any bookings.");
        } else {
            System.out.println("Bookings:");
            for (Booking booking : bookings) {
                Flight flight = booking.getFlight();
                System.out.println("Booking ID: " + booking.getId());
                System.out.println("Flight Number: " + flight.getFlightNumber());
                System.out.println("Origin: " + flight.getOrigin());
                System.out.println("Destination: " + flight.getDestination());
                System.out.println("Date: " + flight.getDepartureDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                System.out.println("Price: " + booking.getPrice());
                System.out.println();
            }
        }
    }

}
