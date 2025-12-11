<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.tap.model.Menu, com.tap.model.Restaurant, com.tap.model.Cart, com.tap.model.CartItem" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menu - Food Delivery</title>
    <link rel="stylesheet" href="style.css">
    <style>
        .quantity-control {
            display: inline-flex;
            align-items: center;
            background: white;
            border: 1px solid #d4d5d9;
            border-radius: 8px;
            padding: 0;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }
        
        .quantity-btn {
            background: transparent;
            border: none;
            color: #60b246;
            font-size: 22px;
            font-weight: 700;
            width: 40px;
            height: 40px;
            cursor: pointer;
            transition: all 0.2s;
        }
        
        .quantity-btn:hover {
            background: #f0f0f0;
        }
        
        .quantity-number {
            color: #60b246;
            font-weight: 700;
            font-size: 16px;
            min-width: 40px;
            text-align: center;
        }
        
        .add-btn {
            background: white;
            color: #60b246;
            border: 1px solid #60b246;
            padding: 10px 30px;
            border-radius: 8px;
            font-weight: 600;
            font-size: 14px;
            cursor: pointer;
            transition: all 0.3s;
            text-transform: uppercase;
        }
        
        .add-btn:hover {
            background: #60b246;
            color: white;
            box-shadow: 0 4px 12px rgba(96, 178, 70, 0.3);
        }
        
        /* Zomato-style added notification */
        .item-added-notification {
            background: #60b246;
            color: white;
            padding: 8px 16px;
            border-radius: 8px;
            font-weight: 600;
            font-size: 14px;
            animation: slideIn 0.3s ease-out;
        }
        
        @keyframes slideIn {
            from {
                opacity: 0;
                transform: scale(0.8);
            }
            to {
                opacity: 1;
                transform: scale(1);
            }
        }
        
        /* Loading state for buttons */
        .btn-loading {
            opacity: 0.7;
            pointer-events: none;
        }
        
        /* Zomato-style modal */
        .modal-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 2000;
            animation: fadeIn 0.3s ease-out;
        }
        
        .modal-content {
            background: white;
            border-radius: 12px;
            padding: 30px;
            max-width: 400px;
            width: 90%;
            text-align: center;
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
            animation: slideUp 0.3s ease-out;
        }
        
        .modal-item-image {
            width: 120px;
            height: 120px;
            border-radius: 12px;
            object-fit: cover;
            margin: 0 auto 20px;
            display: block;
        }
        
        .modal-item-name {
            font-size: 20px;
            font-weight: 700;
            margin-bottom: 8px;
            color: #333;
        }
        
        .modal-item-price {
            font-size: 18px;
            color: #60b246;
            font-weight: 600;
            margin-bottom: 25px;
        }
        
        .modal-quantity-control {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 20px;
            margin-bottom: 25px;
        }
        
        .modal-qty-btn {
            background: #60b246;
            color: white;
            border: none;
            width: 45px;
            height: 45px;
            border-radius: 50%;
            font-size: 24px;
            font-weight: 700;
            cursor: pointer;
            transition: all 0.2s;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        
        .modal-qty-btn:hover {
            background: #4a9c35;
            transform: scale(1.1);
        }
        
        .modal-qty-btn:disabled {
            background: #ccc;
            cursor: not-allowed;
            transform: none;
        }
        
        .modal-quantity {
            font-size: 24px;
            font-weight: 700;
            color: #333;
            min-width: 40px;
        }
        
        .modal-buttons {
            display: flex;
            gap: 15px;
            justify-content: center;
        }
        
        .modal-btn {
            padding: 12px 25px;
            border: none;
            border-radius: 8px;
            font-weight: 600;
            font-size: 16px;
            cursor: pointer;
            transition: all 0.3s;
        }
        
        .modal-btn-secondary {
            background: #f0f0f0;
            color: #666;
        }
        
        .modal-btn-secondary:hover {
            background: #e0e0e0;
        }
        
        .modal-btn-primary {
            background: #60b246;
            color: white;
            min-width: 150px;
        }
        
        .modal-btn-primary:hover {
            background: #4a9c35;
            box-shadow: 0 4px 12px rgba(96, 178, 70, 0.3);
        }
        
        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }
        
        @keyframes slideUp {
            from {
                opacity: 0;
                transform: translateY(30px) scale(0.9);
            }
            to {
                opacity: 1;
                transform: translateY(0) scale(1);
            }
        }
    </style>
