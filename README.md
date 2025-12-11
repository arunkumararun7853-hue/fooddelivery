# fooddelivery
# Food Delivery Web Application

A complete food delivery web application built with Java Servlets, JSP, and MySQL. Features include user authentication, restaurant browsing, menu management, cart functionality, and order processing.

## 🚀 Features

### User Management
- **User Registration & Login**: Secure authentication system with session management
- **Profile Management**: User profile with name, email, phone, and address
- **Session-based Authentication**: Automatic redirects based on login status

### Restaurant & Menu System
- **Restaurant Browsing**: View available restaurants with ratings and cuisine types
- **Dynamic Menu Display**: Browse restaurant menus with images, prices, and descriptions
- **Menu Item Management**: Add, update, and manage menu items

### Shopping Cart
- **Session-based Cart**: Persistent cart across browser sessions
- **Zomato-style UI**: Modern add-to-cart interface with quantity controls
- **Restaurant Validation**: Users can only order from one restaurant at a time
- **Real-time Updates**: Live cart summary with item count and total

### Order Processing
- **Secure Checkout**: Complete order form with delivery details
- **Payment Options**: Multiple payment methods (COD, Card, UPI, Net Banking)
- **Order Confirmation**: Professional confirmation page with order tracking
- **Transaction Management**: Database transactions ensure data consistency

## 🛠 Technology Stack

- **Backend**: Java Servlets, JSP
- **Database**: MySQL
- **Frontend**: HTML5, CSS3, JavaScript
- **Server**: Apache Tomcat
- **Architecture**: MVC Pattern with DAO Layer

## 📁 Project Structure

```
├── com/tap/
│   ├── dao/                    # Data Access Objects
│   │   ├── MenuDAO.java
│   │   ├── UserRepository.java
│   │   └── RestaurantRepositoryImpl.java
│   ├── daoimplementations/     # DAO Implementations
│   │   ├── MenuDAOImpl.java
│   │   └── CartDAOImpl.java
│   ├── model/                  # Data Models
│   │   ├── User.java
│   │   ├── Restaurant.java
│   │   ├── Menu.java
│   │   ├── Cart.java
│   │   ├── CartItem.java
│   │   ├── Order.java
│   │   └── OrderItem.java
│   ├── servlets/              # Servlet Controllers
│   │   ├── SignupServlet.java
│   │   ├── SigninServlet.java
│   │   ├── RestaurantServlet.java
│   │   ├── MenuServlet.java
│   │   ├── CartServlet.java
│   │   ├── OrderServlet.java
│   │   └── OrderConfirmationServlet.java
│   └── utility/               # Utility Classes
│       └── DBConnection.java
├── *.jsp                      # JSP Pages
├── *.html                     # Static HTML Pages
├── style.css                  # Stylesheet
└── *.sql                      # Database Scripts
```

## 🗄 Database Schema

### Core Tables
- **user**: User accounts and profiles
- **restaurant**: Restaurant information and details
- **menu**: Menu items with pricing and availability
- **orders**: Order records with delivery information
- **orderitems**: Individual items within orders
- **cart_items**: Shopping cart persistence (optional)

### Key Features
- Foreign key relationships for data integrity
- Indexes for optimized query performance
- Timestamp tracking for orders and user activity
- Flexible address and payment method storage

## 🚦 Getting Started

### Prerequisites
- Java 8 or higher
- Apache Tomcat 9+
- MySQL 8.0+
- Web browser

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd food-delivery-app
   ```

2. **Database Setup**
   ```sql
   -- Create database
   CREATE DATABASE food_delivery;
   USE food_delivery;
   
   -- Run schema
   SOURCE database_schema.sql;
   
   -- Insert sample data (optional)
   SOURCE sample_data.sql;
   ```

3. **Configure Database Connection**
   Update `com/tap/utility/DBConnection.java` with your database credentials:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/food_delivery";
   private static final String USERNAME = "your_username";
   private static final String PASSWORD = "your_password";
   ```

4. **Deploy to Tomcat**
   - Copy project files to Tomcat webapps directory
   - Start Tomcat server
   - Access application at `http://localhost:8080/your-app-name`

## 🎯 Usage Flow

### For Customers
1. **Browse Restaurants**: Visit homepage to see available restaurants
2. **Sign Up/Login**: Create account or login to existing account
3. **Browse Menu**: Select restaurant and view menu items
4. **Add to Cart**: Use quantity controls to add items to cart
5. **Checkout**: Fill delivery details and select payment method
6. **Order Confirmation**: Receive order confirmation with tracking details

### For Development
1. **Add Restaurants**: Use restaurant management interface
2. **Manage Menus**: Add/update menu items for restaurants
3. **Monitor Orders**: Track order status and customer information
4. **User Management**: Handle user accounts and profiles

## 🔧 Configuration

### Servlet Mappings
- `/restaurants` - Restaurant listing
- `/menu` - Menu display
- `/cart` - Shopping cart
- `/signin` - User authentication
- `/signup` - User registration
- `/order` - Order processing
- `/orderconformation` - Order confirmation

### Session Management
- User authentication stored in session
- Cart persistence across browser sessions
- Automatic cleanup after order completion

## 🛡 Security Features

- **SQL Injection Prevention**: Prepared statements throughout
- **Session Management**: Secure session handling
- **Input Validation**: Server-side validation for all forms
- **Authentication Checks**: Protected routes require login
- **Transaction Safety**: Database transactions for order processing

## 📱 UI/UX Features

- **Responsive Design**: Works on desktop and mobile devices
- **Modern Interface**: Zomato-inspired design with smooth animations
- **Real-time Updates**: Live cart updates and quantity controls
- **User Feedback**: Clear success/error messages
- **Intuitive Navigation**: Easy-to-use interface with logical flow

## 🔍 API Endpoints

### Authentication
- `POST /signin` - User login
- `POST /signup` - User registration
- `GET /signin?action=logout` - User logout

### Restaurant & Menu
- `GET /restaurants` - List all restaurants
- `GET /menu?restaurantId={id}` - Get restaurant menu

### Cart Management
- `POST /cart` - Add/update cart items
- `GET /cart` - View cart contents

### Order Processing
- `POST /order` - Place new order
- `GET /orderconformation` - Order confirmation page

## 🐛 Troubleshooting

### Common Issues
1. **Database Connection**: Check MySQL service and credentials
2. **JSP Not Found**: Ensure proper servlet mappings
3. **Session Issues**: Clear browser cache and cookies
4. **Cart Problems**: Check session storage and restaurant validation

### Debug Files
- Various `debug_*.jsp` files for testing components
- `test_*.jsp` files for isolated feature testing
- SQL test files for database validation

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/new-feature`)
3. Commit changes (`git commit -am 'Add new feature'`)
4. Push to branch (`git push origin feature/new-feature`)
5. Create Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For support and questions:
- Create an issue in the repository
- Check existing documentation
- Review debug files for troubleshooting

---

**Built with ❤️ using Java Servlets and JSP**
