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

</div>

## 📚 Overview

ArchivumLibris API provides endpoints for managing books, users, and purchases with a clean, maintainable architecture.
The system allows for:

- **Book catalog management** - Create, update, search and delete books
- **User management** - Registration, authentication and profile management
- **Purchase processing** - Create and track book orders

The project follows **Hexagonal Architecture** (Ports and Adapters) principles with Feature Slices organization to
achieve excellent separation of concerns and maintainability.

## 🔷 Architecture

This project implements a modern approach to software architecture:

- **Domain-Driven Design** - Focus on the core domain and domain logic
- **Hexagonal Architecture** - Clear separation between application core and external dependencies
- **Feature Slices** - Organize code by feature rather than technical layers

## 🧩 Key Concepts

The architecture supports the core object-oriented principles:

- **Encapsulation** - Through well-defined boundaries
- **Inheritance** - In domain models where appropriate
- **Polymorphism** - Via interfaces at architecture boundaries
- **Abstraction** - Through ports defining clear contracts

## 🚀 Features

### 📖 Book Management

Complete book catalog management with rich metadata support:

- Create, read, update, and delete books
- Search and filter books by various criteria
- Manage book categories and authors

### 👥 User Management

Comprehensive user system:

- User registration and authentication
- Profile management
- Role-based access control

### 🛒 Purchase Processing

End-to-end purchase flow:

- Shopping cart functionality
- Purchase history

## 🛠️ Getting Started

### Prerequisites

- JDK 21+
- Maven 3.8+
- PostgreSQL

### Installation

1. Clone the repository
   ```bash
   git clone https://github.com/brunoliratm/ArchivumLibris-API.git
   ```

2. Navigate to the project directory
   ```bash
   cd ArchivumLibris-API
   ```

3. Build the project
   ```bash
   ./mvnw clean install
   ```

4. Run the application
   ```bash
   ./mvnw spring-boot:run
   ```

## 📋 API Documentation

API documentation is available via Swagger UI at `/swagger-ui.html` after starting the application.

## 🤝 Contributing

Contributions make the open source community an amazing place to learn, inspire, and create. Any contributions you make
are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.

## 📮 Contact

Project Link: [https://github.com/brunoliratm/ArchivumLibris-API](https://github.com/brunoliratm/ArchivumLibris-API)
