# Spring Boot Board

> **Note:** This is my first Spring Boot project—a full-featured board application with JWT authentication, role-based access control, and an admin dashboard.

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

A modern web application built with Spring Boot 3.5.6 and Java 21, featuring JWT authentication, role-based authorization (USER/ADMIN), and a clean Thymeleaf-based UI. This project demonstrates core Spring ecosystem patterns including Spring Security, Spring Data JPA, and RESTful API design.

---

## 📋 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Project Structure](#project-structure)
- [Usage](#usage)
- [Development Notes](#development-notes)
- [Contributing](#contributing)
- [License](#license)

---

## ✨ Features

### Core Functionality
- **User Authentication & Authorization**
  - JWT-based authentication
  - Session-based authentication (form login)
  - Role-based access control (ROLE_USER, ROLE_ADMIN)
  - Secure password hashing with BCrypt

### Board Features
- Create, read, and delete posts
- Author attribution and timestamps
- User profile management
- Post ownership validation

### Admin Dashboard
- Admin-only access control
- View all posts across the system
- Delete any post (administrative override)
- User management interface

### Security & Best Practices
- Spring Security 6 integration
- JWT token generation and validation
- Method-level security with `@PreAuthorize`
- CSRF protection (configurable)
- Automatic timestamp auditing with JPA

---

## 🛠 Tech Stack

### Backend
- **Java 21** - Latest LTS version
- **Spring Boot 3.5.6** - Application framework
- **Spring Security 6** - Authentication & authorization
- **Spring Data JPA** - ORM and database interaction
- **Hibernate 6** - JPA implementation

### Frontend
- **Thymeleaf** - Server-side templating
- **Thymeleaf Spring Security** - Security dialect for UI

### Database
- **MySQL 8.0** - Relational database
- **HikariCP** - High-performance connection pooling

### Libraries & Tools
- **Lombok 1.18.34** - Reduce boilerplate code
- **JJWT 0.11.5** - JWT creation and parsing
- **SpringDoc OpenAPI 2.3.0** - API documentation (Swagger UI)
- **Bean Validation** - Input validation

### Build & Dev Tools
- **Maven 3** - Dependency management and build
- **Spring Boot DevTools** - Hot reload during development

---

## 📦 Prerequisites

Before running this project, ensure you have the following installed:

- **Java Development Kit (JDK) 21** or higher
  - [Download OpenJDK 21](https://jdk.java.net/21/)
  - [Download Oracle JDK 21](https://www.oracle.com/java/technologies/downloads/#java21)
- **Maven 3.8+** (or use the included Maven wrapper)
- **MySQL 8.0+** (or compatible database)
- **Git** (for cloning the repository)

---

## 🚀 Installation

### 1. Clone the Repository

```bash
git clone https://github.com/murasakijyuutann/spring_boot_board.git
cd spring_boot_board
```

### 2. Set Up the Database

Create a MySQL database for the application:

```sql
CREATE DATABASE board_project CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. Configure Database Connection

Update `src/main/resources/application.yml` with your MySQL credentials:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/board_project?serverTimezone=UTC&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull
    username: your_username
    password: your_password
```

### 4. Build the Project

```bash
# Using Maven wrapper (recommended)
./mvnw clean package

# Or using system Maven
mvn clean package
```

---

## ⚙️ Configuration

### Application Properties

The application uses `application.yml` for configuration. Key settings:

```yaml
server:
  port: 8081  # Application port

spring:
  jpa:
    hibernate:
      ddl-auto: update  # Auto-create/update schema
    show-sql: true      # Log SQL queries

  datasource:
    url: jdbc:mysql://localhost:3306/board_project?...
    username: root
    password: your_password

springdoc:
  swagger-ui:
    path: /swagger-ui.html  # Swagger UI endpoint
  api-docs:
    path: /api-docs         # OpenAPI JSON endpoint
```

### Environment Profiles

- **Default Profile**: Uses `application.yml`
- **Production Profile**: Uses `application-prod.yml` (configured for AWS RDS in this project)

To run with production profile:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

---

## 🏃 Running the Application

### Option 1: Using Maven

```bash
mvn spring-boot:run
```

### Option 2: Running the JAR

```bash
java -jar target/springboard-jwt-0.0.1-SNAPSHOT.jar
```

### Option 3: Using Your IDE

Run the main class:
```
mjyuu.spring_boot_board.SpringBootBoardApplication
```

The application will start on **http://localhost:8081**

---

## 📚 API Documentation

### Swagger UI

Interactive API documentation is available at:

**http://localhost:8081/swagger-ui.html**

### OpenAPI Specification

Raw OpenAPI JSON:

**http://localhost:8081/api-docs**

### Key Endpoints

#### Authentication
- `POST /auth/register` - Register a new user
- `POST /auth/login` - Login and receive JWT token

#### Posts
- `GET /api/posts` - List all posts
- `GET /api/posts/{id}` - Get post by ID
- `POST /api/posts` - Create a new post (authenticated)
- `DELETE /api/posts/{id}` - Delete post (author or admin)

#### Users
- `GET /api/users` - List all users (authenticated)

#### Admin
- `GET /admin` - Admin dashboard (ROLE_ADMIN only)
- `POST /admin/posts/{id}/delete` - Delete any post (admin override)

#### Web Pages
- `GET /` - Home page (post list)
- `GET /login` - Login page
- `GET /register` - Registration page
- `GET /users` - Users list page
- `GET /posts/{id}` - View post details
- `GET /posts/create` - Create post form
- `POST /posts/create` - Submit new post

---

## 📁 Project Structure

```
spring_boot_board/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── mjyuu/spring_boot_board/
│   │   │       ├── config/              # Security & app configuration
│   │   │       │   ├── SecurityConfig.java
│   │   │       │   └── JwtFilter.java
│   │   │       ├── controller/          # REST & web controllers
│   │   │       │   ├── AuthController.java
│   │   │       │   ├── PostController.java
│   │   │       │   ├── UserController.java
│   │   │       │   ├── AdminController.java
│   │   │       │   └── PageController.java
│   │   │       ├── dto/                 # Data Transfer Objects
│   │   │       │   ├── LoginDTO.java
│   │   │       │   ├── RegisterDTO.java
│   │   │       │   ├── PostDTO.java
│   │   │       │   └── UserSummaryDTO.java
│   │   │       ├── entity/              # JPA entities
│   │   │       │   ├── User.java
│   │   │       │   ├── Post.java
│   │   │       │   └── Role.java
│   │   │       ├── repository/          # Spring Data repositories
│   │   │       │   ├── UserRepository.java
│   │   │       │   └── PostRepository.java
│   │   │       ├── security/            # Security utilities
│   │   │       │   ├── JwtUtil.java
│   │   │       │   ├── CustomUserDetails.java
│   │   │       │   └── CustomUserDetailsService.java
│   │   │       ├── service/             # Business logic
│   │   │       │   ├── AuthService.java
│   │   │       │   └── PostService.java
│   │   │       └── SpringBootBoardApplication.java
│   │   └── resources/
│   │       ├── application.yml           # Default configuration
│   │       ├── application-prod.yml      # Production config
│   │       └── templates/                # Thymeleaf templates
│   │           ├── index.html
│   │           ├── login.html
│   │           ├── register.html
│   │           ├── users.html
│   │           ├── admin.html
│   │           ├── post-create.html
│   │           └── post-detail.html
│   └── test/                             # Unit & integration tests
├── pom.xml                               # Maven dependencies
├── README.md
└── Dockerfile                            # Container configuration
```

---

## 💡 Usage

### Registering a User

1. Navigate to **http://localhost:8081/register**
2. Fill in email, password, and nickname
3. Submit the form
4. You'll be redirected to the login page

### Logging In

1. Go to **http://localhost:8081/login**
2. Enter your email and password
3. After successful login, you'll be redirected to the home page

### Creating a Post

1. Click **"Create Post"** in the navigation bar
2. Enter a title and content
3. Submit the form
4. Your post will appear on the home page

### Admin Access

To access the admin dashboard, you need a user with `ROLE_ADMIN`:

1. Manually update a user in the database:
   ```sql
   UPDATE users SET role = 'ROLE_ADMIN' WHERE email = 'admin@example.com';
   ```
2. Login with that account
3. Navigate to **http://localhost:8081/admin**
4. You can now view and delete any post in the system

### Using JWT Authentication (REST API)

1. **Register** via `POST /auth/register`
   ```json
   {
     "email": "user@example.com",
     "password": "securepassword",
     "nickname": "User"
   }
   ```

2. **Login** via `POST /auth/login`
   ```json
   {
     "email": "user@example.com",
     "password": "securepassword"
   }
   ```
   Response:
   ```json
   {
     "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
   }
   ```

3. **Make Authenticated Requests**
   Add the JWT token to the `Authorization` header:
   ```
   Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
   ```

---

## 🔧 Development Notes

### Database Schema

The application uses JPA with Hibernate's `ddl-auto: update` mode, which automatically creates and updates the database schema based on entity definitions.

**Main Tables:**
- `users` - User accounts with roles and timestamps
- `post` - Board posts with author references and timestamps

### Security Architecture

- **JWT Authentication**: Used for REST API access
- **Session Authentication**: Used for web UI access (Thymeleaf pages)
- **Dual Authentication Support**: The application supports both JWT tokens and session-based authentication simultaneously

### Role-Based Access Control

The application implements two roles:
- **ROLE_USER**: Default role for registered users
  - Can create and delete own posts
  - Can view all posts and users
- **ROLE_ADMIN**: Administrative role
  - All USER permissions
  - Can delete any post (admin override)
  - Access to admin dashboard

### JPA Auditing

The application uses Spring Data JPA auditing to automatically populate timestamps:
- `@CreatedDate` on `User.createdAt` and `Post.createdAt`
- Enabled via `@EnableJpaAuditing` in the main application class

### Known Limitations

- **Zero Date Handling**: The application handles MySQL zero-date values (`0000-00-00 00:00:00`) by converting them to `null` via the JDBC URL parameter `zeroDateTimeBehavior=convertToNull`
- **CSRF**: Currently disabled for ease of development (can be enabled in production)
- **JWT Secret Key**: Generated in-memory (should be externalized for production)

---

## 🤝 Contributing

Contributions are welcome! This is a learning project, so feedback and suggestions are especially appreciated.

### How to Contribute

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Style

- Follow standard Java conventions
- Use Lombok annotations to reduce boilerplate
- Write meaningful commit messages
- Add comments for complex logic

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- Spring Boot and the Spring Framework team for excellent documentation
- The Java community for comprehensive learning resources
- Baeldung, Spring.io guides, and Stack Overflow for troubleshooting help

---

## 📧 Contact

For questions or feedback, feel free to reach out:

- **GitHub**: [yourusername](https://github.com/yourusername)
- **Email**: your.email@example.com

---

**Built with ❤️ as a first Spring Boot learning project**
