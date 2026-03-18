# Artere Test

REST API for managing **categories**, **products**, and **shopping carts** with hierarchical category structure.

## Tech Stack

Java 25, Spring Boot 3.4, Spring Data JPA, PostgreSQL, Flyway, SpringDoc OpenAPI (Swagger UI), Testcontainers, Docker Compose.

## Prerequisites

- **Java 25+**
- **Docker** & **Docker Compose**
- **Maven 3.9+**

## Getting Started

### 1. Start the database

```bash
docker compose up -d
```

### 2. Run the application

```bash
mvn spring-boot:run
```

### 3. Explore the API

Open Swagger UI at: **http://localhost:8080/swagger-ui.html**

## Connect to the Database via pgAdmin

1. Open **http://localhost:5050**
2. Login with `admin@artere.local` / `admin`
3. **Add New Server:**
   - **Name:** `artere` (or anything you like)
   - **Connection** tab:
     - **Host:** `artere-postgres`
     - **Port:** `5432`
     - **Database:** `arteredb`
     - **Username:** `postgres`
     - **Password:** `postgres`
4. Click **Save**

> **Note:** The host is `artere-postgres` (the Docker container name), not `localhost`, because pgAdmin runs inside the same Docker network.

## API Endpoints

### Categories

| Method   | URL                                                  | Description                      |
|----------|------------------------------------------------------|----------------------------------|
| `POST`   | `/api/categories`                                    | Create a category                |
| `GET`    | `/api/categories`                                    | List all categories              |
| `GET`    | `/api/categories/roots`                              | List root categories             |
| `GET`    | `/api/categories/{id}`                               | Get a category by ID             |
| `GET`    | `/api/categories/{id}/subcategories`                 | List sub-categories              |
| `PUT`    | `/api/categories/{id}`                               | Update a category                |
| `DELETE` | `/api/categories/{id}`                               | Delete a category                |
| `PUT`    | `/api/categories/{parentId}/subcategories/{childId}` | Link a sub-category              |
| `DELETE` | `/api/categories/{parentId}/subcategories/{childId}` | Unlink a sub-category            |

### Products

| Method   | URL                                               | Description                      |
|----------|---------------------------------------------------|----------------------------------|
| `POST`   | `/api/products`                                   | Create a product                 |
| `GET`    | `/api/products`                                   | List all products                |
| `GET`    | `/api/products/{id}`                              | Get a product by ID              |
| `PUT`    | `/api/products/{id}`                              | Update a product                 |
| `DELETE` | `/api/products/{id}`                              | Delete a product                 |
| `PUT`    | `/api/products/{productId}/category/{categoryId}` | Assign product to a category     |
| `DELETE` | `/api/products/{productId}/category`              | Remove product from its category |

### Cart

| Method   | URL                                    | Description                                          |
|----------|----------------------------------------|------------------------------------------------------|
| `POST`   | `/api/carts`                           | Create an empty cart                                 |
| `GET`    | `/api/carts/{cartId}`                  | Get cart with items and total price                  |
| `POST`   | `/api/carts/{cartId}/items`            | Add a product to the cart (merges if already present)|
| `PUT`    | `/api/carts/{cartId}/items/{itemId}`   | Update item quantity                                 |
| `DELETE` | `/api/carts/{cartId}/items/{itemId}`   | Remove an item from the cart                         |
| `DELETE` | `/api/carts/{cartId}`                  | Delete entire cart                                   |

## Running Tests

```bash
mvn test
```

Requires Docker running (uses Testcontainers).

## Stopping the Environment

```bash
docker compose down
```

Add `-v` to also remove the database volume:

```bash
docker compose down -v
```
