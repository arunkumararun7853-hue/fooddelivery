package com.tap.servelets;



import java.io.IOException;


import com.tap.daoimplementations.UserDAOImpl;
import com.tap.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    
    private UserDAOImpl userRepository;
    
    @Override
    public void init() throws ServletException {
        userRepository = new UserDAOImpl();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println("=== SignupServlet doPost START ===");
        
        // Get form parameters
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        System.out.println("Signup parameters: name=" + name + ", username=" + username + 
                          ", email=" + email + ", phone=" + phone);
        
        // Basic validation
        if (name == null || username == null || email == null || password == null || 
            name.trim().isEmpty() || username.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) {
            System.out.println("Validation failed: Missing required fields");
            response.sendRedirect("signup.html?error=missing");
            return;
        }
        
        // Validate passwords match
        if (!password.equals(confirmPassword)) {
            System.out.println("Validation failed: Passwords do not match");
            response.sendRedirect("signup.html?error=password");
            return;
        }
        
        // Create user object
        User user = new User(name, username, password, email, phone, address, city);
        System.out.println("Created user object: " + user);
        
        try {
            // Add user to database
            userRepository.addUser(user);
            System.out.println("User added successfully with ID: " + user.getUserId());
            
            // Redirect to signin page with success message
            response.sendRedirect("signin.html?registered=true");
            
        } catch (Exception e) {
            System.err.println("Registration failed: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("signup.html?error=exists");
        }
        
        System.out.println("=== SignupServlet doPost END ===");
    }
}
