# PharmacyStorage - Pharmacy Management System

A Spring Boot REST API for managing a pharmacy’s drugs, categories, and stock movements.
It supports recording in/out movements, filtering them by date and drug, and retrieving results in a clean response DTO.

## Features


## Technology Stack

- **Java 21**
- **Spring Boot 3.5.6**
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok** (Reduces boilerplate code)
- **Jakarta Validation**

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- PostgreSQL 12+

## Setup Instructions

### 1. Database Setup

1. Install and start PostgreSQL
2. Create a database named `postgres` (or update the configuration)
3. Update database credentials in `src/main/resources/application.properties` if needed

### 2. Application Setup

1. Clone the repository
2. Navigate to the project directory
3. Run the application:

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

### Base URL
```
http://localhost:8080/api/v1/Pharmacy
```
**Sample - Request**
GET /api/movements?fromDate=2025-09-01&toDate=2025-09-21&drugNames=Aspirin&drugNames=Paracetamol


**Sample - Response:**
```json
{
  "success": true,
  "data": [
    {
      "id": "7c1c0132-98ac-4b9c-bc64-1dbddc1d2212",
      "drugName": "Aspirin",
      "quantity": 10,
      "movementDate": "2025-09-10"
    },
    {
      "id": "a1c4be12-56d2-4f44-99e1-3c71cfa91234",
      "drugName": "Paracetamol",
      "quantity": 5,
      "movementDate": "2025-09-15"
    }
  ]
}

```
## Development

### Building and Running the Application
```bash
mvn spring-boot:run
```


## Project Structure

```
PHARMACYSTORAGE/
│── pom.xml
│── README.md
│
├── src/
│   ├── main/
│   │   ├── java/com/gi/pharmacyWarehouse/
│   │   │   ├── controller/
│   │   │   │   └── PharmacyController.java
│   │   │   │
│   │   │   ├── dto/
│   │   │   │   ├── DrugDTO.java
│   │   │   │   └── MovementDTO.java
│   │   │   │
│   │   │   ├── exception/
│   │   │   │   ├── DrugException.java
│   │   │   │   └── Global ExceptionHandler.java
│   │   │   │
│   │   │   ├── model/
│   │   │   │   ├── ApiError.java
│   │   │   │   ├── ApiResponse.java
│   │   │   │   ├── Drug.java
│   │   │   │   ├── DrugCategory.java
│   │   │   │   ├── Movement.java
│   │   │   │
│   │   │   ├── repository/
│   │   │   │   ├── DrugCategoryRepository.java
│   │   │   │   ├── DrugRepository.java
│   │   │   │   └── MovementRepository.java
│   │   │   │
│   │   │   ├── service/
│   │   │   │   └── PharmacyService.java
│   │   │   │
│   │   │   └── PharmacyWarehouseApplication.java
│   │   │
│   │   └── resources/
│   │       └── application.properties

```
## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE.txt file for details. 
