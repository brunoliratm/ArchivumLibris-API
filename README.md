<div align="center">
  <img src="assets/ArchivumLibris.svg" alt="Archivum Libris" width="50%">

  <br>

**A modern RESTful API for book management built with Spring Boot**

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat-square&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=flat-square&logo=spring&logoColor=white)](https://spring.io/projects/spring-data-jpa)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=flat-square&logo=spring-security&logoColor=white)](https://spring.io/projects/spring-security)
[![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=flat-square&logo=flyway&logoColor=white)](https://flywaydb.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-336791?style=flat-square&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=flat-square&logo=swagger&logoColor=black)](https://swagger.io/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white)](https://www.docker.com/)

</div>

## 📚 Overview

ArchivumLibris API provides endpoints for managing books, users, and purchases with a clean, maintainable architecture.
The system allows for:

- **Book catalog management** - Create, read, update, and delete books with detailed metadata
- **User management** - Registration, authentication, and profile management with role-based access
- **Purchase processing** - Create and track book purchases with payment methods

The project follows **Hexagonal Architecture** (Ports and Adapters) principles with Feature Slices organization to
achieve excellent separation of concerns and maintainability.

## 🔷 Architecture

This project implements a modern approach to software architecture:

- **Domain-Driven Design** - Focus on the core domain and domain logic
- **Hexagonal Architecture** - Clear separation between application core and external dependencies
- **Feature Slices** - Organize code by feature rather than technical layers
- **Clean Architecture** - Independent of frameworks, UI, and external agencies

### 📁 Project Structure

```
src/
├── main/java/com/archivumlibris/
│   ├── adapter/                    # Adapters layer (Ports & Adapters)
│   │   ├── in/                     # Inbound adapters (Controllers)
│   │   │   ├── auth/               # Authentication endpoints
│   │   │   ├── book/               # Book management endpoints
│   │   │   ├── purchase/           # Purchase processing endpoints
│   │   │   └── user/               # User management endpoints
│   │   └── out/                    # Outbound adapters
│   │       ├── jpa/                # JPA entities and repositories
│   │       └── persistence/        # Repository implementations
│   ├── application/                # Application layer (Use cases)
│   │   ├── seeder/                 # Data seeders
│   │   └── service/                # Business services
│   ├── domain/                     # Domain layer (Core business logic)
│   │   ├── model/                  # Domain entities and enums
│   │   └── port/                   # Interfaces (contracts)
│   │       ├── in/                 # Inbound ports (use cases)
│   │       └── out/                # Outbound ports (repositories)
│   ├── dto/                        # Data Transfer Objects
│   │   ├── request/                # Request DTOs
│   │   └── response/               # Response DTOs
│   ├── mapper/                     # Object mappers
│   ├── security/                   # Security configuration and JWT
│   ├── shared/                     # Shared configurations and utilities
│   └── exception/                  # Custom exceptions and handlers
└── resources/
    ├── db/migration/               # Flyway database migrations
    ├── application.properties      # Application configuration
    └── META-INF/                   # Spring configuration metadata
```

## 🚀 Features

### 📖 Book Management

Complete book catalog management with rich metadata support:

- ✅ Create, read, update, and delete books
- ✅ Search and filter books by title, author, publisher, and genre
- ✅ Book genre categorization (Fiction, Non-Fiction, Science, etc.)
- ✅ Price management with decimal precision
- ✅ Pagination support for large catalogs

### 👥 User Management

Comprehensive user system with security:

- ✅ User registration and authentication via JWT
- ✅ Profile management (name, email updates)
- ✅ Role-based access control (ADMIN, USER)
- ✅ Soft delete functionality (users marked as deleted)
- ✅ Password encryption with BCrypt
- ✅ Admin seeder for initial setup

### 🛒 Purchase Processing

End-to-end purchase flow:

- ✅ Purchase creation with book and user association
- ✅ Payment method tracking
- ✅ Purchase date recording
- ✅ Price tracking at time of purchase
- ✅ Purchase history retrieval

### 🔐 Security Features

- ✅ JWT-based authentication
- ✅ Role-based authorization
- ✅ Password encryption
- ✅ Automatic admin user creation
- ✅ Secure endpoints with proper access control

## 🛠️ Tech Stack

- **Backend:** Spring Boot 3.x, Java 21
- **Database:** PostgreSQL with Flyway migrations
- **Security:** Spring Security with JWT
- **Documentation:** OpenAPI 3 (Swagger)
- **Build Tool:** Maven
- **Container:** Docker & Docker Compose

## 🏃‍♂️ Getting Started

### Prerequisites

- JDK 21+
- Maven 3.8+
- PostgreSQL 15+ (or use Docker)

### � Installation & Setup

#### Option 1: Docker Compose (Recommended)

1. **Clone the repository**
   ```bash
   git clone https://github.com/brunoliratm/ArchivumLibris-API.git
   cd ArchivumLibris-API
   ```

2. **Start with Docker Compose**
   ```bash
   docker-compose up --build -d
   ```

3. **Access the application:**
   - **API:** http://localhost:8080
   - **Swagger UI:** http://localhost:8080/swagger-ui.html
   - **Database:** localhost:5432 (user: postgres, password: postgres, db: ala)

#### Option 2: Manual Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/brunoliratm/ArchivumLibris-API.git
   cd ArchivumLibris-API
   ```

2. **Setup PostgreSQL database**
   ```sql
   CREATE DATABASE ala;
   ```

3. **Configure environment variables** (optional)
   ```bash
   export SPRING_DATASOURCE_URL=localhost:5432/ala
   export SPRING_DATASOURCE_USERNAME=postgres
   export SPRING_DATASOURCE_PASSWORD=your_password
   export JWT_SECRET=your_jwt_secret
   export ADMIN_DEFAULT_EMAIL=admin@yourdomain.com
   export ADMIN_DEFAULT_PASSWORD=your_admin_password
   ```

4. **Build and run the application**
   ```bash
   # Build the project
   ./mvnw clean install

   # Run the application
   ./mvnw spring-boot:run
   ```
   
### 🚀 How to Run

1. **Start the application** using one of the methods above
2. **Access the API endpoints:**
   - **API Base URL:** http://localhost:8080
   - **Swagger UI:** http://localhost:8080/swagger-ui.html
   - **API Documentation:** http://localhost:8080/v3/api-docs

3. **Login with default admin credentials:**
   - **Email:** admin@email.com
   - **Password:** admin2025@

4. **Test the API** using Swagger UI or your preferred API client

## 📋 API Documentation

Comprehensive API documentation is available via **Swagger UI** at `/swagger-ui.html` after starting the application.

### 🔗 Main Endpoints

- **Books:** `/api/books` - CRUD operations for book management
- **Users:** `/api/users` - User management and authentication
- **Purchases:** `/api/purchases` - Purchase processing and history
- **Authentication:** `/api/auth` - Login and token management

### 🔑 Default Admin Credentials

- **Email:** admin@email.com (configurable via `ADMIN_DEFAULT_EMAIL`)
- **Password:** admin2025@ (configurable via `ADMIN_DEFAULT_PASSWORD`)

## 🏗️ Database Schema

The application uses Flyway for database migrations with the following main tables:

- **users** - User accounts with roles and soft delete
- **books** - Book catalog with metadata and pricing
- **purchases** - Purchase transactions linking users and books


## 🐳 Docker Support

The project includes:

- **Multi-stage Dockerfile** for optimized builds
- **Docker Compose** for simplified local development
- **PostgreSQL** container support with data persistence
- **Optimized Alpine-based images** for production

**Quick start with Docker Compose:**

```bash
docker-compose up --build -d
```

**Manual Docker commands:**

```bash
# Build the application
./mvnw clean package -DskipTests

# Build Docker image
docker build -t archivumlibris-api .

# Run with database
docker run --name archivumlibris-postgres \
  -e POSTGRES_DB=ala \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 -d postgres:16-alpine

docker run --name archivumlibris-api \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=host.docker.internal:5432/ala \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=postgres \
  --link archivumlibris-postgres:postgres \
  -d archivumlibris-api
```

## 🤝 Contributing

Contributions make the open source community an amazing place to learn, inspire, and create. Any contributions you make
are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

Distributed under the MIT License. See [`LICENSE`](LICENSE) for more information.

## 📮 Contact

**Project Link:** [https://github.com/brunoliratm/ArchivumLibris-API](https://github.com/brunoliratm/ArchivumLibris-API)
