## Getting Started

Welcome to the Customer Order Management System! This application is designed to assess proficiency in Core Java, MySQL, and JDBC by simulating a customer order management system for a retail business. The application allows users to manage customers, orders, and products through a menu-based console interface.

## Folder Structure

The workspace contains two folders by default:

- `src`: Contains the source code for the application.
- `lib`: Contains any dependencies or external libraries used in the project.

Compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Prerequisites

Before you begin, ensure you have the following installed on your machine:

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html) (version 8 or higher)
- [MySQL Server](https://dev.mysql.com/downloads/mysql/)
- [Visual Studio Code](https://code.visualstudio.com/) with the Java Extension Pack

## Setting Up the Database

1. **Start MySQL Server**: Ensure your MySQL server is running.

2. **Create the Database**: 
    - Open your MySQL client and run the following command to create the database:
      ```sql
      CREATE DATABASE CUSTOMER_DATABASE;
      ```

3. **Create Tables**: Use the following SQL scripts to create the required tables:

   - `Customer` Table:
     ```sql
     CREATE TABLE Customer (
         customer_id INT AUTO_INCREMENT PRIMARY KEY,
         name VARCHAR(255) NOT NULL,
         email VARCHAR(255) UNIQUE NOT NULL,
         phone VARCHAR(15) NOT NULL,
         address TEXT
     );
     ```

   - `Product` Table:
     ```sql
     CREATE TABLE Product (
         product_id INT AUTO_INCREMENT PRIMARY KEY,
         name VARCHAR(255) NOT NULL,
         category VARCHAR(255),
         price DECIMAL(10, 2),
         stock_quantity INT
     );
     ```

   - `Order` Table:
     ```sql
     CREATE TABLE `Order` (
         order_id INT AUTO_INCREMENT PRIMARY KEY,
         customer_id INT,
         order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
         status VARCHAR(50),
         FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
     );
     ```

   - `Order_Item` Table:
     ```sql
     CREATE TABLE Order_Item (
         order_item_id INT AUTO_INCREMENT PRIMARY KEY,
         order_id INT,
         product_id INT,
         quantity INT,
         price DECIMAL(10, 2),
         FOREIGN KEY (order_id) REFERENCES `Order`(order_id),
         FOREIGN KEY (product_id) REFERENCES Product(product_id)
     );
     ```

## Running the Application

1. **Open the Project in VS Code**:
   - Open the project folder in Visual Studio Code.
   
2. **Configure Database Connection**:
   - Ensure the database connection parameters (`url`, `username`, `password`) in the Java classes are correct.

3. **Compile the Project**:
   - Open the terminal in VS Code.
   - Navigate to the `src` directory:
     ```bash
     cd src
     ```
   - Compile the project using:
     ```bash
     javac -d ../bin *.java
     ```

4. **Run the Application**:
   - Navigate to the `bin` directory:
     ```bash
     cd ../bin
     ```
   - Run the application using:
     ```bash
     java Main
     ```
     
## Using the Application

Once the application is running, you will be presented with a menu to manage customers, products, and orders. Here are some operations you can perform:

- **Manage Customers**:
  - Add a new customer
  - View customer details
  - Update customer information
  - Delete a customer

- **Manage Products**:
  - Add a new product
  - View product details
  - Update product information
  - Delete a product

- **Manage Orders**:
  - Place a new order
  - View order details
  - Update order status
  - Cancel an order

Follow the on-screen prompts to interact with the system.

## Dependency Management

The `JAVA PROJECTS` view in Visual Studio Code allows you to manage your dependencies easily. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## Closing the Application

To safely close the application, use the menu option to exit. The database connection will be closed automatically.

## Troubleshooting

If you encounter any issues:

- Ensure your MySQL server is running and accessible.
- Double-check the database connection parameters in your Java code.
- Ensure that the necessary tables have been created in the `CUSTOMER_DATABASE`.
