<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map, com.tap.model.Cart, com.tap.model.CartItem" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout - Food Delivery</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <%
    // Check if user is logged in
    Object userId = session.getAttribute("userId");
    if (userId == null) {
        response.sendRedirect("signin.html");
        return;
    }
    
    // Get cart from session
    Cart cart = (Cart) session.getAttribute("cart");
    if (cart == null || cart.isEmpty()) {
        response.sendRedirect("cart");
        return;
    }
    %>
    
    <header>
        <nav>
            <a href="home" class="logo">🍔 Foodie</a>
            <ul class="nav-links">
                <li><a href="home">Home</a></li>
                <li><a href="orderhistory.html">Orders</a></li>
                <li><a href="cart">Cart</a></li>
                <li><span style="color: #60b246; font-weight: 600;">Hi, <%= session.getAttribute("userName") %></span></li>
                <li><a href="signin?action=logout">Logout</a></li>
            </ul>
        </nav>
    </header>

    <div class="container">
        <h1 style="margin-bottom: 30px; font-size: 32px;">Checkout</h1>
        
        <div style="display: grid; grid-template-columns: 2fr 1fr; gap: 30px;">
            <div class="form-container" style="max-width: 100%; margin: 0;">
                <h2 style="margin-bottom: 20px;">Delivery Details</h2>
                <form action="order" method="post">
                    <div class="checkout-form">
                        <div class="form-group">
                            <label>Full Name</label>
                            <input type="text" name="fullName" required placeholder="Enter your full name">
                        </div>
                        <div class="form-group">
                            <label>Phone Number</label>
                            <input type="tel" name="phoneNumber" required placeholder="Enter your phone number">
                        </div>
                        <div class="form-group full-width">
                            <label>Delivery Address</label>
                            <input type="text" name="address1" required placeholder="House/Flat No., Building Name">
                        </div>
                        <div class="form-group full-width">
                            <input type="text" name="address2" required placeholder="Street, Area, Locality">
                        </div>
                        <div class="form-group">
                            <label>City</label>
                            <input type="text" name="city" required placeholder="City">
                        </div>
                        <div class="form-group">
                            <label>Pincode</label>
                            <input type="text" name="pincode" required placeholder="Pincode">
                        </div>
                        <div class="form-group full-width">
                            <label>Payment Method</label>
                            <select name="paymentMethod" style="width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 6px; font-size: 16px;">
                                <option value="cod">Cash on Delivery</option>
                                <option value="card">Credit Card</option>
                                <option value="debit">Debit Card</option>
                                <option value="upi">UPI</option>
                                <option value="netbanking">Net Banking</option>
                            </select>
                        </div>
                    </div>
                    <button type="submit" class="submit-btn" style="margin-top: 20px;">Place Order</button>
                </form>
            </div>

            <div class="order-summary">
                <h2 style="margin-bottom: 20px;">Order Summary</h2>
                <div id="order-items">
                    <%
                    double subtotal = 0;
                    for (CartItem item : cart.getItems().values()) {
                        double itemTotal = item.getSubTotal();
                        subtotal += itemTotal;
                    %>
                    <div style="display: flex; justify-content: space-between; margin-bottom: 15px; padding-bottom: 15px; border-bottom: 1px solid #f0f0f0;">
                        <div>
                            <div style="font-weight: bold; margin-bottom: 5px;"><%= item.getName() %></div>
                            <div style="color: #666; font-size: 14px;">Qty: <%= item.getQuantity() %> × ₹<%= String.format("%.2f", item.getPrice()) %></div>
                        </div>
                        <div style="font-weight: bold; color: #ff6347;">₹<%= String.format("%.2f", itemTotal) %></div>
                    </div>
                    <%
                    }
                    
                    double deliveryFee = 40.0;
                    double tax = subtotal * 0.05;
                    double total = subtotal + deliveryFee + tax;
                    %>
                </div>
                <div class="cart-summary" style="margin-top: 20px; padding-top: 20px; border-top: 2px solid #f0f0f0;">
                    <div class="summary-row">
                        <span>Subtotal</span>
                        <span>₹<%= String.format("%.2f", subtotal) %></span>
                    </div>
                    <div class="summary-row">
                        <span>Delivery Fee</span>
                        <span>₹<%= String.format("%.2f", deliveryFee) %></span>
                    </div>
                    <div class="summary-row">
                        <span>Tax (5%)</span>
                        <span>₹<%= String.format("%.2f", tax) %></span>
                    </div>
                    <div class="summary-row total">
                        <span>Total</span>
                        <span>₹<%= String.format("%.2f", total) %></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>