<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.tap.model.Restaurant" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Food Delivery - Restaurants</title>
    <link rel="stylesheet" href="style.css">
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

    <section class="hero" style="position: relative; min-height: 420px; display: flex; align-items: center; justify-content: center; background: linear-gradient(135deg, #ffecd2 0%, #fcb69f 100%); overflow: hidden;">
        <img src="https://images.unsplash.com/photo-1504674900247-0877df9cc836?auto=format&fit=crop&w=1200&q=80" alt="Delicious food" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; object-fit: cover; opacity: 0.45; z-index: 1;">
        <div style="position: relative; z-index: 2; text-align: center; width: 100%;">
            <h1 style="font-size: 3rem; font-weight: 700; color: #222; margin-bottom: 18px; text-shadow: 0 2px 16px rgba(255,255,255,0.7);">Craving Great Food?</h1>
            <p style="font-size: 1.35rem; color: #333; margin-bottom: 28px; text-shadow: 0 1px 8px rgba(255,255,255,0.5);">Discover top restaurants, order your favorites, and enjoy fast delivery at your doorstep.</p>
            <div class="search-bar" style="display: flex; justify-content: center; gap: 0; max-width: 480px; margin: 0 auto;">
                <input type="text" placeholder="Search for restaurants, cuisines, or dishes..." style="padding: 14px 18px; font-size: 1.1rem; border-radius: 32px 0 0 32px; border: none; outline: none; width: 70%; box-shadow: 0 2px 8px rgba(0,0,0,0.08);">
                <button style="padding: 14px 28px; font-size: 1.1rem; border-radius: 0 32px 32px 0; border: none; background: #ff7043; color: #fff; font-weight: 600; cursor: pointer; box-shadow: 0 2px 8px rgba(0,0,0,0.08);">Search</button>
            </div>
        </div>
    </section>

    <!-- Inspirational Food Container -->
    <section class="inspirational-food" style="margin: 48px auto 32px auto; max-width: 1100px;">
        <h2 style="font-size: 2.1rem; font-weight: 700; margin-bottom: 18px; color: #222;">Inspiration for your first order</h2>
        <div style="display: flex; gap: 24px; overflow-x: auto; padding-bottom: 8px;">
            <div style="min-width: 160px; text-align: center;">
                <img src="https://images.unsplash.com/photo-1513104890138-7c749659a591?auto=format&fit=crop&w=160&q=80" alt="Pizza" style="width: 120px; height: 120px; border-radius: 50%; object-fit: cover; box-shadow: 0 2px 12px rgba(0,0,0,0.08);">
                <div style="margin-top: 10px; font-weight: 600; color: #333;">Pizza</div>
            </div>
            <div style="min-width: 160px; text-align: center;">
                <img src="https://images.unsplash.com/photo-1568901346375-23c9450c58cd?auto=format&fit=crop&w=160&q=80" alt="Burger" style="width: 120px; height: 120px; border-radius: 50%; object-fit: cover; box-shadow: 0 2px 12px rgba(0,0,0,0.08);">
                <div style="margin-top: 10px; font-weight: 600; color: #333;">Burger</div>
            </div>
            <div style="min-width: 160px; text-align: center;">
                <img src="https://images.unsplash.com/photo-1585937421612-70a008356fbe?auto=format&fit=crop&w=160&q=80" alt="Indian" style="width: 120px; height: 120px; border-radius: 50%; object-fit: cover; box-shadow: 0 2px 12px rgba(0,0,0,0.08);">
                <div style="margin-top: 10px; font-weight: 600; color: #333;">Indian</div>
            </div>
            <div style="min-width: 160px; text-align: center;">
                <img src="https://images.unsplash.com/photo-1565299585323-38d6b0865b47?auto=format&fit=crop&w=160&q=80" alt="Mexican" style="width: 120px; height: 120px; border-radius: 50%; object-fit: cover; box-shadow: 0 2px 12px rgba(0,0,0,0.08);">
                <div style="margin-top: 10px; font-weight: 600; color: #333;">Mexican</div>
            </div>
            <div style="min-width: 160px; text-align: center;">
                <img src="https://images.unsplash.com/photo-1551024506-0bccd828d307?auto=format&fit=crop&w=160&q=80" alt="Desserts" style="width: 120px; height: 120px; border-radius: 50%; object-fit: cover; box-shadow: 0 2px 12px rgba(0,0,0,0.08);">
                <div style="margin-top: 10px; font-weight: 600; color: #333;">Desserts</div>
            </div>
            <div style="min-width: 160px; text-align: center;">
                <img src="https://images.unsplash.com/photo-1526318896980-cf78c088247c?auto=format&fit=crop&w=160&q=80" alt="Chinese" style="width: 120px; height: 120px; border-radius: 50%; object-fit: cover; box-shadow: 0 2px 12px rgba(0,0,0,0.08);">
                <div style="margin-top: 10px; font-weight: 600; color: #333;">Chinese</div>
            </div>
        </div>
    </section>

    <!-- Top Brands Section -->
    <section class="top-brands" style="margin: 32px auto 40px auto; max-width: 1100px;">
        <h2 style="font-size: 2rem; font-weight: 700; margin-bottom: 18px; color: #222;">Top Brands</h2>
        <div style="display: flex; gap: 32px; flex-wrap: wrap; justify-content: flex-start;">
            <div style="width: 120px; text-align: center;">
                <div style="width: 80px; height: 80px; margin: 0 auto 8px; background: #ee3124; border-radius: 12px; display: flex; align-items: center; justify-content: center; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
                    <span style="color: white; font-weight: 700; font-size: 18px;">Pizza Hut</span>
                </div>
                <div style="font-weight: 600; color: #333;">Pizza Hut</div>
            </div>
            <div style="width: 120px; text-align: center;">
                <div style="width: 80px; height: 80px; margin: 0 auto 8px; background: #0078ae; border-radius: 12px; display: flex; align-items: center; justify-content: center; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
                    <span style="color: white; font-weight: 700; font-size: 18px;">Domino's</span>
                </div>
                <div style="font-weight: 600; color: #333;">Domino's</div>
            </div>
            <div style="width: 120px; text-align: center;">
                <div style="width: 80px; height: 80px; margin: 0 auto 8px; background: #ffc72c; border-radius: 12px; display: flex; align-items: center; justify-content: center; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
                    <span style="color: #da291c; font-weight: 700; font-size: 24px;">M</span>
                </div>
                <div style="font-weight: 600; color: #333;">McDonald's</div>
            </div>
            <div style="width: 120px; text-align: center;">
                <div style="width: 80px; height: 80px; margin: 0 auto 8px; background: #e4002b; border-radius: 12px; display: flex; align-items: center; justify-content: center; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
                    <span style="color: white; font-weight: 700; font-size: 22px;">KFC</span>
                </div>
                <div style="font-weight: 600; color: #333;">KFC</div>
            </div>
            <div style="width: 120px; text-align: center;">
                <div style="width: 80px; height: 80px; margin: 0 auto 8px; background: #008c15; border-radius: 12px; display: flex; align-items: center; justify-content: center; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
                    <span style="color: #ffc600; font-weight: 700; font-size: 18px;">SUBWAY</span>
                </div>
                <div style="font-weight: 600; color: #333;">Subway</div>
            </div>
            <div style="width: 120px; text-align: center;">
                <div style="width: 80px; height: 80px; margin: 0 auto 8px; background: linear-gradient(to bottom, #f5ebdc 50%, #ed7902 50%); border-radius: 12px; display: flex; align-items: center; justify-content: center; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
                    <span style="color: #512314; font-weight: 700; font-size: 16px;">BURGER<br>KING</span>
                </div>
                <div style="font-weight: 600; color: #333;">Burger King</div>
            </div>
        </div>
    </section>

    <div class="container">
        <h2 style="margin-bottom: 20px; font-size: 28px;">Popular Restaurants</h2>
        <div class="restaurant-grid">
            <%
            List<Restaurant> restaurants = (List<Restaurant>) request.getAttribute("restaurants");
            if (restaurants != null && !restaurants.isEmpty()) {
                for (Restaurant r : restaurants) {
            %>
            <a href="menu?restaurantId=<%= r.getRestaurantId() %>" class="restaurant-card">
                <div class="restaurant-image" style="background-image: url('<%= r.getImageUrl() %>'); background-size: cover; background-position: center; border-radius: 16px 16px 0 0; height: 160px;"></div>
                <div class="restaurant-info">
                    <div class="restaurant-name"><%= r.getRestaurantName() %></div>
                    <div class="restaurant-cuisine"><%= r.getCuisine() %></div>
                    <div class="restaurant-rating">
                        <span class="rating"><%= r.getRating() %> ⭐</span>
                        <span>500+ ratings</span>
                    </div>
                    <div class="restaurant-delivery"><%= r.getDeliveryTime() %></div>
                </div>
            </a>
            <%
                }
            } else {
            %>
            <p style="text-align: center; color: #666; padding: 40px; grid-column: 1 / -1;">No restaurants available at the moment.</p>
            <%
            }
            %>
        </div>
    </div>
</body>
</html>
