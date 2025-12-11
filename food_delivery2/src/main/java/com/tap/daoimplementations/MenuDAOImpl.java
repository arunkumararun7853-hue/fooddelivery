package com.tap.daoimplementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tap.dao.MenuDAO;
import com.tap.model.Menu;
import com.tap.utility.DBConnection;

public class MenuDAOImpl implements MenuDAO {
    
    private static final String INSERT_SQL = "INSERT INTO menu (menu_name, price, is_available, description, image_url, restaurantId) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT MenuId, menu_name, description, price, is_available, image_url, restaurantId FROM menu WHERE MenuId = ?";
    private static final String UPDATE_SQL = "UPDATE menu SET menu_name = ?, description = ?, price = ?, is_available = ?, image_url = ? WHERE MenuId = ?";
    private static final String DELETE_SQL = "DELETE FROM menu WHERE MenuId = ?";
    private static final String SELECT_BY_RESTAURANT = "SELECT MenuId, menu_name, description, price, is_available, image_url, restaurantId FROM menu WHERE restaurantId = ?";

    @Override
    public void addMenu(Menu menu) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            
            preparedStatement.setString(1, menu.getMenuName());
            preparedStatement.setDouble(2, menu.getPrice());
            preparedStatement.setBoolean(3, menu.isAvailable());
            preparedStatement.setString(4, menu.getDescription());
            preparedStatement.setString(5, menu.getImageUrl());
            preparedStatement.setInt(6, menu.getRestaurantId());
            
            preparedStatement.executeUpdate();
            
            ResultSet keys = preparedStatement.getGeneratedKeys();
            if (keys.next()) {
                menu.setMenuId(keys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Menu getMenu(int menuId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1, menuId);
            
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Menu m = new Menu();
                m.setMenuId(rs.getInt("MenuId"));
                m.setMenuName(rs.getString("menu_name"));
                m.setDescription(rs.getString("description"));
                m.setPrice(rs.getDouble("price"));
                m.setAvailable(rs.getBoolean("is_available"));
                m.setImageUrl(rs.getString("image_url"));
                m.setRestaurantId(rs.getInt("restaurantId"));
                return m;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void updateMenu(Menu menu) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_SQL);
            
            preparedStatement.setString(1, menu.getMenuName());
            preparedStatement.setString(2, menu.getDescription());
            preparedStatement.setDouble(3, menu.getPrice());
            preparedStatement.setBoolean(4, menu.isAvailable());
            preparedStatement.setString(5, menu.getImageUrl());
            preparedStatement.setInt(6, menu.getMenuId());
            
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteMenu(int MenuId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_SQL);
            preparedStatement.setInt(1, MenuId);
            
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Menu> getAlMenusByRestaurent(int restaurantId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Menu> list = new ArrayList<>();
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_BY_RESTAURANT);
            preparedStatement.setInt(1, restaurantId);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Menu menu = new Menu();
                menu.setMenuId(resultSet.getInt("MenuId"));
                menu.setMenuName(resultSet.getString("menu_name"));
                menu.setDescription(resultSet.getString("description"));
                menu.setPrice(resultSet.getDouble("price"));
                menu.setAvailable(resultSet.getBoolean("is_available"));
                menu.setImageUrl(resultSet.getString("image_url"));
                menu.setRestaurantId(resultSet.getInt("restaurantId"));
                list.add(menu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}