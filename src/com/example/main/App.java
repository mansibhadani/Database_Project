package com.example.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class App {
    private static final String URL = "jdbc:postgresql://localhost:5432/HW_Assignment2";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static void main(String[] args) {
        // SQL statements
        String updateProduct = "UPDATE product SET prodid = ? WHERE prodid = ?";
        String updateProductStock = "UPDATE stock SET prodid = ? WHERE prodid = ?";
        String insertProduct = "INSERT INTO product (prodid, pname, price) VALUES (?, ?, ?)";
        String insertStock = "INSERT INTO stock (prodid, depid, quantity) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Start the transaction
            conn.setAutoCommit(false);

            try (
                PreparedStatement stmt1 = conn.prepareStatement(updateProduct);
                PreparedStatement stmt2 = conn.prepareStatement(updateProductStock);
                PreparedStatement stmt3 = conn.prepareStatement(insertProduct);
                PreparedStatement stmt4 = conn.prepareStatement(insertStock)
            ) {
                // 1. Update product p1 to pp1 in Product
                stmt1.setString(1, "pp1");
                stmt1.setString(2, "p1");
                stmt1.executeUpdate();

                // 2. Update product p1 to pp1 in Stock
                stmt2.setString(1, "pp1");
                stmt2.setString(2, "p1");
                stmt2.executeUpdate();

                // 3. Add a product (p100, cd, 5) in Product
                stmt3.setString(1, "p100");
                stmt3.setString(2, "cd");
                stmt3.setInt(3, 5);
                stmt3.executeUpdate();

                // 4. Add (p100, d2, 50) in Stock
                stmt4.setString(1, "p100");
                stmt4.setString(2, "d2");
                stmt4.setInt(3, 50);
                stmt4.executeUpdate();

                // Commit the transaction
                conn.commit();
                System.out.println("Transaction committed successfully.");
            } catch (SQLException e) {
                // Rollback the transaction in case of an error
                conn.rollback();
                System.err.println("Transaction rolled back due to error: " + e.getMessage());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
