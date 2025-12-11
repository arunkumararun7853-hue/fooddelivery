package com.tap.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // adjust URL/user/pass for your environment
    private static final String URL = "jdbc:mysql://localhost:3306/fooddeliveryapp";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Arun@2611";

    /**
     * Returns a live Connection or throws SQLException/runtime exception.
     * IMPORTANT: This never returns null; it throws on failure so callers don't NPE.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // optional but helpful when driver not present: immediate informative failure
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // driver missing is fatal — wrap as runtime so caller sees it immediately
            throw new RuntimeException("MySQL JDBC driver not found on classpath. Add mysql-connector-java to WEB-INF/lib or your project dependencies.", e);
        }

        // Let DriverManager throw SQLException if connection fails (no swallowing)
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // quick test main (run locally to diagnose BEFORE deploying to tomcat)
    public static void main(String[] args) {
        try (Connection c = getConnection()) {
            System.out.println("Connected OK to: " + c.getMetaData().getURL());
        } catch (Throwable t) {
            System.err.println("Failed to get DB connection:");
            t.printStackTrace(System.err);
        }
    }
}
