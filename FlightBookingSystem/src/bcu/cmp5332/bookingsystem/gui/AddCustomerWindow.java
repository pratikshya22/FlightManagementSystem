package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddCustomer;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a window for adding a new customer to the flight booking system.
 */
public class AddCustomerWindow extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private static AddCustomerWindow instance;

    private MainWindow mw;
    private JTextField nameText = new JTextField();
    private JTextField phoneText = new JTextField();
    private JTextField emailText = new JTextField();

    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");

    /**
     * Constructs an AddCustomerWindow object with the specified MainWindow.
     *
     * @param mw The MainWindow object.
     */
    public AddCustomerWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    /**
     * Returns the instance of AddCustomerWindow.
     *
     * @param mw The MainWindow object.
     * @return The AddCustomerWindow instance.
     */
    public static AddCustomerWindow getInstance(MainWindow mw) {
        if (instance == null) {
            instance = new AddCustomerWindow(mw);
        }
        return instance;
    }


    /**
     * Initializes the AddCustomerWindow by setting up the UI components.
     */
    private void initialize() {
        setTitle("Add a New Customer");
        setSize(350, 220);

        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        topPanel.add(new JLabel("Name : "), gbc);
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // This will make the name field expand horizontally
        topPanel.add(nameText, gbc);
        gbc.gridy = 2;
        topPanel.add(new JLabel("Phone : "), gbc);
        gbc.gridy = 3;
        topPanel.add(phoneText, gbc);
        gbc.gridy = 4;
        topPanel.add(new JLabel("Email : "), gbc);
        gbc.gridy = 5;
        topPanel.add(emailText, gbc);


        JPanel bottomPanel = new JPanel(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(addBtn);
        bottomPanel.add(cancelBtn);

        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        getContentPane().add(topPanel, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            addCustomer();
        } else if (ae.getSource() == cancelBtn) {
            this.dispose();
        }

    }

    /**
     * Adds a new customer to the flight booking system based on the user input.
     */
    /**
     * Adds a new customer to the flight booking system based on the user input.
     */
    private void addCustomer() {
        try {
            String name = nameText.getText();
            String phone = phoneText.getText();
            String email = emailText.getText();
            // create and execute the AddCustomer Command
            Command addCustomer = new AddCustomer(name, phone, email);
            addCustomer.execute(mw.getFlightBookingSystem());
            // refresh the view with the list of customers
            mw.displayCustomers();
            // display success message
            JOptionPane.showMessageDialog(this, "Customer added successfully");
            // hide (close) the AddCustomerWindow
            this.dispose();
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
