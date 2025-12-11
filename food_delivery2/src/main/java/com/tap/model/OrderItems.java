package com.tap.model;

import java.math.BigDecimal;

public class OrderItems {
	 private int orderItemId;
     private int orderId;
     private int menuId;
     private int quantity;
     private BigDecimal unitPrice;
     
     
     public OrderItems() {
    	 
     }


	public OrderItems(int orderItemId, int orderId, int menuId, int quantity, BigDecimal unitPrice) {
		super();
		this.orderItemId = orderItemId;
		this.orderId = orderId;
		this.menuId = menuId;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}


	public  int getOrderItemId() {
		return orderItemId;
	}


	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}


	public int getOrderId() {
		return orderId;
	}


	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	public int getMenuId() {
		return menuId;
	}


	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public BigDecimal getUnitPrice() {
		return unitPrice;
	}


	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
     
}
