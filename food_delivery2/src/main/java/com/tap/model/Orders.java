package com.tap.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Orders {
	private  int orderId;
    private LocalDateTime orderDate;
    private int restaurantId;
    private int userId;
    private BigDecimal totalAmount;
    private String paymentMethod;
    private String status;
    private String deliveryAddress;
   
 
    
    
    
    public Orders() {
    }


	public Orders(int orderId, LocalDateTime orderDate, int restaurantId, int userId, BigDecimal totalAmount,
			String paymentMethod, String status, String deliveryAddress) {
		super();
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.restaurantId = restaurantId;
		this.userId = userId;
		this.totalAmount = totalAmount;
		this.paymentMethod = paymentMethod;
		this.status = status;
		this.deliveryAddress = deliveryAddress;
	}
	 public Orders(LocalDateTime orderDate, int restaurantId, int userId, BigDecimal totalAmount,
             String paymentMethod, String status, String deliveryAddress) {
    this.orderDate = orderDate;
    this.restaurantId = restaurantId;
    this.userId = userId;
    this.totalAmount = totalAmount;
    this.paymentMethod = paymentMethod;
    this.status = status;
    this.deliveryAddress = deliveryAddress;
}
	 


	public int getOrderId() {
		return orderId;
	}


	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	public LocalDateTime getOrderDate() {
		return orderDate;
	}


	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}


	public int getRestaurantId() {
		return restaurantId;
	}


	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public BigDecimal getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}


	public String getPaymentMethod() {
		return paymentMethod;
	}


	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getDeliveryAddress() {
		return deliveryAddress;
	}


	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
    
}
