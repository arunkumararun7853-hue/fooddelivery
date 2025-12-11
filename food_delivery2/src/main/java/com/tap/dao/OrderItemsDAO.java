package com.tap.dao;

import java.util.List;

import com.tap.model.OrderItems;

public interface OrderItemsDAO {

	void addOrderItem(OrderItems orderItem);
	OrderItems getOrderItem (int orderItemId);
	void updateOrderItem (OrderItems orderItem);
	void deleteOrderItem (int OrderItemId);
	List<OrderItems> getOrderItemByOrder(int orderId);
}
