package com.tap.daoimplementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tap.dao.CartDAO;
import com.tap.dao.MenuDAO;
import com.tap.model.Cart;
import com.tap.model.CartItem;
import com.tap.model.Menu;
import com.tap.utility.DBConnection;

public class CartDAOImpl implements CartDAO {
    
    private MenuDAO menuDAO;
    
    public CartDAOImpl() {
        this.menuDAO = new MenuDAOImpl();
        ensureCartTableExists();
    }
    
    private void ensureCartTableExists() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnection();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS cart_items (" +
                "cart_item_id INT PRIMARY KEY AUTO_INCREMENT," +
                "user_id INT NOT NULL," +
                "menu_id INT NOT NULL," +
                "quantity INT NOT NULL DEFAULT 1," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
                "UNIQUE KEY unique_user_menu (user_id, menu_id)" +
                ")";
            
            preparedStatement = connection.prepareStatement(createTableSQL);
            preparedStatement.executeUpdate();
            System.out.println("Cart table ensured to exist");
        } catch (SQLException e) {
            System.err.println("Error creating cart table: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static final String INSERT_CART_ITEM = "INSERT INTO cart_items (user_id, menu_id, quantity) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE quantity = ?";
    private static final String UPDATE_CART_ITEM = "UPDATE cart_items SET quantity = ? WHERE user_id = ? AND menu_id = ?";
    private static final String DELETE_CART_ITEM = "DELETE FROM cart_items WHERE user_id = ? AND menu_id = ?";
    private static final String CLEAR_CART = "DELETE FROM cart_items WHERE user_id = ?";
    private static final String SELECT_CART_ITEMS = "SELECT ci.menu_id, ci.quantity, m.menu_name, m.price, m.restaurantId FROM cart_items ci JOIN menu m ON ci.menu_id = m.MenuId WHERE ci.user_id = ?";
    private static final String SELECT_CART_COUNT = "SELECT SUM(quantity) FROM cart_items WHERE user_id = ?";
    private static final String SELECT_CART_TOTAL = "SELECT SUM(ci.quantity * m.price) FROM cart_items ci JOIN menu m ON ci.menu_id = m.MenuId WHERE ci.user_id = ?";

    @Override
    public void addItemToCart(int userId, int menuId, int quantity) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            System.out.println("Attempting to add item to cart - userId: " + userId + ", menuId: " + menuId + ", quantity: " + quantity);
            connection = DBConnection.getConnection();
            System.out.println("Database connection obtained");
            
            preparedStatement = connection.prepareStatement(INSERT_CART_ITEM);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, menuId);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setInt(4, quantity);
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("SQL executed, rows affected: " + rowsAffected);
            System.out.println("Added item " + menuId + " to cart for user " + userId);
        } catch (SQLException e) {
            System.err.println("SQL Error in addItemToCart: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean canAddItemToCart(int userId, int menuId) {
        System.out.println("Checking if can add item - userId: " + userId + ", menuId: " + menuId);
        
        // Get current cart
        Cart currentCart = getCartByUserId(userId);
        System.out.println("Current cart isEmpty: " + currentCart.isEmpty() + ", restaurantId: " + currentCart.getRestaurantId());
        
        if (currentCart.isEmpty()) {
            System.out.println("Cart is empty, can add any item");
            return true; // Empty cart, can add any item
        }
        
        // Get restaurant ID of the item being added
        Menu menu = menuDAO.getMenu(menuId);
        if (menu == null) {
            System.out.println("Menu item not found: " + menuId);
            return false; // Menu item doesn't exist
        }
        
        System.out.println("Menu item found - restaurantId: " + menu.getRestaurantId());
        
        // Check if it's from the same restaurant
        boolean canAdd = currentCart.getRestaurantId() == menu.getRestaurantId();
        System.out.println("Can add item (same restaurant): " + canAdd);
        return canAdd;
    }

    @Override
    public void updateCartItem(int userId, int menuId, int quantity) {
        if (quantity <= 0) {
            removeItemFromCart(userId, menuId);
            return;
        }
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_CART_ITEM);
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, menuId);
            preparedStatement.executeUpdate();
            System.out.println("Updated item " + menuId + " quantity to " + quantity + " for user " + userId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeItemFromCart(int userId, int menuId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_CART_ITEM);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, menuId);
            preparedStatement.executeUpdate();
            System.out.println("Removed item " + menuId + " from cart for user " + userId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void clearCart(int userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(CLEAR_CART);
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
            System.out.println("Cleared cart for user " + userId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Cart getCartByUserId(int userId) {
        Cart cart = new Cart();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_CART_ITEMS);
            preparedStatement.setInt(1, userId);
            
            ResultSet rs = preparedStatement.executeQuery();
            int restaurantId = 0;
            
            while (rs.next()) {
                CartItem item = new CartItem();
                item.setMenuId(rs.getInt("menu_id"));
                item.setName(rs.getString("menu_name"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));
                
                // Set restaurant ID from first item
                if (restaurantId == 0) {
                    restaurantId = rs.getInt("restaurantId");
                    cart.setRestaurantId(restaurantId);
                }
                
                cart.addItem(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return cart;
    }

    @Override
    public List<CartItem> getCartItems(int userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<CartItem> cartItems = new ArrayList<>();
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_CART_ITEMS);
            preparedStatement.setInt(1, userId);
            
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                CartItem item = new CartItem();
                item.setMenuId(rs.getInt("menu_id"));
                item.setName(rs.getString("menu_name"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));
                cartItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return cartItems;
    }

    @Override
    public int getCartItemCount(int userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int count = 0;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_CART_COUNT);
            preparedStatement.setInt(1, userId);
            
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return count;
    }

    @Override
    public double getCartTotal(int userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        double total = 0.0;
        
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_CART_TOTAL);
            preparedStatement.setInt(1, userId);
            
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return total;
    }
}