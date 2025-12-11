package com.tap.daoimplementations;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tap.dao.UserDAO;
import com.tap.model.User;
import com.tap.utility.DBConnection;

public class UserDAOImpl implements UserDAO {
    
    // Logical order: name, username, email, phone, password, address, role
    private static final String INSERT_SQL = "INSERT INTO user (name, user_name, email, phone_number, password, address, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT user_id, name, user_name, email, phone_number, password, address, role, createddate, lastlogindate FROM user WHERE user_id = ?";
    private static final String UPDATE_SQL = "UPDATE user SET name = ?, user_name = ?, email = ?, phone_number = ?, password = ?, address = ?, role = ? WHERE user_id = ?";
    private static final String DELETE_SQL = "DELETE FROM user WHERE user_id = ?";
    private static final String SELECT_ALL = "SELECT user_id, name, user_name, email, phone_number, password, address, role, createddate, lastlogindate FROM user";
    private static final String VALIDATE_USER = "SELECT user_id, name, user_name, email, phone_number, password, address, role, createddate, lastlogindate FROM user WHERE email = ? AND password = ?";

    @Override
    public void addUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            
            // Set parameters in logical order
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getAddress());
            preparedStatement.setString(7, user.getRole());
            
            preparedStatement.executeUpdate();
            
            ResultSet keys = preparedStatement.getGeneratedKeys();
            if (keys.next()) {
                user.setUserId(keys.getInt(1));
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
    public User getUser(int userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1, userId);
            
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return createUserFromResultSet(rs);
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
    public void updateUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_SQL);
            
            // Set parameters in logical order
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getAddress());
            preparedStatement.setString(7, user.getRole());
            preparedStatement.setInt(8, user.getUserId());
            
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
    public void deleteUser(int userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_SQL);
            preparedStatement.setInt(1, userId);
            
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
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL);
            
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                list.add(createUserFromResultSet(rs));
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

    @Override
    public User validateUser(String email, String password) {
        System.out.println("UserRepositoryImpl.validateUser called with email: " + email);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnection();
            System.out.println("Database connection established: " + (connection != null));
            
            preparedStatement = connection.prepareStatement(VALIDATE_USER);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            
            System.out.println("Executing query: " + VALIDATE_USER);
            System.out.println("Parameters: email=" + email + ", password=[HIDDEN]");
            
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                System.out.println("User found in database");
                User user = createUserFromResultSet(rs);
                System.out.println("Created user object: " + user.getName());
                return user;
            } else {
                System.out.println("No user found with provided credentials");
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception in validateUser: " + e.getMessage());
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
    
    // Helper method to create User from ResultSet - keeps code DRY
    private User createUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setName(rs.getString("name"));
        user.setUsername(rs.getString("user_name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone_number"));
        user.setPassword(rs.getString("password"));
        user.setAddress(rs.getString("address"));
        user.setRole(rs.getString("role"));
        user.setCreatedDate(rs.getTimestamp("createddate"));
        user.setLastLoginDate(rs.getTimestamp("lastlogindate"));
        return user;
    }
}