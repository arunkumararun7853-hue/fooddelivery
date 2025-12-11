package com.tap.model;

import java.math.BigDecimal;

public class Restaurant {
	private int restaurantId;
	private String restaurantName;
	private String address;
	private BigDecimal rating;
	private String cuisine;
	private String deliveryTime;
	private boolean isActive;
	private String imageUrl;
	
	
		
	public Restaurant () {
		
	}


	public Restaurant(int restaurantId, String restaurantName, String address, BigDecimal rating, String cuisine,
			String deliveryTime, boolean isActive, String imageUrl) {
		super();
		this.restaurantId = restaurantId;
		this.restaurantName = restaurantName;
		this.address = address;
		this.rating = rating;
		this.cuisine = cuisine;
		this.deliveryTime = deliveryTime;
		this.isActive = isActive;
		this.imageUrl = imageUrl;
		
	}


	public int getRestaurantId() {
		return restaurantId;
	}


	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}


	public String getRestaurantName() {
		return restaurantName;
	}


	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public BigDecimal getRating() {
		return rating;
	}


	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}


	public String getCuisine() {
		return cuisine;
	}


	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}


	public String getDeliveryTime() {
		return deliveryTime;
	}


	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}


	public boolean isActive() {
		return isActive;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}



	
	
	
}
