package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.gui.MainWindow;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

/**
 * The LoadGUI class represents a command to load the GUI version of the flight booking system.
 */
public class LoadGUI implements Command {

    /**
     * Executes the command to load the GUI version of the flight booking system.
     *
     * @param flightBookingSystem The FlightBookingSystem object.
     * @throws FlightBookingSystemException If there is an error loading the GUI.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        new MainWindow(flightBookingSystem);
    }
    
}
