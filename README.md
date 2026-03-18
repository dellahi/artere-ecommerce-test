# Artere Test

REST API for managing **categories** and **products** with hierarchical category structure.

## Tech Stack

Java 21, Spring Boot 3.4, Spring Data JPA, PostgreSQL, Flyway, SpringDoc OpenAPI (Swagger UI), Testcontainers, Docker Compose.

## Prerequisites

- **Java 21+**
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
