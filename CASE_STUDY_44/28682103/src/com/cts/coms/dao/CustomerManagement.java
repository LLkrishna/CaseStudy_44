package com.cts.coms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.cts.coms.exception.CustomerNotFoundException;

import com.cts.coms.util.DataBaseConnection;

public class CustomerManagement {
    private Connection connection;

    // Constructor to initialize the database connection
    public CustomerManagement() {
        connection = DataBaseConnection.getConnection(); // Get the connection from DataBaseConnection
    }

    // Method to add a new customer
    public void addCustomer(String name, String email, String phone, String address) {
        String sql = "INSERT INTO Customer (name, email, phone, address) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, address);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Customer added successfully. Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to view customer details by ID
   public void viewCustomer(int customerId) throws CustomerNotFoundException {
        String sql = "SELECT * FROM Customer WHERE customer_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Customer ID: " + rs.getInt("customer_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Phone: " + rs.getString("phone"));
                System.out.println("Address: " + rs.getString("address"));
            } else {
                throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
            }
        } catch (CustomerNotFoundException c){
            System.out.println("error "  +  c.getMessage());
        }
         catch (SQLException e) {
            e.getMessage();
        }
    }

    // Method to update customer information
    public void updateCustomer(int customerId, String name, String email, String phone, String address) {
        String sql = "UPDATE Customer SET name = ?, email = ?, phone = ?, address = ? WHERE customer_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, address);
            pstmt.setInt(5, customerId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer information updated successfully.");
            } else {
                System.out.println("Customer not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a customer by ID
    public void deleteCustomer(int customerId) {
        String sql = "DELETE FROM Customer WHERE customer_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer deleted successfully.");
            } else {
                System.out.println("Customer not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Close the database connection
    public void closeConnection() {
        DataBaseConnection.closeConnection(); // Close the connection using DataBaseConnection
    }
}
