package com.tap.dao;

import java.util.List;

import com.mysql.cj.x.protobuf.MysqlxCrud.Order;
import com.tap.model.Orders;

public interface OrdersDAO {
	void addOrder(Orders order);
	Orders getOrder (int orderId);
	void updateOrder(Orders order);
	void deleteOrder(int OrderId);
	List<Order> getAllOrdersByUser(int userId);
}
