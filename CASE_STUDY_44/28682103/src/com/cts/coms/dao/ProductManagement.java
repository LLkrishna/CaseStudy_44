package com.cts.coms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.cts.coms.exception.ProductNotFoundException;
import com.cts.coms.util.DataBaseConnection;

public class ProductManagement {
    private Connection connection;

    // Constructor to initialize the database connection
    public ProductManagement() {
        connection = DataBaseConnection.getConnection(); // Get the connection from DataBaseConnection
    }

    // Method to add a new product
    public void addProduct(String name, String category, double price, int stockQuantity) {
        String sql = "INSERT INTO Product (name, category, price, stock_quantity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, category);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, stockQuantity);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Product added successfully. Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to view product details by ID
    public void viewProduct(int productId) throws ProductNotFoundException {
        String sql = "SELECT * FROM Product WHERE product_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Product ID: " + rs.getInt("product_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Category: " + rs.getString("category"));
                System.out.println("Price: $" + rs.getDouble("price"));
                System.out.println("Stock Quantity: " + rs.getInt("stock_quantity"));
            } else {
                 throw new ProductNotFoundException("Product not found with ID: " + productId);
            }
         } catch (ProductNotFoundException c){
            System.out.println("error "  +  c.getMessage());
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update product information
    public void updateProduct(int productId, String name, String category, double price, int stockQuantity) {
        String sql = "UPDATE Product SET name = ?, category = ?, price = ?, stock_quantity = ? WHERE product_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, category);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, stockQuantity);
            pstmt.setInt(5, productId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product information updated successfully.");
            } else {
                System.out.println("Product not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a product by ID
    public void deleteProduct(int productId) {
        String sql = "DELETE FROM Product WHERE product_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("Product not found.");
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
