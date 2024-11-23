package bcu.cmp5332.bookingsystem.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Customer class represents a customer in the flight booking system.
 * It contains information about the customer such as their ID, name, phone number, email, balance, and list of bookings.
 */
public class Customer {

    private int id; // The unique identifier for the customer
    private String name; // The name of the customer
    private String phone; // The phone number of the customer
    private String email; // The email address of the customer
    private final List<Booking> bookings = new ArrayList<>(); // List of bookings made by the customer
    private boolean deleted; // Flag indicating whether the customer is deleted

    /**
     * Constructs a new Customer object with the specified parameters.
     *
     * @param id    The unique identifier for the customer.
     * @param name  The name of the customer.
     * @param phone The phone number of the customer.
     * @param email The email address of the customer.
     */
    public Customer(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.deleted = false;
    }

    /**
     * Returns the unique identifier for the customer.
     *
     * @return The customer ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the customer.
     *
     * @param id The customer ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the customer.
     *
     * @return The name of the customer.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the customer.
     *
     * @param name The name of the customer.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the phone number of the customer.
     *
     * @return The phone number of the customer.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the customer.
     *
     * @param phone The phone number of the customer.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the email address of the customer.
     *
     * @return The email address of the customer.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the customer.
     *
     * @param email The email address of the customer.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the list of bookings made by the customer.
     *
     * @return The list of bookings made by the customer.
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Adds a booking to the list of bookings made by the customer.
     *
     * @param booking The booking to be added.
     */
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    /**
     * Returns a short string representation of the customer's details.
     *
     * @return A short string representation of the customer's details.
     */
    public String getDetailsShort() {
        return "Customer #" + id + " - " + name + " - " + phone + " - " + email;
    }

    /**
     * Returns the booking made by the customer for the specified flight ID.
     *
     * @param flightId The ID of the flight.
     * @return The booking made by the customer for the specified flight ID.
     */
    public Booking getBookingByFlightId(int flightId) {
        for (Booking booking : bookings) {
            if (booking.getFlight().getId() == flightId) {
                return booking;
            }
        }
        return null;
    }

    /**
     * Returns the number of bookings made by the customer.
     *
     * @return The number of bookings made by the customer.
     */
    public int getNumberOfBookings() {
        return bookings.size();
    }

    /**
     * Removes a booking from the list of bookings made by the customer.
     *
     * @param booking The booking to be removed.
     */
    public void removeBooking(Booking booking) {
        bookings.remove(booking);
    }

    /**
     * Returns whether the customer is deleted or not.
     *
     * @return True if the customer is deleted, false otherwise.
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Sets the deleted status of the customer.
     *
     * @param deleted True if the customer is deleted, false otherwise.
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Checks if any of the customer's bookings are cancelled.
     *
     * @return True if at least one booking is cancelled, false otherwise.
     */
    public boolean isCancelled() {
        for (Booking booking : bookings) {
            if (booking.isCancelled()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a list of active bookings (not cancelled) made by the customer.
     *
     * @return The list of active bookings made by the customer.
     */
    public List<Booking> getActiveBookings() {
        List<Booking> activeBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (!booking.isCancelled()) {
                activeBookings.add(booking);
            }
        }
        return activeBookings;
    }

    /**
     * Updates the number of bookings for the customer, considering only active bookings (not cancelled).
     */
    public void updateNumberOfBookings() {
        // Get the list of active bookings (not cancelled)
        List<Booking> activeBookings = getActiveBookings();

        // Update the number of bookings for the customer
        bookings.clear();
        bookings.addAll(activeBookings);
    }
}
