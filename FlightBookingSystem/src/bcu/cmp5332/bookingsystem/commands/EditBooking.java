package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import java.io.IOException;

import bcu.cmp5332.bookingsystem.data.BookingDataManager;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The EditBooking class represents a command to update a booking by changing the flight.
 */
public class EditBooking implements Command {

    private final int bookingId; // ID of the booking to be edited
    private final int newFlightId; // ID of the new flight

    /**
     * Constructs an EditBooking object with the specified booking ID and new flight ID.
     *
     * @param bookingId   The ID of the booking to be edited.
     * @param newFlightId The ID of the new flight.
     */
    public EditBooking(int bookingId, int newFlightId) {
        this.bookingId = bookingId;
        this.newFlightId = newFlightId;
    }

    /**
     * Executes the command to update a booking by changing the flight.
     *
     * @param fbs The flight booking system.
     * @throws FlightBookingSystemException If an error occurs while executing the command.
     */
    @Override
    public void execute(FlightBookingSystem fbs) throws FlightBookingSystemException {
        Booking booking = fbs.getBookingByID(bookingId);
        if (booking == null) {
            throw new FlightBookingSystemException("Booking not found for ID: " + bookingId);
        }

        Flight newFlight = fbs.getFlightByID(newFlightId);
        if (newFlight == null) {
            throw new FlightBookingSystemException("New flight not found for ID: " + newFlightId);
        }

        // Remove passenger from current flight
        Flight currentFlight = booking.getFlight();
        currentFlight.removePassenger(booking.getCustomer());

        // Check if the new flight has available seats
        if (newFlight.getPassengers().size() >= newFlight.getNumberOfSeats()) {
            throw new FlightBookingSystemException("The new flight is full. Booking cannot be moved.");
        }

        // Move booking to the new flight
        booking.setFlight(newFlight);
        newFlight.addPassenger(booking.getCustomer());

        // Store the updated data using BookingDataManager
        BookingDataManager manager = new BookingDataManager();
        try {
            manager.storeData(fbs);
        } catch (IOException e) {
            throw new FlightBookingSystemException("Error storing booking data: " + e.getMessage());
        }

        System.out.println("Booking successfully updated.");
    }
}
