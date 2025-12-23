
#  E-Commerce Backend Service

A robust **RESTful API** for an E-commerce platform, engineered with **Spring Boot**. This service handles multi-category product management and features a localized image handling system, eliminating the need for external cloud storage dependencies.

---

##  Key Features

* **Full CRUD Operations**: Create, Read, Update, and Delete products seamlessly.
* **Dynamic Filtering**: Specialized endpoints for category-based product listings (Mobiles, Laptops, Fashion, etc.).
* **Local Image Storage**: Built-in logic to serve product images directly from Spring Boot static resources.
* **Instant Setup**: Uses an **H2 In-Memory Database**—no complex database installation required.
* **Auto-Seeding**: Preloads comprehensive sample data via `data.sql` upon startup.
* **Layered Architecture**: Follows industry standards with distinct Controller, Service, and Repository layers.

---

##  Tech Stack

| Component | Technology |
| --- | --- |
| **Language** | Java 17+ |
| **Framework** | Spring Boot 3.x |
| **ORM** | Spring Data JPA / Hibernate |
| **Database** | H2 (In-Memory) |
| **Build Tool** | Maven |
| **Utility** | Lombok |

---

##  Project Structure

The project follows a clean, layered architecture to ensure maintainability and scalability:

* **Controller Layer**: Handles HTTP requests and maps them to API endpoints.
* **Service Layer**: Contains the core business logic.
* **Repository Layer**: Interacts with the H2 database using JPA.
* **Model Layer**: Defines the data entities (Product, Category).

---

##  Database Configuration

The application is pre-configured for a **zero-setup experience**:

* **Database**: H2 In-Memory.
* **Initialization**: The schema is automatically created on startup.
* **Data Seeding**: `src/main/resources/data.sql` automatically populates the database with categories like Electronics, Toys, and Cars.
* **H2 Console**: Access the database UI at `/h2-console` to run manual queries during development.

---

##  Getting Started

### Prerequisites

* JDK 17 or higher
* Maven 3.6+

### Installation

1. **Clone the repository**:
```bash
git clone https://github.com/sritharun00i/EcommerceApp
cd EcommerceApp

```


2. **Run the application**:
```bash
mvn spring-boot:run

```


3. **Verify the API**:
The server will start on `http://localhost:8080`.

---

##  API Endpoints (Quick Reference)

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/api/products` | Get all products |
| `GET` | `/api/products/{id}` | Get product details |
| `GET` | `/api/products/category/{name}` | Filter by category |
| `POST` | `/api/products` | Add a new product |
| `PUT` | `/api/products/{id}` | Update product details |
| `DELETE` | `/api/products/{id}` | Remove a product |

---

##  Image Handling

Images are stored in `src/main/resources/static/images/`. To view an image, use the path:
`http://localhost:8080/images/product-name.jpg`

---
