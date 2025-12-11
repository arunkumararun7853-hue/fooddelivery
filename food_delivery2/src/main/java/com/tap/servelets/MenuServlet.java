package com.tap.servelets;



import java.io.IOException;
import java.util.List;

import com.tap.dao.MenuDAO;


import com.tap.daoimplementations.MenuDAOImpl;
import com.tap.daoimplementations.RestaurantDAOImpl;
import com.tap.model.Menu;
import com.tap.model.Restaurant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {
    
    private MenuDAO menuDAO;
    private RestaurantDAOImpl restaurantRepository;
    
    @Override
    public void init() throws ServletException {
        menuDAO = new MenuDAOImpl();
        restaurantRepository = new  RestaurantDAOImpl();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String restaurantIdParam = request.getParameter("restaurantId");
        
        if (restaurantIdParam != null && !restaurantIdParam.isEmpty()) {
            try {
                int restaurantId = Integer.parseInt(restaurantIdParam);
                
                System.out.println("Fetching menu for restaurant ID: " + restaurantId);
                
                // Get restaurant details
                Restaurant restaurant = restaurantRepository.getRestaurant(restaurantId);
                System.out.println("Restaurant found: " + (restaurant != null ? restaurant.getRestaurantName() : "null"));
                
                // Get menu items for this restaurant
                List<Menu> menuItems = menuDAO.getAlMenusByRestaurent(restaurantId);
                System.out.println("Menu items found: " + (menuItems != null ? menuItems.size() : "null"));
                
                // Set attributes
                request.setAttribute("restaurant", restaurant);
                request.setAttribute("menus", menuItems);
                
                // Forward to JSP
                request.getRequestDispatcher("menu.jsp").forward(request, response);
                
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid restaurant ID");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading menu: " + e.getMessage());
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Restaurant ID is required");
        }
    }
}
