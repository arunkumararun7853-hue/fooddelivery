package com.tap.dao;



import com.tap.model.Cart;
import com.tap.model.CartItem;
import java.util.List;

public interface CartDAO {
    void addItemToCart(int userId, int menuId, int quantity);
    void updateCartItem(int userId, int menuId, int quantity);
    void removeItemFromCart(int userId, int menuId);
    void clearCart(int userId);
    Cart getCartByUserId(int userId);
    List<CartItem> getCartItems(int userId);
    int getCartItemCount(int userId);
    double getCartTotal(int userId);
    boolean canAddItemToCart(int userId, int menuId);
}