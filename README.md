# Employee Management System

REST API for managing employee records - Built for TechCorp Solutions

## Quick Start

```bash
# Build and run
mvn clean package
java -jar target/employee-management-system.jar
```

## API Endpoints

- Health Check: `GET http://localhost:8081/api/health`
- Get All Employees: `GET http://localhost:8081/api/employees`
- Get Employee: `GET http://localhost:8081/api/employees/{id}`
- Create Employee: `POST http://localhost:8081/api/employees`
- Update Employee: `PUT http://localhost:8081/api/employees/{id}`
- Delete Employee: `DELETE http://localhost:8081/api/employees/{id}`

## Testing

```bash
mvn test
```

## Tech Stack

- Java 11
- Spring Boot 3.1.5
- Maven
- H2 Database
