# MySQL Setup Instructions

## Quick Fix: Using H2 for Testing

The application is currently configured to use **H2 in-memory database** for quick testing. You can run it immediately:

```bash
mvn spring-boot:run
```

Then access:
- API: http://localhost:8080/api/products
- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: (empty)

## Setting Up MySQL (Production)

### Option 1: Install MySQL via Homebrew (macOS)

```bash
# Install MySQL
brew install mysql

# Start MySQL service
brew services start mysql

# Secure installation (optional but recommended)
mysql_secure_installation

# Connect to MySQL
mysql -u root -p
```

### Option 2: Install MySQL via Official Installer

1. Download MySQL from: https://dev.mysql.com/downloads/mysql/
2. Install the MySQL Community Server
3. Follow the installation wizard
4. Note the root password you set during installation

### Create Database and User

Once MySQL is running, execute:

```sql
-- Connect to MySQL
mysql -u root -p

-- Create database
CREATE DATABASE ecomdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Create user
CREATE USER 'ecom_user'@'localhost' IDENTIFIED BY 'ecom_password';

-- Grant privileges
GRANT ALL PRIVILEGES ON ecomdb.* TO 'ecom_user'@'localhost';
FLUSH PRIVILEGES;

-- Verify
SHOW DATABASES;
EXIT;
```

### Switch Application to MySQL

Edit `src/main/resources/application.properties`:

1. Comment out H2 configuration:
```properties
# spring.h2.console.enabled=true
# spring.datasource.url=jdbc:h2:mem:testdb
# spring.datasource.username=sa
# spring.datasource.password=
```

2. Uncomment MySQL configuration:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecomdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=ecom_user
spring.datasource.password=ecom_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=true
spring.h2.console.enabled=false
```

3. Restart the application:
```bash
mvn spring-boot:run
```

### Verify MySQL Connection

Test the connection:
```bash
mysql -u ecom_user -p ecomdb
```

If successful, you'll see the MySQL prompt.

## Troubleshooting

### "Connection refused" Error
- **Check if MySQL is running:**
  ```bash
  brew services list | grep mysql
  # or
  ps aux | grep mysql
  ```
- **Start MySQL:**
  ```bash
  brew services start mysql
  # or
  sudo systemctl start mysql  # Linux
  ```

### "Access denied" Error
- Verify username and password in `application.properties`
- Check user privileges:
  ```sql
  SHOW GRANTS FOR 'ecom_user'@'localhost';
  ```

### Port Already in Use
- Check if MySQL is using port 3306:
  ```bash
  lsof -i :3306
  ```
- Change MySQL port in `application.properties` if needed

### Can't Find MySQL Command
- Add MySQL to PATH:
  ```bash
  export PATH=$PATH:/usr/local/mysql/bin
  ```
- Or use full path: `/usr/local/mysql/bin/mysql`

## Current Status

✅ **Application is configured for H2** - Ready to test immediately
⏳ **MySQL setup pending** - Follow instructions above when ready for production

