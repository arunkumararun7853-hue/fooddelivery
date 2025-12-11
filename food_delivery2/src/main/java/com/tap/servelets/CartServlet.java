package com.tap.servelets;



import java.io.IOException;

import com.tap.dao.MenuDAO;
import com.tap.daoimplementations.MenuDAOImpl;
import com.tap.model.Cart;
import com.tap.model.CartItem;
import com.tap.model.Menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    
    private MenuDAO menuDAO;
    
    @Override
    public void init() throws ServletException {
        menuDAO = new MenuDAOImpl();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println("=== CartServlet doPost START ===");
        
        try {
            HttpSession session = request.getSession();
            Cart cart = getCartFromSession(session);
            
            String action = request.getParameter("action");
            String restaurantIdParam = request.getParameter("restaurantId");
            String itemIdParam = request.getParameter("itemId");
            String quantityParam = request.getParameter("quantity");
            
            System.out.println("Parameters: action=" + action + ", restaurantId=" + restaurantIdParam + 
                              ", itemId=" + itemIdParam + ", quantity=" + quantityParam);
            
            if ("clear".equals(action)) {
                cart.clear();
                System.out.println("Cart cleared");
                
            } else if ("add".equals(action) && restaurantIdParam != null && itemIdParam != null && quantityParam != null) {
                try {
                    int restaurantId = Integer.parseInt(restaurantIdParam);
                    int itemId = Integer.parseInt(itemIdParam);
                    int quantity = Integer.parseInt(quantityParam);
                    
                    if (quantity > 0 && quantity <= 10) {
                        addItemToCart(cart, restaurantId, itemId, quantity);
                        System.out.println("Item added to cart successfully");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format in add action");
                }
                
            } else if ("update".equals(action) && itemIdParam != null && quantityParam != null) {
                try {
                    int itemId = Integer.parseInt(itemIdParam);
                    int quantity = Integer.parseInt(quantityParam);
                    
                    if (quantity > 0 && quantity <= 10) {
                        cart.updateItem(itemId, quantity);
                        System.out.println("Item updated to quantity " + quantity);
                    } else if (quantity <= 0) {
                        cart.removeItem(itemId);
                        if (cart.isEmpty()) {
                            cart.setRestaurantId(0);
                        }
                        System.out.println("Item removed (quantity <= 0)");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format in update action");
                }
                
            } else if ("remove".equals(action) && itemIdParam != null) {
                try {
                    int itemId = Integer.parseInt(itemIdParam);
                    cart.removeItem(itemId);
                    if (cart.isEmpty()) {
                        cart.setRestaurantId(0);
                    }
                    System.out.println("Item removed from cart");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format in remove action");
                }
            }
            
            // Save cart to session
            session.setAttribute("cart", cart);
            System.out.println("Cart saved to session. Total items: " + cart.getTotalItems());
            System.out.println("=== CartServlet doPost END ===");
            
            response.sendRedirect("cart");
            
        } catch (Exception e) {
            System.out.println("ERROR in CartServlet doPost: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("cart");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println("=== CartServlet doGet START ===");
        
        try {
            HttpSession session = request.getSession();
            Cart cart = getCartFromSession(session);
            
            request.setAttribute("cart", cart);
            System.out.println("Set cart as request attribute with " + cart.getTotalItems() + " items");
            System.out.println("=== CartServlet doGet END ===");
            
            request.getRequestDispatcher("cart.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.out.println("ERROR in CartServlet doGet: " + e.getMessage());
            e.printStackTrace();
            
            // Create a fallback cart and try again
            Cart fallbackCart = new Cart();
            request.setAttribute("cart", fallbackCart);
            request.getRequestDispatcher("cart.jsp").forward(request, response);
        }
    }
    
    // Helper method to get cart from session
    private Cart getCartFromSession(HttpSession session) {
        Object cartObj = session.getAttribute("cart");
        
        if (cartObj == null) {
            Cart cart = new Cart();
            System.out.println("Created new cart");
            return cart;
        } else if (cartObj instanceof Cart) {
            Cart cart = (Cart) cartObj;
            System.out.println("Using existing cart with " + cart.getTotalItems() + " items");
            return cart;
        } else {
            System.out.println("ERROR: Session cart is not a Cart instance: " + cartObj.getClass().getName());
            return new Cart(); // Create new cart as fallback
        }
    }
    
    private void addItemToCart(Cart cart, int restaurantId, int menuId, int quantity) {
        try {
            // Check restaurant consistency
            if (!cart.isEmpty() && cart.getRestaurantId() != restaurantId) {
                System.out.println("ERROR: Cannot add items from different restaurants");
                return;
            }
            
            Menu menu = menuDAO.getMenu(menuId);
            if (menu != null && menu.isAvailable()) {
                CartItem cartItem = new CartItem(menuId, menu.getMenuName(), menu.getPrice(), quantity);
                cart.addItem(cartItem);
                cart.setRestaurantId(restaurantId);
                System.out.println("SUCCESS: Added " + menu.getMenuName() + " to cart");
            } else {
                System.out.println("FALLBACK: Menu item not found, adding fallback");
                CartItem fallbackItem = new CartItem(menuId, "Menu Item " + menuId, 99.99, quantity);
                cart.addItem(fallbackItem);
                cart.setRestaurantId(restaurantId);
            }
        } catch (Exception e) {
            System.out.println("ERROR: Database failed, adding fallback item");
            CartItem fallbackItem = new CartItem(menuId, "Menu Item " + menuId, 99.99, quantity);
            cart.addItem(fallbackItem);
            cart.setRestaurantId(restaurantId);
        }
    }
}