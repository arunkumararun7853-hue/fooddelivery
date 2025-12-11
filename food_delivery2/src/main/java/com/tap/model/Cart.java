package com.tap.model;

import java.util.HashMap;
import java.util.Map;

import com.tap.model.CartItem;

public class Cart {
    private Map<Integer, CartItem> items;
    private int restaurantId;

    public Cart() {
        this.items = new HashMap<>();
        this.restaurantId = 0; // 0 means no restaurant set
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void addItem(CartItem item) {
        int menuId = item.getMenuId();
        
        if (items.containsKey(menuId)) {
            // Item already exists, update quantity
            CartItem existingItem = items.get(menuId);
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
        } else {
            // New item, add to cart
            items.put(menuId, item);
        }
    }

    public void updateItem(int menuId, int quantity) {
        if (items.containsKey(menuId)) {
            if (quantity <= 0) {
                items.remove(menuId);
            } else {
                items.get(menuId).setQuantity(quantity);
            }
        }
    }

    public void removeItem(int menuId) {
        items.remove(menuId);
    }

    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
        restaurantId = 0;
    }

    public double getTotalAmount() {
        double total = 0;
        for (CartItem item : items.values()) {
            total += item.getSubTotal();
        }
        return total;
    }

    public int getTotalItems() {
        int count = 0;
        for (CartItem item : items.values()) {
            count += item.getQuantity();
        }
        return count;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                ", totalAmount=" + getTotalAmount() +
                ", totalItems=" + getTotalItems() +
                '}';
    }
}
