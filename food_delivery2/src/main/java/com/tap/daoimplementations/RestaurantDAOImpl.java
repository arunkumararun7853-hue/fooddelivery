package com.tap.daoimplementations;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tap.dao.RestaurantDAO;
import com.tap.model.Restaurant;
import com.tap.utility.DBConnection;

public class RestaurantDAOImpl implements RestaurantDAO{
	private static final String INSERT_SQL = "INSERT INTO restaurant (restaurant_name, address, rating, cuisine, delivery_time, is_active, image_url) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT RestaurantId, restaurant_name, address, rating, cuisine, delivery_time, is_active, image_url FROM restaurant WHERE RestaurantId = ?";
    private static final String UPDATE_SQL = "UPDATE restaurant SET restaurant_name = ?, address = ?, rating = ?, cuisine = ?, delivery_time = ?, is_active = ?, image_url = ?,  WHERE RestaurantId = ?";
    private static final String DELETE_SQL = "DELETE FROM restaurant WHERE RestaurantId = ?";
    private static final String SELECT_ALL = "SELECT RestaurantId, restaurant_name, address, rating, cuisine, delivery_time, is_active, image_url FROM restaurant";
	@Override
	public void addRestaurant(Restaurant restaurant) {
		
		
		
		Connection connection =null;
		PreparedStatement preparedStatement=null;
		try {
			connection = DBConnection.getConnection();
			preparedStatement = connection.prepareStatement(INSERT_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
			
			
			
			    preparedStatement.setString(1, restaurant.getRestaurantName());
	            preparedStatement.setString(2, restaurant.getAddress());
	            BigDecimal rating = restaurant.getRating();
	            
	            if (rating != null)
	            	preparedStatement.setBigDecimal(3, rating); 
	            else preparedStatement.setNull(3, java.sql.Types.DECIMAL);
	            
	            
	            preparedStatement.setString(4, restaurant.getCuisine());
	            preparedStatement.setString(5, restaurant.getDeliveryTime());
	            preparedStatement.setBoolean(6, restaurant.isActive());
	            preparedStatement.setString(7, restaurant.getImageUrl());
	            
	            preparedStatement.executeUpdate();
	            ResultSet keys = preparedStatement.getGeneratedKeys();
	            
	                if (keys.next()) {
	                    restaurant.setRestaurantId(keys.getInt(1));
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
	}
	@Override
	public Restaurant getRestaurant(int restaurantId) {
		Connection connection =null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DBConnection.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_BY_ID);
			
			preparedStatement.setInt(1, restaurantId);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				Restaurant r = new Restaurant();
				r.setRestaurantId(resultSet.getInt("RestaurantId"));
                r.setRestaurantName(resultSet.getString("restaurant_name"));
                r.setAddress(resultSet.getString("address"));
                r.setRating(resultSet.getBigDecimal("rating"));
                r.setCuisine(resultSet.getString("cuisine"));
                r.setDeliveryTime(resultSet.getString("delivery_time"));
                r.setActive(resultSet.getBoolean("is_active"));
                r.setImageUrl(resultSet.getString("image_url"));
                
                return r;
			}
			
			
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}	finally {
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
	public void updateRestaurnt(Restaurant restaurant) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_SQL);
            preparedStatement.setString(1, restaurant.getRestaurantName());
            preparedStatement.setString(2, restaurant.getAddress());
            
            
            BigDecimal rating = restaurant.getRating();
            if (rating != null)
            	preparedStatement.setBigDecimal(3, rating); 
            else preparedStatement.setNull(3, java.sql.Types.DECIMAL);
           
            preparedStatement.setString(4, restaurant.getCuisine());
            preparedStatement.setString(5, restaurant.getDeliveryTime());
            preparedStatement.setBoolean(6, restaurant.isActive());
            preparedStatement.setString(7, restaurant.getImageUrl());
            
            
           
            
            
            preparedStatement.setInt(9, restaurant.getRestaurantId());
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
	public void deleteRestsurant(int restaurantId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DBConnection.getConnection();
			preparedStatement = connection.prepareStatement(DELETE_SQL);
			preparedStatement.setInt(1, restaurantId);
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
	public List<Restaurant> getAllRestaurants() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		 List<Restaurant> list = new ArrayList<>();
		try {
			connection = DBConnection.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_ALL);
			
			
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				 Restaurant r = new Restaurant();
	                r.setRestaurantId(rs.getInt("RestaurantId"));
	                r.setRestaurantName(rs.getString("restaurant_name"));
	                r.setAddress(rs.getString("address"));
	                r.setRating(rs.getBigDecimal("rating"));
	                r.setCuisine(rs.getString("cuisine"));
	                r.setDeliveryTime(rs.getString("delivery_time"));
	                r.setActive(rs.getBoolean("is_active"));
	                r.setImageUrl(rs.getString("image_url"));
	              
	               
					list.add(r);
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
		return list;
	}
}