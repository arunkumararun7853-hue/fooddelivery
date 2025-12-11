package com.tap.daoimplementations;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.x.protobuf.MysqlxCrud.Order;
import com.tap.dao.OrdersDAO;
import com.tap.utility.DBConnection;
import com.tap.model.Orders;



public class OrdersDAOImpl implements OrdersDAO{

	@Override
	public void addOrder(Orders order) {
		final String sql = "INSERT INTO orders (order_date, restaurant_id, user_id, total_amount, payment_method, status, delivery_address) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try 
        {
        Connection connection = DBConnection.getConnection();
       PreparedStatement ps = connection.prepareStatement(sql);
       Timestamp ts = order.getOrderDate() != null ? Timestamp.valueOf(order.getOrderDate()) : new Timestamp(System.currentTimeMillis());
            ps.setTimestamp(1, ts);
            ps.setInt(2, order.getRestaurantId());
            ps.setInt(3, order.getUserId());
            ps.setBigDecimal(4, order.getTotalAmount() != null ? order.getTotalAmount() : BigDecimal.ZERO);
            ps.setString(5, order.getPaymentMethod());
            ps.setString(6, order.getStatus());
            ps.setString(7, order.getDeliveryAddress());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    order.setOrderId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting order", e);
        }
	}

	@Override
	public Orders getOrder(int orderId) {
		String sql = "SELECT order_id, order_date, restaurant_id, user_id, total_amount, payment_method, status, delivery_address FROM orders WHERE order_id = ?";
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		
		try {
			connection = DBConnection.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, orderId);
			ResultSet resultSet = preparedStatement.executeQuery();
			 if (resultSet.next()) {
                 return mapRow(resultSet);
             }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		return null;
	}

	

	@Override
	public void deleteOrder(int OrderId) {
		String sql = "DELETE FROM orders WHERE order_id = ?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DBConnection.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, OrderId);
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public List<Order> getAllOrdersByUser(int userId) {
		String sql = "SELECT order_id, order_date, restaurant_id, user_id, total_amount, payment_method, status, delivery_address FROM orders WHERE user_id = ? ORDER BY order_date DESC";
        List<Orders> list = new ArrayList<>();
        Connection connection=null;
        PreparedStatement preparedStatement = null;
        
        try {
        	connection = DBConnection.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				list.add(mapRow(resultSet));
			}
			
			
		} catch (SQLException e) {
			
			
			e.printStackTrace();
		}finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
        
		return null;
	}

	@Override
	public void updateOrder(Orders order) {
		String sql = "UPDATE orders SET order_date = ?, restaurant_id = ?, user_id = ?, total_amount = ?, payment_method = ?, status = ?, delivery_address = ? WHERE order_id = ?";
		PreparedStatement ps=null;
		Connection connection = null;
			try {
				connection = DBConnection.getConnection();
				ps = connection.prepareStatement(sql);
				Timestamp ts = order.getOrderDate() != null ? Timestamp.valueOf(order.getOrderDate()) : new Timestamp(System.currentTimeMillis());
	            ps.setTimestamp(1, ts);
	            ps.setInt(2, order.getRestaurantId());
	            ps.setInt(3, order.getUserId());
	            ps.setBigDecimal(4, order.getTotalAmount() != null ? order.getTotalAmount() : BigDecimal.ZERO);
	            ps.setString(5, order.getPaymentMethod());
	            ps.setString(6, order.getStatus());
	            ps.setString(7, order.getDeliveryAddress());
	            ps.setInt(8, order.getOrderId());
	            ps.executeUpdate();
			
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	 private Orders mapRow(ResultSet rs) throws SQLException {
	        Orders o = new Orders();
	        o.setOrderId(rs.getInt("order_id"));
	        Timestamp ts = rs.getTimestamp("order_date");
	        if (ts != null) {
	            o.setOrderDate(ts.toLocalDateTime());
	        }
	        o.setRestaurantId(rs.getInt("restaurant_id"));
	        o.setUserId(rs.getInt("user_id"));
	        o.setTotalAmount(rs.getBigDecimal("total_amount"));
	        o.setPaymentMethod(rs.getString("payment_method"));
	        o.setStatus(rs.getString("status"));
	        o.setDeliveryAddress(rs.getString("delivery_address"));
	        return o;
	    }
	
}