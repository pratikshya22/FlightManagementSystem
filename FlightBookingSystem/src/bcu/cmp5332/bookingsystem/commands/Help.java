package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The Help class represents a command to display the help message containing a list of available commands.
 */
public class Help implements Command {

    /**
     * Executes the command to display the help message containing a list of available commands.
     *
     * @param flightBookingSystem The flight booking system.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) {
        System.out.println(Command.HELP_MESSAGE);
    }
}
