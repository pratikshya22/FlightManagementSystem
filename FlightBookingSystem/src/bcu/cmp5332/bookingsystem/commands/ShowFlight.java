package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.util.List;

/**
 * The ShowFlight class represents a command to display details of a specific flight.
 */
public class ShowFlight implements Command {

    private final int flightId;

    /**
     * Constructs a new ShowFlight object with the specified flight ID.
     *
     * @param flightId The ID of the flight to display details for.
     */
    public ShowFlight(int flightId) {
        this.flightId = flightId;
    }

    /**
     * Executes the command to display details of the specified flight.
     *
     * @param fbs The FlightBookingSystem object.
     * @throws FlightBookingSystemException If there is an error displaying flight details.
     */
    @Override
    public void execute(FlightBookingSystem fbs) throws FlightBookingSystemException {
        Flight flight = fbs.getFlightByID(flightId);
        if (flight == null) {
            throw new FlightBookingSystemException("Flight with ID " + flightId + " not found.");
        }

        System.out.println("Flight Number: " + flight.getFlightNumber());
        System.out.println("Origin: " + flight.getOrigin());
        System.out.println("Destination: " + flight.getDestination());
        System.out.println("Departure Date: " + flight.getDepartureDate());
        System.out.println("Number of Seats: " + flight.getNumberOfSeats());
        System.out.println("Price: " + flight.getPrice());

        List<Customer> passengers = flight.getPassengers();
        if (passengers.isEmpty()) {
            System.out.println("No passengers booked for this flight.");
        } else {
            System.out.println("Passengers:");
            for (Customer passenger : passengers) {
                System.out.println("Name: " + passenger.getName());
                System.out.println("Phone Number: " + passenger.getPhone());
                System.out.println();
            }
        }
    }
}
