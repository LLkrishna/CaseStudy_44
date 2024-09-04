package com;
import java.util.Scanner;

import com.cts.coms.exception.CustomerNotFoundException;
import com.cts.coms.exception.OrderNotFoundException;
import com.cts.coms.exception.ProductNotFoundException;
import com.cts.coms.model.Management;

public class Main {
    public static void main(String[] args) throws CustomerNotFoundException, OrderNotFoundException, ProductNotFoundException {
        Scanner scanner = new Scanner(System.in); // Initialize scanner for user input
        int choice; // Variable to store user's menu choice

        do {
            // Displaying the main menu
            System.out.println("Welcome to the Retail Management System");
            System.out.println("1. Customer Management");
            System.out.println("2. Product Management");
            System.out.println("3. Order Management");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt(); // Reading user choice

            Management m = new Management(); // Creating an instance of Management class to handle operations

            // Handling user choice with a switch-case structure
            switch (choice) {
                case 1:
                    m.manageCustomer(scanner); // Call method to manage customers
                    break;
                case 2:
                    m.manageProduct(scanner); // Call method to manage products
                    break;
                case 3:
                    m.manageOrder(scanner); // Call method to manage orders
                    break;
                case 4:
                    System.out.println("Exiting the system..."); // Exit message
                    break;
                default:
                    System.out.println("Invalid choice. Please try again."); // Handle invalid input
            }
        } while (choice != 4); // Continue to show the menu until the user chooses to exit

        scanner.close(); // Close the scanner to prevent resource leaks
    }
}
