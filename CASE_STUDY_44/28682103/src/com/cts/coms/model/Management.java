package com.cts.coms.model;
import java.util.Scanner;

import com.cts.coms.dao.*;
import com.cts.coms.exception.CustomerNotFoundException;
import com.cts.coms.exception.OrderNotFoundException;
import com.cts.coms.exception.ProductNotFoundException;


public class Management {

    // Method to handle customer management operations
    public void manageCustomer(Scanner scanner) throws CustomerNotFoundException {
        int customerChoice;
        CustomerManagement customermanagement = new CustomerManagement(); // Customer management instance
        
        do {
            
            // Displaying Customer Management menu
            System.out.println("\nCustomer Management");
            System.out.println("1. Add a new customer");
            System.out.println("2. View customer details");
            System.out.println("3. Update customer information");
            System.out.println("4. Delete a customer");
            System.out.println("5. Back to Main Menu");
            System.out.println("Enter your choice: ");
            customerChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            
            // Handling user choice for customer management
            switch (customerChoice) {
                case 1:
                    addCustomer(scanner, customermanagement); // Add a new customer
                    break;
                case 2:
                    viewCustomer(scanner, customermanagement); // View customer details
                    break;
                case 3:
                    updateCustomer(scanner, customermanagement); // Update customer information
                    break;
                case 4:
                    deleteCustomer(scanner, customermanagement); // Delete a customer
                    break;
                case 5:
                    customermanagement.closeConnection(); // Close the connection before returning to main menu
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (customerChoice != 5); // Repeat until the user chooses to go back to the main menu
    }

    // Method to handle product management operations
    public void manageProduct(Scanner scanner) throws ProductNotFoundException {
        ProductManagement productmanagement = new ProductManagement(); // Product management instance
        int productChoice;
        do {
            // Displaying Product Management menu
            System.out.println("\nProduct Management");
            System.out.println("1. Add a new product");
            System.out.println("2. View product details");
            System.out.println("3. Update product information");
            System.out.println("4. Delete a product");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            productChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            // Handling user choice for product management
            switch (productChoice) {
                case 1:
                    addProduct(scanner, productmanagement); // Add a new product
                    break;
                case 2:
                    viewProduct(scanner, productmanagement); // View product details
                    break;
                case 3:
                    updateProduct(scanner, productmanagement); // Update product information
                    break;
                case 4:
                    deleteProduct(scanner, productmanagement); // Delete a product
                    break;
                case 5:
                    productmanagement.closeConnection(); // Close the connection before returning to main menu
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (productChoice != 5); // Repeat until the user chooses to go back to the main menu
    }

    // Method to handle order management operations
    public void manageOrder(Scanner scanner) throws OrderNotFoundException {
        OrderManagement ordermanagement  = new OrderManagement(); // Order management instance
        int orderChoice;
        do {
            // Displaying Order Management menu
            System.out.println("\nOrder Management");
            System.out.println("1. Place a new order");
            System.out.println("2. View order details");
            System.out.println("3. Update order status");
            System.out.println("4. Cancel an order");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            orderChoice = scanner.nextInt();

            // Handling user choice for order management
            switch (orderChoice) {
                case 1:
                    placeOrder(scanner, ordermanagement); // Place a new order
                    break;
                case 2:
                    viewOrder(scanner, ordermanagement); // View order details
                    break;
                case 3:
                    updateOrder(scanner, ordermanagement); // Update order status
                    break;
                case 4:
                    cancelOrder(scanner, ordermanagement); // Cancel an order
                    break;
                case 5:
                    ordermanagement.closeConnection(); // Close the connection before returning to main menu
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (orderChoice != 5); // Repeat until the user chooses to go back to the main menu
    }

    // Method to add a new customer
    private static void addCustomer(Scanner sc, CustomerManagement customermanagement) {
        System.out.println();
        System.out.print("Enter new customer name: ");
        String name = sc.nextLine();
        System.out.print("Enter new customer email: ");
        String email = sc.nextLine();
        System.out.print("Enter new customer phone number: ");
        String phone = sc.nextLine();
        System.out.print("Enter new customer address: ");
        String address = sc.nextLine();
        System.out.println("Adding a new customer...");
        customermanagement.addCustomer(name, email, phone, address); // Add customer details
        System.out.println("Added new Customer");
    }

    // Method to view customer details
    private static void viewCustomer(Scanner sc, CustomerManagement customermanagement) throws CustomerNotFoundException {
        System.out.print("Enter ID of the customer to view: ");
        int id = sc.nextInt();
        sc.nextLine(); // Consume the newline character
        System.out.println("Viewing customer details...");
        customermanagement.viewCustomer(id); // View customer by ID
    }

    // Method to update customer information
    private static void updateCustomer(Scanner sc, CustomerManagement customermanagement) {
        System.out.print("Enter ID of the customer to update: ");
        int id = sc.nextInt();
        sc.nextLine(); // Consume the newline character
        System.out.print("Enter new customer name: ");
        String name = sc.nextLine();
        System.out.print("Enter new customer email: ");
        String email = sc.nextLine();
        System.out.print("Enter new customer phone number: ");
        String phone = sc.nextLine();
        System.out.print("Enter new customer address: ");
        String address = sc.nextLine();
        System.out.println("Updating customer information...");
        customermanagement.updateCustomer(id, name, email, phone, address); // Update customer details
        System.out.println("Updated Successfully!");
    }

    // Method to delete a customer
    private static void deleteCustomer(Scanner sc, CustomerManagement customermanagement) {
        System.out.print("Enter ID of the customer to delete: ");
        int id = sc.nextInt();
        sc.nextLine(); // Consume the newline character
        customermanagement.deleteCustomer(id); // Delete customer by ID
    }

    // Method to add a new product
    private static void addProduct(Scanner sc, ProductManagement productmanagement) {
        System.out.println();
        System.out.print("Enter new product name: ");
        String name = sc.nextLine();
        System.out.print("Enter category of new product: ");
        String category = sc.nextLine();
        System.out.print("Enter price of new product: ");
        double price = sc.nextDouble();
        System.out.print("Enter quantity of new product: ");
        int quantity = sc.nextInt();
        System.out.println("Adding a new product...");
        productmanagement.addProduct(name, category, price, quantity); // Add product details
    }

    // Method to view product details
    private static void viewProduct(Scanner sc, ProductManagement productmanagement) throws ProductNotFoundException {
        System.out.print("Enter ID of the Product to view: ");
        int id = sc.nextInt();
        sc.nextLine(); // Consume the newline character
        System.out.println("Viewing product details...");
        productmanagement.viewProduct(id); // View product by ID
    }

    // Method to update product information
    private static void updateProduct(Scanner sc, ProductManagement productmanagement) {
        System.out.print("Enter ID of the product to update: ");
        int id = sc.nextInt();
        sc.nextLine(); // Consume the newline character
        System.out.print("Enter product name: ");
        String name = sc.nextLine();
        System.out.print("Enter category of product: ");
        String category = sc.nextLine();
        System.out.print("Enter price of product: ");
        double price = sc.nextDouble();
        System.out.print("Enter product quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine(); // Consume the newline character
        System.out.println("Updating product information...");
        productmanagement.updateProduct(id, name, category, price, quantity); // Update product details
        System.out.println("Updated Successfully!");
    }

    // Method to delete a product
    private static void deleteProduct(Scanner sc, ProductManagement productmanagement) {
        System.out.print("Enter ID of the product to delete: ");
        int id = sc.nextInt();
        sc.nextLine(); // Consume the newline character
        productmanagement.deleteProduct(id); // Delete product by ID
    }

    // Method to place a new order
    private static void placeOrder(Scanner sc, OrderManagement ordermanagement) {
        System.out.println("Welcome! Please enter the details to place your order");
        System.out.print("Enter your Customer ID: ");
        int customerId = sc.nextInt();
        System.out.print("Enter Product ID to place order: ");
        int productId  = sc.nextInt();
        System.out.print("Enter the quantity to be placed: ");
        int quantity  = sc.nextInt();
        ordermanagement.placeOrder(customerId, productId, quantity); // Place the order with customer ID, product ID, and quantity
    }

    // Method to view order details
    private static void viewOrder(Scanner sc, OrderManagement ordermanagement) throws OrderNotFoundException {
        System.out.print("Enter the Order ID to view: ");
        int id = sc.nextInt();
        sc.nextLine(); // Consume the newline character
        ordermanagement.viewOrder(id); // View order by ID
    }

    // Method to update order status
    private static void updateOrder(Scanner sc, OrderManagement ordermanagement) {
        System.out.print("Enter the Order ID to update the status: ");
        int id = sc.nextInt();
        System.out.println("Choose the status to be updated:");
        String status = null;
        System.out.println("Enter 1 to update it to 'pending'");
        System.out.println("Enter 2 to update it to 'confirmed'");
        int opt = sc.nextInt();
        if (opt == 1) {
            status = "pending";
            ordermanagement.updateOrderStatus(id, status); // Update order status to pending
        } else if (opt == 2) {
            status = "confirmed";
            ordermanagement.updateOrderStatus(id, status); // Update order status to confirmed
        } else {
            System.out.println("Invalid input! Please try again.");
        }
    }

    // Method to cancel an order
    private static void cancelOrder(Scanner sc, OrderManagement ordermanagement) {
        System.out.print("Enter the Order ID to cancel: ");
        int id = sc.nextInt();
        sc.nextLine(); // Consume the newline character
        ordermanagement.cancelOrder(id); // Cancel order by ID
    }
}
