package com.tap.dao;

import java.util.List;

import com.tap.model.Restaurant;

public interface RestaurantDAO {

	void addRestaurant (Restaurant restaurant);
	Restaurant getRestaurant (int restaurantId);
	void updateRestaurnt (Restaurant restaurant);
	void deleteRestsurant(int restaurantId);
	List <Restaurant> getAllRestaurants();
}
