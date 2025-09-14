# Kando Backend

Kando Backend is a Spring Boot REST API for managing tasks, categories, and labels. It is designed to serve as the backend for a productivity or task management application.

## Features
- CRUD operations for Tasks, Categories, and Labels
- Task prioritization and status management
- RESTful API endpoints
- Exception handling
- CORS configuration
- Database migration with Flyway

## Technologies Used
- Java 17+
- Spring Boot
- Spring Data JPA
- Flyway (database migrations)
- Maven
- Docker Compose (optional)

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven
- Docker (optional, for containerized setup)

### Setup & Run
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/kando_backend.git
   cd kando_backend
   ```
2. Build and run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
   Or, using Docker Compose:
   ```bash
   docker-compose up --build
   ```
3. The API will be available at `http://localhost:8080` by default.

### Database
- Uses Flyway for migrations (see `src/main/resources/db/migration/`)
- Default configuration is in `src/main/resources/application.yml`

## API Overview
- Endpoints for managing tasks, categories, and labels
- See `TaskController.java` for details

## Folder Structure
```
kando_backend/
├── src/
│   ├── main/
│   │   ├── java/dev/simoncodes/kando_backend/
│   │   │   ├── domain/        # Entity classes
│   │   │   ├── dto/           # Data Transfer Objects
│   │   │   ├── mapper/        # Mappers
│   │   │   ├── repo/          # Repositories
│   │   │   ├── service/       # Services
│   │   │   ├── utils/         # Utilities
│   │   │   ├── web/           # Controllers & Exception Handling
│   │   ├── resources/
│   │   │   ├── application.yml
│   │   │   └── db/migration/
│   └── test/
│       └── java/dev/simoncodes/kando_backend/
├── pom.xml
├── docker-compose.yml
└── README.md
```

## Contributing
Contributions are welcome! Please open issues or submit pull requests for improvements or bug fixes.

## License
This project is licensed under the MIT License.

