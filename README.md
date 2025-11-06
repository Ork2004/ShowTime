# ShowTime - Cinema REST API

ShowTime is a Spring Boot REST API for managing a cinema system.
It allows you to manage movies, theaters, halls, seats, shows, and ticket bookings.
---
## Screenshots

| Page            | Screenshot                                          |
|-----------------|-----------------------------------------------------|
| Main Page       | ![Index](docs/images/index.png)                     |
| Login           | ![Login](docs/images/login.png)                     |
| Admin Dashboard | ![Admin Dashboard](docs/images/admin-dashboard.png) |
| Manage Theaters | ![Manage Theaters](docs/images/manage-theaters.png) |
| Movie List      | ![Movie List](docs/images/movie-list.png)           |

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

---

## Swagger Documentation

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
- Java 20
- Maven 3+
- PostgreSQL running on `localhost:5432`

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/Ork2004/ShowTime.git
   cd ShowTime
   ```

2. Create the database:
   ```sql
   CREATE DATABASE showtime_db;
   ```

3. Import initial data:
   ```bash
   psql -U postgres -d showtime_db -f docs/data.sql
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
