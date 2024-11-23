package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import java.io.IOException;

import bcu.cmp5332.bookingsystem.data.BookingDataManager;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The CancelBooking class represents a command to cancel a booking for a customer on a flight.
 */
public class CancelBooking implements Command {

    private final int customerId; // ID of the customer
    private final int flightId; // ID of the flight

    /**
     * Constructs a CancelBooking object with the specified customer ID and flight ID.
     *
     * @param customerId The ID of the customer.
     * @param flightId   The ID of the flight.
     */
    public CancelBooking(int customerId, int flightId) {
        this.customerId = customerId;
        this.flightId = flightId;
    }

    /**
     * Executes the command to cancel a booking for a customer on a flight.
     *
     * @param fbs The flight booking system.
     * @throws FlightBookingSystemException If an error occurs while executing the command.
     */
    @Override
    public void execute(FlightBookingSystem fbs) throws FlightBookingSystemException {
        Customer customer = fbs.getCustomerByID(customerId);
        if (customer == null) {
            throw new FlightBookingSystemException("Customer not found for ID: " + customerId);
        }

        Flight flight = fbs.getFlightByID(flightId);
        if (flight == null) {
            throw new FlightBookingSystemException("Flight not found for ID: " + flightId);
        }

        Booking booking = null;
        for (Booking b : customer.getBookings()) {
            if (b.getFlight().getId() == flightId) {
                booking = b;
                break;
            }
        }

        if (booking == null) {
            throw new FlightBookingSystemException("No booking found for customer ID: " + customerId + " and flight ID: " + flightId);
        }

        // Cancel the booking
        booking.cancelBooking();

        // Store updated data
        BookingDataManager dataManager = new BookingDataManager();
        try {
            dataManager.storeData(fbs);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Booking successfully canceled for customer ID: " + customerId + " and flight ID: " + flightId);
    }
}
