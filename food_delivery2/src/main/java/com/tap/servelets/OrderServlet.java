package com.tap.servelets;



import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tap.model.Cart;
import com.tap.model.CartItem;
import com.tap.model.Order;
import com.tap.model.OrderItem;
import com.tap.utility.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println("=== OrderServlet doPost START ===");
        
        HttpSession session = request.getSession();
        
        // Check if user is logged in
        Object userIdObj = session.getAttribute("userId");
        if (userIdObj == null) {
            System.out.println("User not logged in - redirecting to signin");
            response.sendRedirect("signin.html");
            return;
        }
        
        int userId = (Integer) userIdObj;
        System.out.println("Processing order for userId: " + userId);
        
        // Get cart from session
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            System.out.println("Cart is empty - redirecting to cart");
            response.sendRedirect("cart");
            return;
        }
        
        System.out.println("Cart has " + cart.getTotalItems() + " items, total: ₹" + cart.getTotalAmount());
        
        // Get form parameters
        String fullName = request.getParameter("fullName");
        String phoneNumber = request.getParameter("phoneNumber");
        String address1 = request.getParameter("address1");
        String address2 = request.getParameter("address2");
        String city = request.getParameter("city");
        String pincode = request.getParameter("pincode");
        String paymentMethod = request.getParameter("paymentMethod");
        
        // Build full address
        String deliveryAddress = fullName + ", " + phoneNumber + ", " + address1 + ", " + address2 + ", " + city + " - " + pincode;
        
        System.out.println("Order details:");
        System.out.println("Name: " + fullName);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Payment: " + paymentMethod);
        System.out.println("Address: " + deliveryAddress);
        
        // Calculate total with fees and tax
        double subtotal = cart.getTotalAmount();
        double deliveryFee = 40.0;
        double tax = subtotal * 0.05;
        double totalAmount = subtotal + deliveryFee + tax;
        
        System.out.println("Order totals - Subtotal: ₹" + subtotal + ", Delivery: ₹" + deliveryFee + ", Tax: ₹" + tax + ", Total: ₹" + totalAmount);
        
        Connection conn = null;
        PreparedStatement orderStmt = null;
        PreparedStatement itemStmt = null;
        
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction
            
            System.out.println("Database connection established, starting transaction");
            
            // Insert order
            String orderSql = "INSERT INTO orders (order_date, restaurantId, userId, total_amount, payment_method, status, delivery_address) VALUES (NOW(), ?, ?, ?, ?, ?, ?)";
            orderStmt = conn.prepareStatement(orderSql, PreparedStatement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, cart.getRestaurantId());
            orderStmt.setInt(2, userId);
            orderStmt.setDouble(3, totalAmount);
            orderStmt.setString(4, paymentMethod);
            orderStmt.setString(5, "pending");
            orderStmt.setString(6, deliveryAddress);
            
            int orderResult = orderStmt.executeUpdate();
            System.out.println("Order inserted, rows affected: " + orderResult);
            
            // Get generated order ID
            ResultSet keys = orderStmt.getGeneratedKeys();
            int orderId = 0;
            if (keys.next()) {
                orderId = keys.getInt(1);
                System.out.println("Generated order ID: " + orderId);
            }
            
            // Insert order items
            String itemSql = "INSERT INTO orderitems (orderId, menuId, quantity, unit_price) VALUES (?, ?, ?, ?)";
            itemStmt = conn.prepareStatement(itemSql);
            
            int itemCount = 0;
            for (CartItem cartItem : cart.getItems().values()) {
                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, cartItem.getMenuId());
                itemStmt.setInt(3, cartItem.getQuantity());
                itemStmt.setDouble(4, cartItem.getPrice());
                itemStmt.addBatch();
                itemCount++;
                
                System.out.println("Added item to batch: " + cartItem.getName() + " x" + cartItem.getQuantity());
            }
            
            int[] itemResults = itemStmt.executeBatch();
            System.out.println("Order items inserted, batch results: " + itemResults.length);
            
            // Commit transaction
            conn.commit();
            System.out.println("Transaction committed successfully");
            
            // Clear cart after successful order
            session.removeAttribute("cart");
            System.out.println("Cart cleared from session");
            
            // Set order details for confirmation page
            session.setAttribute("lastOrderId", orderId);
            session.setAttribute("lastOrderTotal", totalAmount);
            
            System.out.println("=== OrderServlet SUCCESS - Redirecting to confirmation ===");
            response.sendRedirect("orderconformation.jsp?orderId=" + orderId);
            
        } catch (SQLException e) {
            System.out.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            
            try {
                if (conn != null) {
                    conn.rollback();
                    System.out.println("Transaction rolled back");
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            
            response.sendRedirect("checkout.jsp?error=order_failed");
            
        } finally {
            try {
                if (itemStmt != null) itemStmt.close();
                if (orderStmt != null) orderStmt.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
                System.out.println("Database resources closed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("=== OrderServlet doPost END ===");
    }
}