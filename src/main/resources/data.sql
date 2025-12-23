-- Cars
INSERT INTO product
(name, brand, description, price, category, release_date, Product_available, stock_quantity)
VALUES
    ('Swift', 'Maruti Suzuki',
     'Popular hatchback known for mileage and reliability',
     650000.00, 'Cars', '2018-05-01', TRUE, 50),

    ('Baleno', 'Maruti Suzuki',
     'Premium hatchback with spacious interior',
     850000.00, 'Cars', '2019-02-15', TRUE, 40),

    ('Creta', 'Hyundai',
     'Mid-size SUV with premium features',
     1100000.00, 'Cars', '2020-03-25', TRUE, 30),

    ('Verna', 'Hyundai',
     'Sedan with refined engine and comfort',
     1050000.00, 'Cars', '2019-08-10', TRUE, 20),

    ('Nexon', 'Tata',
     'Compact SUV with 5-star safety rating',
     900000.00, 'Cars', '2018-01-22', TRUE, 35),

    ('Harrier', 'Tata',
     'Premium SUV with bold design',
     1550000.00, 'Cars', '2019-06-05', TRUE, 15),

    ('XUV700', 'Mahindra',
     'Feature-rich SUV with ADAS',
     1700000.00, 'Cars', '2021-10-04', TRUE, 25),

    ('Thar', 'Mahindra',
     'Off-road SUV with rugged capability',
     1400000.00, 'Cars', '2020-10-02', TRUE, 18),

    ('Slavia', 'Skoda',
     'Sedan with European driving dynamics',
     1150000.00, 'Cars', '2022-02-28', TRUE, 12),

    ('Virtus', 'Volkswagen',
     'Sporty sedan with turbo engine',
     1200000.00, 'Cars', '2022-06-09', TRUE, 10);

-- Laptops
INSERT INTO product
(name, brand, description, price, category, release_date, Product_available, stock_quantity)
VALUES
    ('Inspiron 15', 'Dell', '15-inch laptop for everyday productivity', 62000.00, 'Laptop', '2021-07-15', TRUE, 25),
    ('Pavilion 14', 'HP', 'Lightweight laptop with powerful performance', 68000.00, 'Laptop', '2022-03-10', TRUE, 20),
    ('IdeaPad Slim 5', 'Lenovo', 'Slim laptop with long battery life', 72000.00, 'Laptop', '2022-09-05', TRUE, 18),
    ('MacBook Air M1', 'Apple', 'Ultra-thin laptop with M1 chip', 99000.00, 'Laptop', '2020-11-17', TRUE, 10);

-- Headphones
INSERT INTO product
(name, brand, description, price, category, release_date, Product_available, stock_quantity)
VALUES
    ('WH-1000XM5', 'Sony', 'Noise cancelling wireless headphones', 29999.00, 'Headphone', '2022-05-12', TRUE, 30),
    ('QuietComfort 45', 'Bose', 'Premium noise cancelling headphones', 28999.00, 'Headphone', '2021-09-23', TRUE, 22),
    ('AirPods Pro', 'Apple', 'Wireless earbuds with ANC', 24999.00, 'Headphone', '2021-10-18', TRUE, 35),
    ('Boat Rockerz 550', 'Boat', 'Affordable wireless headphones', 1999.00, 'Headphone', '2020-06-15', TRUE, 50);
-- Mobiles
INSERT INTO product
(name, brand, description, price, category, release_date, Product_available, stock_quantity)
VALUES
    ('iPhone 14', 'Apple', 'Premium smartphone with A15 Bionic', 69999.00, 'Mobile', '2022-09-16', TRUE, 15),
    ('Galaxy S23', 'Samsung', 'Flagship Android smartphone', 74999.00, 'Mobile', '2023-02-17', TRUE, 20),
    ('Pixel 7', 'Google', 'Clean Android experience with great camera', 59999.00, 'Mobile', '2022-10-13', TRUE, 18),
    ('Redmi Note 12', 'Xiaomi', 'Budget smartphone with AMOLED display', 15999.00, 'Mobile', '2023-01-11', TRUE, 40);
-- Electronics
INSERT INTO product
(name, brand, description, price, category, release_date, Product_available, stock_quantity)
VALUES
    ('Smart LED TV 43"', 'Samsung', '43-inch Full HD Smart TV', 32999.00, 'Electronics', '2021-08-20', TRUE, 12),
    ('Microwave Oven', 'LG', 'Convection microwave oven', 21999.00, 'Electronics', '2020-11-05', TRUE, 10),
    ('Air Conditioner 1.5T', 'Daikin', 'Energy efficient split AC', 45999.00, 'Electronics', '2022-04-01', TRUE, 8),
    ('Refrigerator 260L', 'Whirlpool', 'Frost-free double door fridge', 28999.00, 'Electronics', '2021-02-14', TRUE, 14);
-- Toys
INSERT INTO product
(name, brand, description, price, category, release_date, Product_available, stock_quantity)
VALUES
    ('LEGO Classic Box', 'LEGO', 'Creative building blocks for kids', 2999.00, 'Toys', '2019-10-10', TRUE, 40),
    ('Remote Control Car', 'Hot Wheels', 'High-speed RC racing car', 1999.00, 'Toys', '2020-12-05', TRUE, 35),
    ('Barbie Dreamhouse', 'Mattel', 'Large doll house playset', 4999.00, 'Toys', '2021-06-18', TRUE, 15),
    ('Puzzle Set 1000 pcs', 'Ravensburger', 'Challenging jigsaw puzzle', 1499.00, 'Toys', '2018-09-01', TRUE, 25);
-- Fashion
INSERT INTO product
(name, brand, description, price, category, release_date, Product_available, stock_quantity)
VALUES
    ('Men Cotton T-Shirt', 'Puma', 'Comfortable everyday wear', 1299.00, 'Fashion', '2022-03-20', TRUE, 60),
    ('Women Kurti', 'Biba', 'Ethnic wear with modern design', 2499.00, 'Fashion', '2021-08-12', TRUE, 45),
    ('Running Shoes', 'Nike', 'Lightweight sports shoes', 5999.00, 'Fashion', '2022-01-05', TRUE, 30),
    ('Leather Wallet', 'Fossil', 'Genuine leather wallet', 3499.00, 'Fashion', '2020-11-22', TRUE, 25);

