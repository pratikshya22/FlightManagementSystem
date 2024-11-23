package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

/**
 * The AddBooking class represents a command to add a booking for a customer on a flight.
 */
public class AddBooking implements Command {

    private final int customerId; // ID of the customer
    private final int flightId; // ID of the flight
    private final LocalDate bookingDate; // Booking date

    /**
     * Constructs an AddBooking object with the specified customer ID, flight ID, and booking date.
     *
     * @param customerId The ID of the customer.
     * @param flightId   The ID of the flight.
     * @param bookingDate The booking date.
     */
    public AddBooking(int customerId, int flightId, LocalDate bookingDate) {
        this.customerId = customerId;
        this.flightId = flightId;
        this.bookingDate = bookingDate;
    }

    /**
     * Executes the command to add a booking for a customer on a flight.
     *
     * @param fbs The flight booking system.
     * @throws FlightBookingSystemException If an error occurs while executing the command.
     */
    @Override
    public void execute(FlightBookingSystem fbs) throws FlightBookingSystemException {
        LocalDate today = LocalDate.now();
        LocalDate twoYearsFromToday = today.plusYears(2);

        if (bookingDate.isAfter(twoYearsFromToday)) {
            throw new FlightBookingSystemException("Bookings more than 2 years in advance are not allowed.");
        }

        Customer customer = fbs.getCustomerByID(customerId);
        if (customer == null) {
            throw new FlightBookingSystemException("Customer with ID " + customerId + " not found.");
        }

        Flight flight = fbs.getFlightByID(flightId);
        if (flight == null) {
            throw new FlightBookingSystemException("Flight with ID " + flightId + " not found.");
        }

        if (!flight.hasNotDeparted(today)) {
            throw new FlightBookingSystemException("Cannot book a flight that has already departed.");
        }

        if (flight.getPassengers().size() >= flight.getNumberOfSeats()) {
            throw new FlightBookingSystemException("The flight is full. Booking cannot be made.");
        }

        int price = flight.calculatePrice(today);

        int bookingId = fbs.generateBookingId();
        Booking booking = new Booking(bookingId, customer, flight, bookingDate, price);
        customer.addBooking(booking);
        flight.addPassenger(customer);

        if (!booking.isCancelled()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("resources/data/bookings.txt", true))) {
                writer.write(booking.getId() + "," + customer.getId() + "," + flight.getId() + "," + booking.getBookingDate() + "," + booking.getPrice());
                writer.newLine();
            } catch (IOException e) {
                throw new FlightBookingSystemException("Error writing to bookings.txt: " + e.getMessage());
            }
        }

        System.out.println("Booking was issued successfully to the customer.");
    }
}
