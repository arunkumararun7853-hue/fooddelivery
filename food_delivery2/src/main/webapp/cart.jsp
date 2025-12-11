<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map, com.tap.model.Cart, com.tap.model.CartItem" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Cart - Food Delivery</title>
    <link rel="stylesheet" href="style.css">
    <style>
        .cart-container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        .cart-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 15px;
            border: 1px solid #ddd;
            margin-bottom: 10px;
            border-radius: 8px;
        }
        .item-details h3 {
            margin: 0 0 5px 0;
            color: #333;
        }
        .item-price {
            color: #666;
            font-size: 14px;
        }
        .quantity-controls {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .qty-btn {
            background: #60b246;
            color: white;
            border: none;
            width: 30px;
            height: 30px;
            border-radius: 50%;
            cursor: pointer;
        }
        .total-section {
            background: #f8f8f8;
            padding: 20px;
            border-radius: 8px;
            margin-top: 20px;
            text-align: center;
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
        }
        .clear-btn {
            background: #ff4444;
            color: white;
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <header>
        <nav>
            <a href="home" class="logo">🍔 Foodie</a>
            <ul class="nav-links">
                <li><a href="home">Home</a></li>
                <li><a href="cart">Cart</a></li>
                <li><a href="signin.html">Sign In</a></li>
            </ul>
        </nav>
    </header>

    <div class="cart-container">
        <h1>Your Cart</h1>
        
        <%
        Cart cart = (Cart) request.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        
        if (cart != null && !cart.isEmpty()) {
        %>
        
        <div class="cart-items">
            <%
            for (CartItem item : cart.getItems().values()) {
            %>
            <div class="cart-item">
                <div class="item-details">
                    <h3><%= item.getName() %></h3>
                    <div class="item-price">₹<%= String.format("%.2f", item.getPrice()) %> each</div>
                </div>
                
                <div class="quantity-controls">
                    <form action="cart" method="post" style="display: inline;">
                        <input type="hidden" name="restaurantId" value="<%= cart.getRestaurantId() %>">
                        <input type="hidden" name="itemId" value="<%= item.getMenuId() %>">
                        <input type="hidden" name="quantity" value="<%= item.getQuantity() - 1 %>">
                        <input type="hidden" name="action" value="update">
                        <button type="submit" class="qty-btn">-</button>
                    </form>
                    
                    <span><%= item.getQuantity() %></span>
                    
                    <form action="cart" method="post" style="display: inline;">
                        <input type="hidden" name="restaurantId" value="<%= cart.getRestaurantId() %>">
                        <input type="hidden" name="itemId" value="<%= item.getMenuId() %>">
                        <input type="hidden" name="quantity" value="<%= item.getQuantity() + 1 %>">
                        <input type="hidden" name="action" value="update">
                        <button type="submit" class="qty-btn">+</button>
                    </form>
                </div>
                
                <div>
                    <div><strong>₹<%= String.format("%.2f", item.getSubTotal()) %></strong></div>
                    <form action="cart" method="post" style="display: inline;">
                        <input type="hidden" name="restaurantId" value="<%= cart.getRestaurantId() %>">
                        <input type="hidden" name="itemId" value="<%= item.getMenuId() %>">
                        <input type="hidden" name="action" value="remove">
                        <button type="submit" style="background: none; border: none; color: #ff4444; cursor: pointer;">Remove</button>
                    </form>
                </div>
            </div>
            <%
            }
            %>
        </div>
        
        <div class="total-section">
            <h2>Total Items: <%= cart.getTotalItems() %></h2>
            <h2>Total Amount: ₹<%= String.format("%.2f", cart.getTotalAmount()) %></h2>
            
            <div>
                <a href="menu?restaurantId=<%= cart.getRestaurantId() %>" class="action-btn">Add More Items</a>
                <%
                Object userId = session.getAttribute("userId");
                if (userId != null) {
                %>
                    <a href="checkout.jsp" class="action-btn">Proceed to Checkout</a>
                <%
                } else {
                %>
                    <a href="signin.html" class="action-btn">Sign In to Checkout</a>
                <%
                }
                %>
            </div>
        </div>
        
        <%
        } else {
        %>
        <div style="text-align: center; padding: 60px 20px;">
            <h2>Your cart is empty</h2>
            <p>Add some delicious items to get started!</p>
            <a href="home" class="action-btn">Browse Restaurants</a>
        </div>
        <%
        }
        %>
        
        <!-- Debug: Clear Cart Button -->
        <div style="text-align: center; margin-top: 20px;">
            <form action="cart" method="post" style="display: inline;">
                <input type="hidden" name="action" value="clear">
                <button type="submit" class="clear-btn">Clear Cart</button>
            </form>
        </div>
        
    </div>
</body>
</html>