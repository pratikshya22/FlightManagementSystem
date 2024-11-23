package bcu.cmp5332.bookingsystem.main;

/**
 * FlightBookingSystemException extends {@link Exception} class and is a custom exception
 * that is used to notify the user about errors or invalid commands.
 */
public class FlightBookingSystemException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructs a new FlightBookingSystemException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public FlightBookingSystemException(String message) {
        super(message);
    }
    
    
}
