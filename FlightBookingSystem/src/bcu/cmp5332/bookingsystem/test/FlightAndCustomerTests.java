package bcu.cmp5332.bookingsystem.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

import org.junit.Test;

import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;

/**
 * Tests for Flight and Customer classes.
 */
public class FlightAndCustomerTests {

    /**
     * Tests the creation of a Flight object.
     */
    @Test
    public void testFlightCreation() {
   
        int id = 1;
        String flightNumber = "FL123";
        String origin = "London";
        String destination = "New York";
        LocalDate departureDate = LocalDate.now().plusDays(7);
        int numberOfSeats = 200;
        double price = 500;

     
        Flight flight = new Flight(id, flightNumber, origin, destination, departureDate, numberOfSeats, price);

  
        assertNotNull(flight);
        assertEquals(id, flight.getId());
        assertEquals(flightNumber, flight.getFlightNumber());
        assertEquals(origin, flight.getOrigin());
        assertEquals(destination, flight.getDestination());
        assertEquals(departureDate, flight.getDepartureDate());
        assertEquals(numberOfSeats, flight.getNumberOfSeats());
        assertEquals(price, flight.getPrice(), 0.001);
    }

    /**
     * Tests the creation of a Customer object.
     */
    @Test
    public void testCustomerCreation() {
       
        int id = 1;
        String name = "John Doe";
        String phoneNumber = "1234567890";
        String email = "john.doe@example.com";

       
        Customer customer = new Customer(id, name, phoneNumber, email);

  
        assertNotNull(customer);
        assertEquals(id, customer.getId());
        assertEquals(name, customer.getName());
        assertEquals(phoneNumber, customer.getPhone());
        assertEquals(email, customer.getEmail());
    }
}
