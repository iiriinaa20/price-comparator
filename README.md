# Price Comparator

## Project Description

**Price Comparator** is a Spring Boot application that allows users to compare product prices across multiple suppliers, optimize baskets, track discounts, and analyze historical price trends. The system supports importing data from CSV files and exposes a robust REST API for managing suppliers, products, alerts, and more.

---

## 1. Tech Stack

- **Backend:** Java 17, Spring Boot 3.4.6
- **Database:** MySQL
- **Security:** Spring Security (basic)
- **CSV Parsing:** OpenCSV 5.9
- **Build Tool:** Maven
- **JPA & ORM:** Spring Data JPA

---

## 2. Architecture and Structure

This project follows a layered architecture:

```
price-comparator
└── src
    └── main
        ├── java
        │   └── com.accesa.price_comparator
        │       ├── contracts         # Repository interfaces
        │       ├── controller        # REST Controllers
        │       ├── dto               # Data Transfer Objects
        │       ├── model             # JPA Entities
        │       └── service           # Business Logic
        ├── resources
        │   ├── static
        │   ├── templates
        │   ├── application.properties
        │   └── *.csv                 # Sample product and discount CSVs
```

---

## 3. How to Run

### Prerequisites

- Java 17+
- Maven 3.6+
- MySQL database

### Steps

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/price-comparator.git
   cd price-comparator
   ```

2. Configure your database connection in `src/main/resources/application.properties`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/price_comparator
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Build the project:

   ```bash
   mvn clean install
   ```

4. Run the application:

   ```bash
   mvn spring-boot:run
   ```

---

## 4. API Documentation

### 4.1 Available Endpoints & Sample Requests

#### AuthController

- `POST /api/auth/register`  Registers a new user.
  ```json
  {
    "username": "john",
    "password": "pass123"
  }
  ```

#### BasketController

- `GET /api/basket/optimize/{basketId}`  Returns the best supplier basket combinations.

#### CategoryController

- `GET /api/category` — Get all categories
- `GET /api/category/{id}`
- `POST /api/category`
  ```json
  {
    "name": "Fruits"
  }
  ```
- `PUT /api/category/{id}`
  ```json
  {
    "name": "Updated Fruits"
  }
  ```
- `DELETE /api/category/{id}`

#### ProductController

- `GET /api/product`
- `GET /api/product/{id}`
- `POST /api/product`
  ```json
  {
    "name": "Apple",
    "brand": "Golden",
    "category": {
      "id": 1
    }
  }
  ```
- `PUT /api/product/{id}`
  ```json
  {
    "name": "Green Apple",
    "brand": "Granny Smith",
    "category": {
      "id": 1
    }
  }
  ```
- `DELETE /api/product/{id}`
- `GET /api/product/price-history/trend Example: \`/api/product/price-history/trend?productId=1&supplierId=2&category=Fruits&brand=Golden\`

**Sample Response:**

```json
{
  "Kaufland": {
    "2024-05": [
      {
        "price": 3.49,
        "discountedPrice": 2.99
      }
    ],
    "2024-06": [
      {
        "price": 3.19,
        "discountedPrice": 2.89
      }
    ]
  }
}
```

Retrieves trend data for a product's price history with optional filters.

#### SupplierController

- `GET /api/supplier`
- `GET /api/supplier/{id}`
- `POST /api/supplier`
  ```json
  {
    "name": "Kaufland",
    "imageUrl": "https://example.com/logo.png",
    "websiteUrl": "https://kaufland.ro"
  }
  ```
- `PUT /api/supplier/{id}`
  ```json
  {
    "name": "Mega Image",
    "imageUrl": "https://example.com/mega.png",
    "websiteUrl": "https://mega-image.ro"
  }
  ```
- `DELETE /api/supplier/{id}`

#### SupplierProductController

- `GET /api/supplier-product`  Returns all supplier products.

- `GET /api/supplier-product/{id}`  Retrieves a specific supplier product by ID.

- `POST /api/supplier-product`

  ```json
  {
    "product": { "id": 1 },
    "supplier": { "id": 2 },
    "price": 4.99,
    "discountedPrice": 3.99,
    "quantity": 1,
    "unit": "kg"
  }
  ```

- `PUT /api/supplier-product/{id}`

  ```json
  {
    "product": { "id": 1 },
    "supplier": { "id": 2 },
    "price": 4.49,
    "discountedPrice": 3.49,
    "quantity": 1,
    "unit": "kg"
  }
  ```

- `DELETE /api/supplier-product/{id}`

- `GET /api/supplier-product/best-discounts?limit=10`  Returns products with the best discounts, limited by the `limit` param.

- `GET /api/supplier-product/new-discounts`  Returns the most recently discounted products.

- `GET /api/supplier-product/price-history?productId=1&supplierId=2`  Returns the historical price changes for a specific product and supplier.

- `GET /api/supplier-product/best-value?unit=kg&limit=5`  Returns products with the best price-per-unit ratio.

#### CsvImportController

- `POST /api/import/{supplierId}?filename=kaufland_2025-05-01.csv`  Imports a list of products from a CSV file for the given supplier.

  **Sample Request:**

  ```http
  POST /api/import/1?filename=kaufland_2025-05-01.csv
  ```

- `POST /api/import/discounts/{supplierId}?filename=kaufland_discounts_2025-05-01.csv`  Imports discount information from a CSV file for the given supplier.

  **Sample Request:**

  ```http
  POST /api/import/discounts/1?filename=kaufland_discounts_2025-05-01.csv
  ```

#### PriceAlertController

- `GET or POST /api/alerts/set?productId=1&targetPrice=9.99`
- `GET /api/alerts/check-grouped`  Returns matching alerts in grouped format.

#### Hello Controller

- `GET /`  Returns a simple greeting.

---

