package com.tap.servelets;



import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/orderconformation")
public class OrderConfirmationServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println("=== OrderConfirmationServlet doGet START ===");
        
        HttpSession session = request.getSession();
        
        // Check if user is logged in
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            System.out.println("User not logged in - redirecting to signin");
            response.sendRedirect("signin.html");
            return;
        }
        
        System.out.println("User logged in, forwarding to confirmation page");
        
        // Forward to JSP
        request.getRequestDispatcher("orderconformation.jsp").forward(request, response);
        
        System.out.println("=== OrderConfirmationServlet doGet END ===");
    }
}
