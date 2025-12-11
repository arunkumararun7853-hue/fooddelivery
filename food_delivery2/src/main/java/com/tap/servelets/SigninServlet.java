package com.tap.servelets;



import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tap.utility.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/signin")
public class SigninServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println("=== SigninServlet doPost START ===");
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        System.out.println("Received email: " + email);
        System.out.println("Received password: " + password);
        
        // Basic validation
        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
            System.out.println("Validation failed: Empty email or password");
            response.sendRedirect("signin.html?error=empty");
            return;
        }
        
        System.out.println("Basic validation passed");
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            System.out.println("Attempting database connection...");
            // Get database connection
            conn = DBConnection.getConnection();
            System.out.println("Database connection successful");
            
            // Query to find user
            String sql = "SELECT user_id, name, email FROM user WHERE email = ? AND password = ?";
            System.out.println("Preparing SQL query: " + sql);
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email.trim());
            stmt.setString(2, password);
            
            System.out.println("Executing query with email: " + email.trim() + ", password: " + password);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                System.out.println("User found in database!");
                // User found - create session
                HttpSession session = request.getSession();
                session.setAttribute("userId", rs.getInt("user_id"));
                session.setAttribute("userName", rs.getString("name"));
                session.setAttribute("userEmail", rs.getString("email"));
                
                System.out.println("User session created for: " + rs.getString("name"));
                
                // Check cart and redirect appropriately
                Object cartObj = session.getAttribute("cart");
                if (cartObj != null) {
                    try {
                        com.tap.model.Cart cart = (com.tap.model.Cart) cartObj;
                        if (!cart.isEmpty()) {
                            System.out.println("Cart has items - redirecting to checkout");
                            response.sendRedirect("checkout.jsp");
                            return;
                        }
                    } catch (Exception e) {
                        System.out.println("Cart object issue: " + e.getMessage());
                    }
                }
                
                // No cart or empty cart - go to restaurants
                System.out.println("No cart or empty cart - redirecting to restaurants");
                response.sendRedirect("restaurants");
                
            } else {
                System.out.println("User NOT found in database with provided credentials");
                // User not found
                response.sendRedirect("signin.html?error=invalid");
            }
            
        } catch (SQLException e) {
            System.out.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("signin.html?error=system");
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
                System.out.println("Database resources closed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("=== SigninServlet doPost END ===");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if ("logout".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect("restaurants");
        } else {
            response.sendRedirect("signin.html");
        }
    }
}