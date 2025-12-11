package com.tap.daoimplementations;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tap.dao.OrderItemsDAO;
import com.tap.model.OrderItems;
import com.tap.utility.DBConnection;

public class OrderItemsDAOImpl implements OrderItemsDAO {

	
	@Override
	public void addOrderItem(OrderItems orderItem) {
		 String sql = "INSERT INTO order_items (order_id, menu_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
		 Connection connection = null;
			PreparedStatement ps = null;
		 try {
			connection = DBConnection.getConnection();
			ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, orderItem.getOrderId());
            ps.setInt(2, orderItem.getMenuId());
            ps.setInt(3, orderItem.getQuantity());
            ps.setBigDecimal(4, orderItem.getUnitPrice() != null ? orderItem.getUnitPrice() : BigDecimal.ZERO);
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                orderItem.setOrderItemId(keys.getInt(1));
            }
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

	@Override
	public OrderItems getOrderItem(int orderItemId) {
		String sql = "SELECT order_item_id, order_id, menu_id, quantity, unit_price FROM order_items WHERE order_item_id = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DBConnection.getConnection();
		    ps = connection.prepareStatement(sql);
			
			 ps.setInt(1, orderItemId);
			 ResultSet rs = ps.executeQuery();
			 if (rs.next()) {
                 return mapRow(rs);
             }
			
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		finally {
			
			try {
				ps.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return null;
	}

	@Override
	public void updateOrderItem(OrderItems orderItem) {
		
	
		String sql = "UPDATE order_items SET order_id = ?, menu_id = ?, quantity = ?, unit_price = ? WHERE order_item_id = ?";
		
		try {
			Connection c = DBConnection.getConnection(); 
			PreparedStatement ps = c.prepareStatement(sql);
			 ps.setInt(1, orderItem.getOrderId());
	            ps.setInt(2, orderItem.getMenuId());
	            ps.setInt(3, orderItem.getQuantity());
	            ps.setBigDecimal(4, orderItem.getUnitPrice() != null ? orderItem.getUnitPrice() : BigDecimal.ZERO);
	            ps.setInt(5, orderItem.getOrderItemId());
	            ps.executeUpdate();
			 
	}catch(SQLException e){
		e.printStackTrace();
	}
}

	@Override
	public void deleteOrderItem(int OrderItemId) {
		// TODO Auto-generated method stub22l
		String sql = "DELETE FROM order_items WHERE order_item_id = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DBConnection.getConnection();
			ps = connection.prepareStatement(sql);
			 ps.setInt(1, OrderItemId);
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

	@Override
	public List<OrderItems> getOrderItemByOrder(int orderId) {
		String sql = "SELECT order_item_id, order_id, menu_id, quantity, unit_price FROM order_items WHERE order_id = ? ORDER BY order_item_id ASC";
        List<OrderItems> list = new ArrayList<>();
        Connection connection = null;
		PreparedStatement ps = null;
        try {
        	connection = DBConnection.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setInt(1, orderId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
                list.add(mapRow(rs));
            }
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
		return list;
	}
	 private OrderItems mapRow(ResultSet rs) throws SQLException {
	        OrderItems oi = new OrderItems();
	        oi.setOrderItemId(rs.getInt("order_item_id"));
	        oi.setOrderId(rs.getInt("order_id"));
	        oi.setMenuId(rs.getInt("menu_id"));
	        oi.setQuantity(rs.getInt("quantity"));
	        oi.setUnitPrice(rs.getBigDecimal("unit_price"));
	        return oi;
	    }

}