</head>
<body>
    <header>
        <nav>
            <a href="home" class="logo">🍔 Foodie</a>
            <ul class="nav-links">
                <li><a href="home">Home</a></li>
                <li><a href="orderhistory.html">Orders</a></li>
                <li><a href="cart">Cart</a></li>
                <li><a href="signin.html">Sign In</a></li>
            </ul>
        </nav>
    </header>

    <div class="container">
        <div class="menu-container">
            <%
            Restaurant restaurant = (Restaurant) request.getAttribute("restaurant");
            if (restaurant != null) {
            %>
            <div class="menu-header">
                <h1><%= restaurant.getRestaurantName() %></h1>
                <p style="color: #666; margin-bottom: 10px;"><%= restaurant.getCuisine() %></p>
                <div style="display: flex; gap: 20px; align-items: center;">
                    <span class="rating"><%= restaurant.getRating() %> ⭐</span>
                    <span style="color: #666;"><%= restaurant.getDeliveryTime() %></span>
                </div>
            </div>
            <% } %>

            <div class="menu-category">
                <h2>Menu Items</h2>
                <%
                // Get current cart from session
                Cart currentCart = (Cart) session.getAttribute("cart");
                
                List<Menu> menuItems = (List<Menu>) request.getAttribute("menus");
                if (menuItems != null && !menuItems.isEmpty()) {
                    for (Menu item : menuItems) {
                        // Check if this item is in the cart
                        CartItem cartItem = null;
                        int itemQuantity = 0;
                        if (currentCart != null && !currentCart.isEmpty()) {
                            cartItem = currentCart.getItems().get(item.getMenuId());
                            if (cartItem != null) {
                                itemQuantity = cartItem.getQuantity();
                            }
                        }
                %>
                <div class="menu-item">
                    <img src="<%= item.getImageUrl() %>" alt="<%= item.getMenuName() %>"
                         style="width: 100px; height: 100px; object-fit: cover; border-radius: 8px; margin-right: 15px;">
                    <div class="item-info">
                        <div class="item-name"><%= item.getMenuName() %></div>
                        <div class="item-description"><%= item.getDescription() %></div>
                        <div class="item-price">₹<%= String.format("%.2f", item.getPrice()) %></div>
                    </div>
                    <% if (item.isAvailable()) { %>
                    <div id="cart-btn-<%= item.getMenuId() %>" 
                         data-menu-id="<%= item.getMenuId() %>"
                         data-menu-name="<%= item.getMenuName().replace("\"", "&quot;") %>"
                         data-menu-price="<%= item.getPrice() %>">
                        
                        <% if (itemQuantity > 0) { %>
                            <!-- Show quantity controls if item is in cart -->
                            <div class="quantity-control">
                                <form action="cart" method="post" style="display: inline;">
                                    <input type="hidden" name="restaurantId" value="<%= request.getParameter("restaurantId") %>">
                                    <input type="hidden" name="itemId" value="<%= item.getMenuId() %>">
                                    <input type="hidden" name="quantity" value="<%= itemQuantity - 1 %>">
                                    <input type="hidden" name="action" value="update">
                                    <button type="submit" class="quantity-btn">−</button>
                                </form>
                                <span class="quantity-number"><%= itemQuantity %></span>
                                <form action="cart" method="post" style="display: inline;">
                                    <input type="hidden" name="restaurantId" value="<%= request.getParameter("restaurantId") %>">
                                    <input type="hidden" name="itemId" value="<%= item.getMenuId() %>">
                                    <input type="hidden" name="quantity" value="<%= itemQuantity + 1 %>">
                                    <input type="hidden" name="action" value="update">
                                    <button type="submit" class="quantity-btn">+</button>
                                </form>
                            </div>
                        <% } else { %>
                            <!-- Show ADD button if item is not in cart -->
                            <button class="add-btn" 
                                    data-item-id="<%= item.getMenuId() %>"
                                    data-item-name="<%= item.getMenuName().replace("\"", "&quot;") %>"
                                    data-item-price="<%= item.getPrice() %>"
                                    data-item-image="<%= item.getImageUrl() %>"
                                    onclick="showAddItemModalFromButton(this)">
                                ADD
                            </button>
                        <% } %>
                    </div>
                    <% } else { %>
                    <button class="add-btn" disabled style="background: #ccc; border-color: #ccc; cursor: not-allowed;">
                        Unavailable
                    </button>
                    <% } %>
                </div>
                <%
                    }
                } else {
                %>
                <p style="text-align: center; color: #666; padding: 40px;">No menu items available for this restaurant.</p>
                <%
                }
                %>
            </div>
        </div>
    </div>



    <!-- Cart Summary Button - Fixed at Bottom -->
    <%
    // Calculate cart totals for display
    int totalCartItems = 0;
    double totalCartAmount = 0.0;
    if (currentCart != null && !currentCart.isEmpty()) {
        totalCartItems = currentCart.getTotalItems();
        totalCartAmount = currentCart.getTotalAmount();
    }
    %>
    
    <% if (totalCartItems > 0) { %>
    <div id="cart-summary" style="position: fixed; bottom: 0; left: 0; right: 0; background: white; padding: 15px 20px; box-shadow: 0 -4px 12px rgba(0,0,0,0.1); z-index: 1000; text-align: center;">
        <button onclick="window.location.href='cart'" style="background: #60b246; color: white; border: none; padding: 18px 30px; border-radius: 8px; font-size: 18px; font-weight: 600; cursor: pointer; box-shadow: 0 4px 12px rgba(96, 178, 70, 0.3); transition: all 0.3s; min-width: 400px;">
            <%= totalCartItems %> Item(s) &nbsp;|&nbsp; ₹<%= String.format("%.2f", totalCartAmount) %> &nbsp;&nbsp;→ View Cart
        </button>
    </div>
    <% } %>

    <!-- Zomato-style Add Item Modal -->
    <div id="addItemModal" class="modal-overlay" style="display: none;">
        <div class="modal-content">
            <img id="modalItemImage" src="" alt="" class="modal-item-image">
            <div id="modalItemName" class="modal-item-name"></div>
            <div id="modalItemPrice" class="modal-item-price"></div>
            
            <div class="modal-quantity-control">
                <button type="button" class="modal-qty-btn" id="decreaseBtn" onclick="updateModalQuantity(-1)">−</button>
                <span id="modalQuantity" class="modal-quantity">1</span>
                <button type="button" class="modal-qty-btn" id="increaseBtn" onclick="updateModalQuantity(1)">+</button>
            </div>
            
            <div class="modal-buttons">
                <button type="button" class="modal-btn modal-btn-secondary" onclick="closeModal()">Cancel</button>
                <button type="button" class="modal-btn modal-btn-primary" onclick="addToCartAndRedirect()">
                    Add to Cart
                </button>
            </div>
        </div>
    </div>

    <script>
        console.log('Menu page loaded with Zomato-style modal functionality');
        
        let currentModalItem = {
            id: null,
            name: '',
            price: 0,
            image: '',
            quantity: 1
        };
        
        function showAddItemModalFromButton(button) {
            const itemId = button.getAttribute('data-item-id');
            const itemName = button.getAttribute('data-item-name');
            const itemPrice = parseFloat(button.getAttribute('data-item-price'));
            const itemImage = button.getAttribute('data-item-image');
            
            showAddItemModal(itemId, itemName, itemPrice, itemImage);
        }
        
        function showAddItemModal(itemId, itemName, itemPrice, itemImage) {
            currentModalItem = {
                id: itemId,
                name: itemName,
                price: itemPrice,
                image: itemImage,
                quantity: 1
            };
            
            // Update modal content
            document.getElementById('modalItemImage').src = itemImage;
            document.getElementById('modalItemImage').alt = itemName;
            document.getElementById('modalItemName').textContent = itemName;
            document.getElementById('modalItemPrice').textContent = '₹' + itemPrice.toFixed(2);
            document.getElementById('modalQuantity').textContent = '1';
            
            // Reset quantity to 1
            updateModalQuantityDisplay();
            
            // Show modal
            document.getElementById('addItemModal').style.display = 'flex';
            document.body.style.overflow = 'hidden'; // Prevent background scrolling
        }
        
        function closeModal() {
            document.getElementById('addItemModal').style.display = 'none';
            document.body.style.overflow = 'auto'; // Restore scrolling
        }
        
        function updateModalQuantity(delta) {
            currentModalItem.quantity += delta;
            
            if (currentModalItem.quantity < 1) {
                currentModalItem.quantity = 1;
            }
            
            updateModalQuantityDisplay();
        }
        
        function updateModalQuantityDisplay() {
            document.getElementById('modalQuantity').textContent = currentModalItem.quantity;
            
            // Update decrease button state
            const decreaseBtn = document.getElementById('decreaseBtn');
            decreaseBtn.disabled = currentModalItem.quantity <= 1;
        }
        
        function addToCartAndRedirect() {
            // Validate data before submission
            const restaurantId = '<%= request.getParameter("restaurantId") %>';
            
            if (!restaurantId || restaurantId === 'null') {
                alert('Error: Restaurant ID is missing. Please refresh the page and try again.');
                return;
            }
            
            if (!currentModalItem || !currentModalItem.id) {
                alert('Error: Item information is missing. Please try again.');
                return;
            }
            
            if (!currentModalItem.quantity || currentModalItem.quantity < 1 || currentModalItem.quantity > 10) {
                alert('Error: Please select a valid quantity (1-10).');
                return;
            }
            
            // Create and submit form to add item to cart
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = 'cart';
            
            // Add hidden inputs with validation
            const inputs = [
                { name: 'restaurantId', value: restaurantId },
                { name: 'itemId', value: currentModalItem.id },
                { name: 'quantity', value: currentModalItem.quantity },
                { name: 'action', value: 'add' }
            ];
            
            inputs.forEach(input => {
                const hiddenInput = document.createElement('input');
                hiddenInput.type = 'hidden';
                hiddenInput.name = input.name;
                hiddenInput.value = input.value;
                form.appendChild(hiddenInput);
            });
            
            // Submit form
            document.body.appendChild(form);
            form.submit();
        }
        
        // Close modal when clicking outside
        document.addEventListener('click', function(event) {
            const modal = document.getElementById('addItemModal');
            if (event.target === modal) {
                closeModal();
            }
        });
        
        // Close modal with Escape key
        document.addEventListener('keydown', function(event) {
            if (event.key === 'Escape') {
                closeModal();
            }
        });
    </script>
</body>
</html>
