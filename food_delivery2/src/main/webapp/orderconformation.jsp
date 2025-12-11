<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmation - Food Delivery</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 600px;
            margin: 0 auto;
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.1);
            text-align: center;
        }
        .success-icon {
            font-size: 80px;
            color: #4caf50;
            margin-bottom: 20px;
        }
        h1 {
            color: #333;
            margin-bottom: 10px;
        }
        .order-details {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            margin: 20px 0;
            text-align: left;
        }
        .detail-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
        }
        .detail-row:last-child {
            border-bottom: none;
            font-weight: bold;
            font-size: 18px;
            color: #4caf50;
        }
        .action-btn {
            background: #60b246;
            color: white;
            padding: 12px 24px;
            border: none;
            border-radius: 6px;
            text-decoration: none;
            display: inline-block;
            margin: 10px;
            font-size: 16px;
        }
        .action-btn:hover {
            background: #4a9c35;
        }
        .secondary-btn {
            background: #6c757d;
        }
        .secondary-btn:hover {
            background: #545b62;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="success-icon">✅</div>
        <h1>Order Placed Successfully!</h1>
        <p style="color: #666; font-size: 18px;">Thank you for your order. We're preparing your delicious meal!</p>
        
        <%
        String orderIdParam = request.getParameter("orderId");
        Object lastOrderId = session.getAttribute("lastOrderId");
        Object lastOrderTotal = session.getAttribute("lastOrderTotal");
        
        int orderId = 0;
        double orderTotal = 0.0;
        
        if (orderIdParam != null) {
            try {
                orderId = Integer.parseInt(orderIdParam);
            } catch (NumberFormatException e) {
                // Use session data as fallback
            }
        }
        
        if (lastOrderId != null) {
            orderId = (Integer) lastOrderId;
        }
        
        if (lastOrderTotal != null) {
            orderTotal = (Double) lastOrderTotal;
        }
        %>
        
        <div class="order-details">
            <div class="detail-row">
                <span>Order ID:</span>
                <span>#<%= orderId > 0 ? orderId : "N/A" %></span>
            </div>
            <div class="detail-row">
                <span>Status:</span>
                <span style="color: #ff9800; font-weight: bold;">Pending</span>
            </div>
            <div class="detail-row">
                <span>Estimated Delivery:</span>
                <span>30-45 minutes</span>
            </div>
            <% if (orderTotal > 0) { %>
            <div class="detail-row">
                <span>Total Amount:</span>
                <span>₹<%= String.format("%.2f", orderTotal) %></span>
            </div>
            <% } %>
        </div>
        
        <p style="color: #666;">
            You will receive SMS updates about your order status. 
            <br>You can track your order in the Orders section.
        </p>
        
        <div style="margin-top: 30px;">
            <a href="home" class="action-btn">Order Again</a>
            <a href="orderhistory.html" class="action-btn secondary-btn">View Orders</a>
        </div>
        
        <%
        // Clean up session attributes
        session.removeAttribute("lastOrderId");
        session.removeAttribute("lastOrderTotal");
        %>
    </div>
</body>
</html>