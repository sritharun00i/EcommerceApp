# MySQL Migration Guide

This guide explains how to migrate the E-Commerce application from H2 in-memory database to MySQL.

## Prerequisites

1. **MySQL Server** (version 8.0 or higher recommended)
2. **MySQL Client** or MySQL Workbench for database administration
3. **Java 21** and **Maven** (already configured)

## Step 1: Install and Setup MySQL

### Install MySQL
- **macOS**: `brew install mysql` or download from [MySQL Downloads](https://dev.mysql.com/downloads/mysql/)
- **Linux**: `sudo apt-get install mysql-server` (Ubuntu/Debian) or `sudo yum install mysql-server` (RHEL/CentOS)
- **Windows**: Download and install from MySQL official website

### Start MySQL Service
```bash
# macOS (Homebrew)
brew services start mysql

# Linux
sudo systemctl start mysql
sudo systemctl enable mysql

# Windows
# Start MySQL service from Services panel
```

### Create Database and User
```sql
-- Connect to MySQL as root
mysql -u root -p

-- Create database
CREATE DATABASE ecomdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Create user (or use existing root user)
CREATE USER 'ecom_user'@'localhost' IDENTIFIED BY 'ecom_password';

-- Grant privileges
GRANT ALL PRIVILEGES ON ecomdb.* TO 'ecom_user'@'localhost';
FLUSH PRIVILEGES;

-- Exit MySQL
EXIT;
```

## Step 2: Update Application Configuration

The `application.properties` file has been updated with MySQL configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecomdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=ecom_user
spring.datasource.password=ecom_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

**Important**: Update the username and password in `application.properties` to match your MySQL setup.

## Step 3: Initialize Database Schema

You have two options:

### Option A: Let Hibernate Create Tables (Development)
Set `spring.jpa.hibernate.ddl-auto=update` in `application.properties` (already configured).
The schema will be created automatically on first run.

### Option B: Manual Schema Creation (Production Recommended)
Execute the `schema.sql` file manually:

```bash
mysql -u ecom_user -p ecomdb < src/main/resources/schema.sql
```

Or using MySQL Workbench:
1. Open MySQL Workbench
2. Connect to your MySQL server
3. Select `ecomdb` database
4. File → Open SQL Script → Select `schema.sql`
5. Execute the script

## Step 4: Load Sample Data (Optional)

The `data.sql` file contains sample products. It will be executed automatically if:
- `spring.jpa.defer-datasource-initialization=true` is set (already configured)
- Tables are empty

To manually load data:
```bash
mysql -u ecom_user -p ecomdb < src/main/resources/data.sql
```

## Step 5: Build and Run Application

```bash
# Clean and build
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will:
1. Connect to MySQL database
2. Create/update tables if `ddl-auto=update`
3. Load sample data from `data.sql`

## Important Notes

### Duplicate User Entities
The codebase contains two User entity classes:
1. **`com.learn.Ecom.model.User`** - Used by Order system (updated with timestamps and indexes)
2. **`com.ecommerce.model.User`** - May be used by authentication/security module

Both map to the same `users` table. The Order system uses `com.learn.Ecom.model.User`. If you have authentication code using `com.ecommerce.model.User`, you may want to:
- Consolidate to a single User entity, or
- Ensure both entities have compatible field mappings

The schema.sql creates a single `users` table that works with both entities.

## Database Schema Overview

### Tables Created

1. **users** - User accounts
   - Indexes: `email` (unique), `role`
   
2. **products** - Product catalog
   - Indexes: `category`, `brand`, `category+price`, `product_available`, `release_date`, `name`
   - Full-text index: `name`, `description`, `brand`, `category`
   
3. **orders** - Customer orders
   - Indexes: `user_id`, `order_status`, `created_at`, `user_id+created_at`
   - Foreign key: `user_id` → `users.id`
   
4. **order_items** - Order line items
   - Indexes: `order_id`, `product_id`
   - Foreign keys: `order_id` → `orders.id`, `product_id` → `products.id`

### Index Strategy

**Users Table:**
- `idx_users_email`: Fast user lookup by email (login)
- `idx_users_role`: Filter users by role

**Products Table:**
- `idx_products_category`: Filter by category
- `idx_products_brand`: Filter by brand
- `idx_products_category_price`: Efficient category filtering with price sorting
- `idx_products_available`: Filter available products
- `idx_products_release_date`: Sort by release date (new arrivals)
- `idx_products_name`: Product name lookup
- `ft_products_search`: Full-text search across name, description, brand, category

**Orders Table:**
- `idx_orders_user_id`: Fast order lookup by user
- `idx_orders_status`: Filter orders by status
- `idx_orders_created_at`: Sort orders by date
- `idx_orders_user_created`: Optimized query for user's order history

**Order Items Table:**
- `idx_order_items_order_id`: Fast item lookup for an order
- `idx_order_items_product_id`: Find all orders containing a product

## API Endpoints

### Product Endpoints (Existing)
- `GET /api/products` - Get all products
- `GET /api/product/{id}` - Get product by ID
- `POST /api/product` - Create product
- `PUT /api/product/{id}` - Update product
- `DELETE /api/product/{id}` - Delete product
- `GET /api/products/search?keyword=...` - Search products

### Order Endpoints (New)
- `POST /api/orders/user/{userId}` - Create order
- `GET /api/orders/{orderId}` - Get order by ID
- `GET /api/orders/user/{userId}` - Get all orders for a user
- `GET /api/orders` - Get all orders (admin)
- `GET /api/orders/status/{status}` - Get orders by status
- `PUT /api/orders/{orderId}/status?status=...` - Update order status
- `PUT /api/orders/{orderId}/cancel` - Cancel order

## Troubleshooting

### Connection Issues
- Verify MySQL is running: `mysqladmin -u root -p ping`
- Check MySQL port (default: 3306)
- Verify database and user exist
- Check firewall settings

### Authentication Errors
- Verify username and password in `application.properties`
- Ensure user has proper privileges: `SHOW GRANTS FOR 'ecom_user'@'localhost';`

### Table Creation Issues
- Check MySQL version compatibility (8.0+ recommended)
- Verify character set: `SHOW VARIABLES LIKE 'character_set%';`
- Ensure InnoDB engine is available: `SHOW ENGINES;`

### Data Loading Issues
- Check `data.sql` syntax (table names are case-sensitive in MySQL)
- Verify foreign key constraints are satisfied
- Check for duplicate entries (unique constraints)

## Production Recommendations

1. **Change `ddl-auto` to `validate`**:
   ```properties
   spring.jpa.hibernate.ddl-auto=validate
   ```
   This prevents accidental schema changes in production.

2. **Use Connection Pooling**:
   Consider adding HikariCP configuration:
   ```properties
   spring.datasource.hikari.maximum-pool-size=10
   spring.datasource.hikari.minimum-idle=5
   ```

3. **Enable SSL**:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ecomdb?useSSL=true&requireSSL=true
   ```

4. **Backup Strategy**:
   Set up regular MySQL backups:
   ```bash
   mysqldump -u ecom_user -p ecomdb > backup_$(date +%Y%m%d).sql
   ```

5. **Monitor Performance**:
   - Use MySQL's `EXPLAIN` to analyze query performance
   - Monitor slow query log
   - Review index usage

## Rollback Plan

If you need to rollback to H2:
1. Change `application.properties` back to H2 configuration
2. Remove MySQL dependency from `pom.xml` (or keep it)
3. Restart application

Note: Data in MySQL will remain intact and can be migrated back if needed.

