# 🎬 ShowTime – Cinema REST API

A **Spring Boot REST API** for managing a cinema system — including movies, theaters, halls, seats, shows, and ticket booking.  
Built with **Spring Boot**, **Hibernate (JPA)**, and **PostgreSQL**, and documented with **Swagger**.  
Frontend included for interacting with the API.

---

## Features

- **JWT Authentication** (Login / Registration)
- **Role-based access control** (`ADMIN`, `USER`)
- **CRUD operations** for:
  - Movies  
  - Theaters  
  - Halls  
  - Seats  
  - Shows (movie screenings)  
  - Tickets  
  - Genres  
  - Users  
- **OpenAPI (Swagger UI)** for API documentation  
- **PostgreSQL** database integration  
- **Frontend** connected via REST API

---

## Tech Stack

| Layer | Technology |
|-------|-------------|
| Backend | Spring Boot 3.5.5 |
| ORM | Hibernate (JPA) |
| Database | PostgreSQL |
| Security | Spring Security + JWT |
| Documentation | Springdoc OpenAPI (Swagger UI) |
| Build Tool | Maven |
| Frontend | HTML, CSS, JavaScript (Fetch API) |

---

## Project Structure

```
com.zhumagulorken.cinema.showtime
├── config/                # Security configuration (JWT, roles)
├── controller/            # REST controllers (Auth, Movie, Theater, etc.)
├── dto/                   # Data Transfer Objects
├── entity/                # JPA entities
├── enum/                  # Role enum
├── exception/             # Global exception handlers
├── repository/            # JPA repositories
├── security/
│   ├── jwt/               # JWT utilities and filters
│   └── service/           # UserDetailsService implementation
├── service/               # Business logic
└── ShowTimeApplication.java
```

---

## Configuration

**application.properties:**

```properties
spring.application.name=ShowTime
server.port=8080

spring.datasource.url=jdbc:postgresql://localhost:5432/showtime_db
spring.datasource.username=postgres
spring.datasource.password=1234567

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

jwt.secret=77a87b5b3ce481888c09ce89a87e25d819698055be3de714b5dbfa7a95b08ba47cfa4d19df3b788c7ff32786ccd57aa3904e94a1e2cc8ff7fb681266a8f8302a
jwt.expiration=3600000
```

---

## Default Users

| Role | Email | Password |
|------|--------|-----------|
| Admin | `admin@example.com` | `admin123` |
| User | `user@example.com` | `user123` |

---

## API Endpoints

### Public Endpoints (No Authentication Required)

| Method | Endpoint | Description |
|--------|-----------|-------------|
| POST   | `/auth/register` | Register a new user |
| POST   | `/auth/login` | Authenticate user and get JWT |
| GET    | `/movies` | Get all movies |
| GET    | `/movies/{id}` | Get movie by ID |
| GET    | `/movies/{id}/shows` | Get all shows for a specific movie |
| GET    | `/theaters/{theaterId}/halls/{hallId}/seats` | Get seat map for a hall |
| GET    | `/swagger-ui/index.html` | Swagger UI documentation |
| GET    | `/v3/api-docs` | OpenAPI spec |
| GET    | `/index.html`, `/about.html`, `/login.html`, `/register.html`, etc. | Static frontend pages |
| GET    | `/css/**`, `/js/**` | Static resources |

---

### User Endpoints (ROLE_USER)

| Method | Endpoint | Description |
|--------|-----------|-------------|
| GET    | `/users/{userId}/tickets` | View all tickets booked by the user |
| POST   | `/users/{userId}/tickets` | Book a ticket for a show |
| DELETE | `/users/{userId}/tickets/{ticketId}` | Cancel a specific ticket |

> Requires JWT token with `ROLE_USER`

---

### Admin Endpoints (ROLE_ADMIN)

#### Movies & Genres

| Method | Endpoint | Description |
|--------|-----------|-------------|
| POST   | `/movies` | Create a new movie |
| PUT    | `/movies/{id}` | Update movie details |
| DELETE | `/movies/{id}` | Delete a movie |
| POST   | `/genres` | Create a new genre |
| GET    | `/genres` | List all genres |
| DELETE | `/genres/{id}` | Delete a genre |

#### Shows

| Method | Endpoint | Description |
|--------|-----------|-------------|
| POST   | `/movies/{movieId}/shows` | Create a new show for a movie |
| PUT    | `/movies/{movieId}/shows/{showId}` | Update show details |
| DELETE | `/movies/{movieId}/shows/{showId}` | Delete a show |

#### Theaters

| Method | Endpoint | Description |
|--------|-----------|-------------|
| GET    | `/theaters` | Get all theaters |
| POST   | `/theaters` | Create a new theater |
| PUT    | `/theaters/{id}` | Update theater info |
| DELETE | `/theaters/{id}` | Delete a theater |

#### Halls

| Method | Endpoint | Description |
|--------|-----------|-------------|
| GET    | `/theaters/{theaterId}/halls` | Get all halls for a theater |
| POST   | `/theaters/{theaterId}/halls` | Create a new hall |
| PUT    | `/theaters/{theaterId}/halls/{hallId}` | Update hall details |
| DELETE | `/theaters/{theaterId}/halls/{hallId}` | Delete a hall |

#### Seats

| Method | Endpoint | Description |
|--------|-----------|-------------|
| GET    | `/theaters/{theaterId}/halls/{hallId}/seats` | Get all seats in a hall |
| POST   | `/theaters/{theaterId}/halls/{hallId}/seats` | Add a new seat |
| PUT    | `/theaters/{theaterId}/halls/{hallId}/seats/{seatId}` | Update seat details |
| DELETE | `/theaters/{theaterId}/halls/{hallId}/seats/{seatId}` | Delete a seat |

---

### Restricted / Denied

| Endpoint | Description |
|-----------|-------------|
| `/users/**` | Direct access to user data is denied (except tickets) |

_(Full API is available in Swagger UI.)_

---

## Swagger Documentation

Once the app is running, visit:  
**[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

---

## Frontend

The frontend (HTML + JS) is located in the `src/main/resources/static/` folder.  
It includes pages for:
- Login / Registration  
- Movie list and details  
- Seat selection and ticket booking  
- Admin dashboards for managing content  

The frontend communicates with the API using **Fetch** and **JWT tokens** stored in `localStorage`.

---

## How to Run

### Prerequisites
- Java 17+
- Maven 3+
- PostgreSQL running on `localhost:5432`

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/showtime.git
   cd showtime
   ```

2. Create the database:
   ```sql
   CREATE DATABASE showtime_db;
   ```

3. (Optional) Import initial data:
   ```bash
   psql -U postgres -d showtime_db -f db/data.sql
   ```

4. Run the app:
   ```bash
   mvn spring-boot:run
   ```

5. Open in browser:  
   `http://localhost:8080`

---

## Author
**Orken Zhumagul**  
[Email](mailto:zhumagul.orken@gmail.com) | [GitHub](https://github.com/Ork2004)
