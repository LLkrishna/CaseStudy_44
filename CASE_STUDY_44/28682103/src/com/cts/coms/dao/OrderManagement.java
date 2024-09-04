package com.cts.coms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cts.coms.exception.CustomerNotFoundException;
import com.cts.coms.exception.OrderNotFoundException;
import com.cts.coms.util.DataBaseConnection;


public class OrderManagement {
    private Connection connection;

    // Constructor to initialize the database connection
    public OrderManagement() {
        connection = DataBaseConnection.getConnection(); // Get the connection from DataBaseConnection
    }

    // Method to place a new order
 public int placeOrder(int customerId, int productId, int quantity) {
    String checkCustomerSql = "SELECT COUNT(*) FROM Customer WHERE customer_id = ?";
    String orderSql = "INSERT INTO `Order` (customer_id, order_date, status) VALUES (?, NOW(), 'pending')";
    String orderItemSql = "INSERT INTO `Order_Item` (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
    String updateProductStockSql = "UPDATE `Product` SET stock_quantity = stock_quantity - ? WHERE product_id = ?";
    String checkStockSql = "SELECT stock_quantity, price FROM `Product` WHERE product_id = ?";

    PreparedStatement checkCustomerStmt = null;
    PreparedStatement orderStmt = null;
    PreparedStatement orderItemStmt = null;
    PreparedStatement updateProductStockStmt = null;
    PreparedStatement checkStockStmt = null;
    ResultSet generatedKeys = null;
    ResultSet stockResult = null;
    ResultSet customerResult = null;

    try {
        // Establish a connection to the database
        
        // Check if the customer exists
        checkCustomerStmt = connection.prepareStatement(checkCustomerSql);
        checkCustomerStmt.setInt(1, customerId);
        customerResult = checkCustomerStmt.executeQuery();
        if (customerResult.next()) {
            int customerCount = customerResult.getInt(1);
            if (customerCount == 0) {
                System.out.println("Customer not found. Please check the customer ID.");
                return -1;
            }
        }

        // Check if sufficient stock is available
        checkStockStmt = connection.prepareStatement(checkStockSql);
        checkStockStmt.setInt(1, productId);
        stockResult = checkStockStmt.executeQuery();
        double price = 0;
        if (stockResult.next()) {
            int availableStock = stockResult.getInt("stock_quantity");
            price = stockResult.getDouble("price");
            if (availableStock < quantity) {
                System.out.println("Insufficient quantity in warehouse.");
                return -1;
            }
        }

        // Insert the order into the `Order` table
        orderStmt = connection.prepareStatement(orderSql, PreparedStatement.RETURN_GENERATED_KEYS);
        orderStmt.setInt(1, customerId);
        int rowsAffected = orderStmt.executeUpdate();

        if (rowsAffected > 0) {
            generatedKeys = orderStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);

                // Insert the order item into the `Order_Item` table
                orderItemStmt = connection.prepareStatement(orderItemSql);
                orderItemStmt.setInt(1, orderId);
                orderItemStmt.setInt(2, productId);
                orderItemStmt.setInt(3, quantity);
                orderItemStmt.setDouble(4, price * quantity);
                orderItemStmt.executeUpdate();

                // Update the product stock
                updateProductStockStmt = connection.prepareStatement(updateProductStockSql);
                updateProductStockStmt.setInt(1, quantity);
                updateProductStockStmt.setInt(2, productId);
                updateProductStockStmt.executeUpdate();

                System.out.println("Order placed successfully. Order ID: " + orderId);
                return orderId;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Close ResultSet and PreparedStatement resources
        try {
            if (generatedKeys != null) generatedKeys.close();
            if (orderStmt != null) orderStmt.close();
            if (orderItemStmt != null) orderItemStmt.close();
            if (updateProductStockStmt != null) updateProductStockStmt.close();
            if (checkStockStmt != null) checkStockStmt.close();
            if (stockResult != null) stockResult.close();
            if (customerResult != null) customerResult.close();
            if (checkCustomerStmt != null) checkCustomerStmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return -1;
}


    // Method to view order details by ID
    public void viewOrder(int orderId) throws OrderNotFoundException{
        String orderSQL = "SELECT * FROM `Order` WHERE order_id = ?";
        String orderItemSQL = "SELECT * FROM Order_Item WHERE order_id = ?";
        try (PreparedStatement orderStmt = connection.prepareStatement(orderSQL);
             PreparedStatement orderItemStmt = connection.prepareStatement(orderItemSQL)) {
            // Fetch order details
            orderStmt.setInt(1, orderId);
            ResultSet orderRs = orderStmt.executeQuery();
            if (orderRs.next()) {
                System.out.println("Order ID: " + orderRs.getInt("order_id"));
                System.out.println("Customer ID: " + orderRs.getInt("customer_id"));
                System.out.println("Order Date: " + orderRs.getTimestamp("order_date"));
                System.out.println("Status: " + orderRs.getString("status"));

                // Fetch order items
                orderItemStmt.setInt(1, orderId);
                ResultSet itemRs = orderItemStmt.executeQuery();
                System.out.println("Order Items:");
                while (itemRs.next()) {
                    System.out.println("  Product ID: " + itemRs.getInt("product_id"));
                    System.out.println("  Quantity: " + itemRs.getInt("quantity"));
                    System.out.println("  Price: $" + itemRs.getDouble("price"));
                    System.out.println();
                }
            } else {
                throw new OrderNotFoundException("Order not found with ID: " + orderId);
            }
        } 
         catch (OrderNotFoundException c){
            System.out.println("error "  +  c.getMessage());
        }catch (SQLException e) {
            // Handle any SQL exceptions that occur during the process
            e.printStackTrace();
        }
    }

    // Method to update order status
    public void updateOrderStatus(int orderId, String newStatus) {
        String getCurrentStatusSQL = "SELECT status FROM `Order` WHERE order_id = ?";
        String updateOrderStatusSQL = "UPDATE `Order` SET status = ? WHERE order_id = ?";
        String adjustStockDecreaseSQL = "UPDATE Product p JOIN Order_Item oi ON p.product_id = oi.product_id SET p.stock_quantity = p.stock_quantity - oi.quantity WHERE oi.order_id = ?";
        String adjustStockIncreaseSQL = "UPDATE Product p JOIN Order_Item oi ON p.product_id = oi.product_id SET p.stock_quantity = p.stock_quantity + oi.quantity WHERE oi.order_id = ?";
        
        try (PreparedStatement getCurrentStatusStmt = connection.prepareStatement(getCurrentStatusSQL);
             PreparedStatement updateOrderStatusStmt = connection.prepareStatement(updateOrderStatusSQL);
             PreparedStatement adjustStockDecreaseStmt = connection.prepareStatement(adjustStockDecreaseSQL);
             PreparedStatement adjustStockIncreaseStmt = connection.prepareStatement(adjustStockIncreaseSQL)) {
    
            // Begin transaction
            connection.setAutoCommit(false);
    
            // Get current order status
            getCurrentStatusStmt.setInt(1, orderId);
            ResultSet rs = getCurrentStatusStmt.executeQuery();
            if (rs.next()) {
                String currentStatus = rs.getString("status");
    
                // Update order status
                updateOrderStatusStmt.setString(1, newStatus);
                updateOrderStatusStmt.setInt(2, orderId);
                int rowsAffected = updateOrderStatusStmt.executeUpdate();
                if (rowsAffected > 0) {
                    // Adjust stock based on status change
                    if ("cancelled".equalsIgnoreCase(currentStatus) && "confirmed".equalsIgnoreCase(newStatus)) {
                        // Increase stock if changing from cancelled to confirmed
                        adjustStockIncreaseStmt.setInt(1, orderId);
                        adjustStockIncreaseStmt.executeUpdate();
                    } else if ("confirmed".equalsIgnoreCase(currentStatus) && "cancelled".equalsIgnoreCase(newStatus)) {
                        // Decrease stock if changing from confirmed to cancelled
                        adjustStockDecreaseStmt.setInt(1, orderId);
                        adjustStockDecreaseStmt.executeUpdate();
                    }
                    System.out.println("Order status updated successfully.");
                } else {
                    System.out.println("Order not found.");
                }
            } else {
                System.out.println("Order not found.");
            }
    
            // Commit transaction
            connection.commit();
        } catch (SQLException e) {
            try {
                // Rollback transaction on error
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                // Reset to default auto-commit behavior
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    



    // Method to cancel an order
    public void cancelOrder(int orderId) {
        String updateOrderStatusSQL = "UPDATE `Order` SET status = 'cancelled' WHERE order_id = ?";
        String restockSQL = "UPDATE Product p JOIN Order_Item oi ON p.product_id = oi.product_id SET p.stock_quantity = p.stock_quantity + oi.quantity WHERE oi.order_id = ?";
        
        try (PreparedStatement updateOrderStatusStmt = connection.prepareStatement(updateOrderStatusSQL);
             PreparedStatement restockStmt = connection.prepareStatement(restockSQL)) {
            // Begin transaction
            connection.setAutoCommit(false);
    
            // Update order status
            updateOrderStatusStmt.setInt(1, orderId);
            updateOrderStatusStmt.executeUpdate();
            
            // Restock products
            restockStmt.setInt(1, orderId);
            restockStmt.executeUpdate();
    
            // Commit transaction
            connection.commit();
            System.out.println("Order canceled and stock updated.");
        } catch (SQLException e) {
            try {
                // Rollback transaction on error
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                // Reset to default auto-commit behavior
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    

    // Close the database connection
    public void closeConnection() {
        DataBaseConnection.closeConnection(); // Close the connection using DataBaseConnection
    }
}
