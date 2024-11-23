package bcu.cmp5332.bookingsystem.main;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.*;

/**
 * The Main class is the entry point for the Flight Booking System application.
 * 
 * It initializes the FlightBookingSystem, loads data from a file, and provides a command-line interface
 * for interacting with the system. It accepts commands from the user, executes them, and displays any
 * relevant information or error messages.
 * 
 * @author Pragya Gajurel, Pratikshya Karki

 */
public class Main {

    /**
     * The main method serves as the entry point for the Flight Booking System application.
     * 
     * It initializes the FlightBookingSystem, loads data from a file, and provides a command-line interface
     * for interacting with the system. It accepts commands from the user, executes them, and displays any
     * relevant information or error messages.
     * 
     * @param args command-line arguments (not used)
     * @throws IOException if an I/O error occurs
     * @throws FlightBookingSystemException if an error related to the Flight Booking System occurs
     */
    public static void main(String[] args) throws IOException, FlightBookingSystemException {
        
        // Load the FlightBookingSystem data
        FlightBookingSystem fbs = FlightBookingSystemData.load();

        // Create a BufferedReader to read input from the console
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Display welcome message and instructions
        System.out.println("Flight Booking System");
        System.out.println("Enter 'help' to see a list of available commands.");
        
        // Main loop for accepting user input and executing commands
        while (true) {
            System.out.print("> ");
            String line = br.readLine();
            // Exit the loop if the user enters 'exit'
            if (line.equals("exit")) {
                break;
            }
            try {
                // Parse and execute the command
                Command command = CommandParser.parse(line, fbs); 
                command.execute(fbs);
            } catch (FlightBookingSystemException ex) {
                // Display any error messages
                System.out.println(ex.getMessage());
            }
        }
        
        // Store the FlightBookingSystem data before exiting
        FlightBookingSystemData.store(fbs);
        // Exit the application
        System.exit(0);
    }
}




