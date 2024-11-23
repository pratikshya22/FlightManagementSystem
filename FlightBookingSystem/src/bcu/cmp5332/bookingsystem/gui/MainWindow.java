package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.BookingDataManager;
//import bcu.cmp5332.bookingsystem.commands.ViewBooking;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


public class MainWindow extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

   
    private JPanel mainPanel;

    private JMenuBar menuBar;
    private JMenu adminMenu;
    private JMenu flightsMenu;
    private JMenu bookingsMenu;
    private JMenu customersMenu;

    private JMenuItem adminExit;

    private JMenuItem flightsView;
    private JMenuItem flightsAdd;
    private JMenuItem flightsDel;
    private JMenuItem flightsPassengerList;

    private JMenuItem bookingsAdd;
    private JMenuItem bookingsUpdate;
    private JMenuItem bookingsCancel;
    private JMenuItem bookingsView;

    private JMenuItem custView;
    private JMenuItem custAdd;
    private JMenuItem custDel;

    private FlightBookingSystem fbs;

    public MainWindow(FlightBookingSystem fbs) {
        initialize();
        this.fbs = fbs;
        setTitle("Flight Booking System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }


	public FlightBookingSystem getFlightBookingSystem() {
        return fbs;
    }

    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        setTitle("Flight Booking Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        menuBar = new JMenuBar();
        
        setJMenuBar(menuBar);

        adminMenu = new JMenu("Admin");
        menuBar.add(adminMenu);

        adminExit = new JMenuItem("Exit");
        adminMenu.add(adminExit);
        adminExit.addActionListener(this);

        flightsMenu = new JMenu("Flights");
        menuBar.add(flightsMenu);

     // Create menu items for Flights menu
        flightsView = new JMenuItem("View");
        flightsAdd = new JMenuItem("Add");
        flightsDel = new JMenuItem("Delete");

        // Add action listeners to each menu item
        flightsView.addActionListener(this);
        flightsAdd.addActionListener(this);
        flightsDel.addActionListener(this);

        // Add menu items to Flights menu
        flightsMenu.add(flightsView);
        flightsMenu.add(flightsAdd);
        flightsMenu.add(flightsDel);


        flightsPassengerList = new JMenuItem("Passenger List");
        flightsMenu.add(flightsPassengerList);
        flightsPassengerList.addActionListener(this);

        bookingsMenu = new JMenu("Bookings");
        menuBar.add(bookingsMenu);

        bookingsAdd = new JMenuItem("Add");
        bookingsUpdate = new JMenuItem("Update");
        bookingsCancel = new JMenuItem("Cancel");
        bookingsView = new JMenuItem("View All");

        bookingsMenu.add(bookingsAdd);
        bookingsMenu.add(bookingsUpdate);
        bookingsMenu.add(bookingsCancel);
        bookingsMenu.add(bookingsView);

        for (int i = 0; i < bookingsMenu.getItemCount(); i++) {
            bookingsMenu.getItem(i).addActionListener(this);
        }
        bookingsUpdate.addActionListener(this);
        bookingsView.addActionListener(this);

        customersMenu = new JMenu("Customers");
        menuBar.add(customersMenu);

        custView = new JMenuItem("View");
        custAdd = new JMenuItem("Add");
        customersMenu.add(custAdd);
        custAdd.addActionListener(this);

        custDel = new JMenuItem("Delete");

        customersMenu.add(custView);
        customersMenu.add(custAdd);
        customersMenu.add(custDel);

        custView.addActionListener(this);
        custAdd.addActionListener(this);
        custDel.addActionListener(this);

        

        setVisible(true);
        setAutoRequestFocus(true);
        toFront();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(191, 119, 246));
      
        add(mainPanel);

       
        JLabel welcomeLabel = new JLabel("<html><center>WELCOME TO<br><br>OUR<br><br> FLIGHT MANAGEMENT SYSTEM<br></center></html>");
        welcomeLabel.setFont(new Font("Times new Roman", Font.BOLD, 36));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        mainPanel.add(welcomeLabel, BorderLayout.CENTER);
    }

    public static void main(String[] args) throws IOException, FlightBookingSystemException {
        FlightBookingSystem fbs = FlightBookingSystemData.load();
    
        new MainWindow(fbs);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == adminExit) {
            try {
                FlightBookingSystemData.store(fbs);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
            }
            System.exit(0);
        } else if (ae.getSource() == flightsView) {
            displayFlights();
        } else if (ae.getSource() == flightsAdd) {
            new AddFlightWindow(this);
        } else if (ae.getSource() == flightsDel) {
            int flightId = -1;
            String input = JOptionPane.showInputDialog(this, "Enter Flight ID:");
            if (input != null && !input.isEmpty()) {
                try {
                    flightId = Integer.parseInt(input);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid flight ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                return;
            }
            deleteFlight(flightId);
        } else if (ae.getSource() == flightsPassengerList) {
            displayPassengerList();
            
            
        } else if (ae.getSource() == bookingsAdd) {
            new AddBookingWindow(this);
        } else if (ae.getSource() == bookingsCancel) {
        	new CancelBookingWindow(this);
        } else if (ae.getSource() == bookingsUpdate) {
            BookingDataManager bookingDataManager = new BookingDataManager();
            new UpdateBookingWindow(bookingDataManager, fbs);
        }else if (ae.getSource() == bookingsView) {
            mainPanel.removeAll(); 
            DisplayBookingsWindow bookingsWindow = new DisplayBookingsWindow(); 
            mainPanel.add(bookingsWindow, BorderLayout.CENTER); 
            mainPanel.revalidate(); 	
            
            
        } else if (ae.getSource() == custView) {
            displayCustomers();
        } else if (ae.getSource() == custAdd) {
            new AddCustomerWindow(this);
        } else if (ae.getSource() == custDel) {
            String input = JOptionPane.showInputDialog(this, "Enter Customer ID:");
            if (input != null) {
                try {
                    int customerId = Integer.parseInt(input);
                    deleteCustomer(customerId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid customer ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void displayPassengerList() {
        String input = JOptionPane.showInputDialog(this, "Enter Flight ID:");
        if (input != null && !input.isEmpty()) {
            try {
                int flightId = Integer.parseInt(input);
                Flight flight = fbs.getFlightByID(flightId);
                if (flight != null) {
                    List<Customer> passengers = flight.getPassengers();
                    new PassengerListWindow(passengers, flight.getFlightNumber());
                    
                } else {
                    JOptionPane.showMessageDialog(this, "Flight not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    
                    
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid flight ID.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (FlightBookingSystemException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void deleteFlight(int flightId) {
        try {
            fbs.deleteFlight(flightId);
            JOptionPane.showMessageDialog(this, "Flight deleted successfully.");
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteCustomer(int customerId) {
        try {
            fbs.deleteCustomer(customerId);
            JOptionPane.showMessageDialog(this, "Customer deleted successfully.");
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void displayCustomers() {
        List<Customer> customersList = fbs.getCustomers();

        String[] columns = new String[]{"ID", "Name", "Phone", "Email", "Number of Active Bookings"};
        Object[][] data = new Object[customersList.size()][5];

        for (int i = 0; i < customersList.size(); i++) {
            Customer customer = customersList.get(i);
            int activeBookingsCount = customer.getActiveBookings().size(); 
            data[i][0] = customer.getId();
            data[i][1] = customer.getName();
            data[i][2] = customer.getPhone();
            data[i][3] = customer.getEmail();
            data[i][4] = activeBookingsCount;
        }

        JTable table = new JTable(data, columns);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int customerId = (int) table.getValueAt(selectedRow, 0);
                    Customer selectedCustomer = null;
                    try {
                        selectedCustomer = fbs.getCustomerByID(customerId);
                        showBookingDetails(selectedCustomer);
                    } catch (FlightBookingSystemException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }

    public void showBookingDetails(Customer customer) {
        List<Booking> bookings = customer.getBookings();

        String[] columns = {"Booking ID", "Flight", "Booking Date", "Price", "Cancellation Fee", "Rebook Fee"};
        Object[][] data = new Object[bookings.size()][6];
        int i = 0;
        for (Booking booking : bookings) {
            data[i][0] = booking.getId();
            data[i][1] = booking.getFlight().getFlightNumber();
            data[i][2] = booking.getBookingDate();
            data[i][3] = booking.getPrice();
            data[i][4] = booking.getCancellationFee();
            data[i][5] = booking.getRebookFee();
            i++;
        }

        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);

        JFrame frame = new JFrame("Booking Details");
        frame.getContentPane().add(scrollPane);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }


 
    public void displayFlights() {
        List<Flight> flightsList = fbs.getFlights();
        LocalDate today = LocalDate.now();

        String[] columns = new String[]{"Flight ID", "Flight No", "Origin", "Destination", "Departure Date", "Number of Seats", "Price", "Fully Booked", "Departed"};

        Object[][] data = new Object[flightsList.size()][9];
        for (int i = 0; i < flightsList.size(); i++) {
            Flight flight = flightsList.get(i);
            boolean fullyBooked = flight.isFullyBooked();
            boolean departed = !flight.hasNotDeparted(today); // Check if flight has departed

            String price;
            try {
                price = String.valueOf(flight.calculatePrice(today));
            } catch (FlightBookingSystemException ex) {
                // Handle the exception by setting a placeholder message for the price
                price = "Price calculation error";
            }

            data[i][0] = flight.getId();
            data[i][1] = flight.getFlightNumber();
            data[i][2] = flight.getOrigin();
            data[i][3] = flight.getDestination();
            data[i][4] = flight.getDepartureDate();
            data[i][5] = flight.getNumberOfSeats();
            data[i][6] = price;
            data[i][7] = fullyBooked ? "Yes" : "No";
            data[i][8] = departed ? "Yes" : "No";
        }

        JTable table = new JTable(data, columns);
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }



    
    
}

