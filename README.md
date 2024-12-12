
# Employee Management System API Documentation

## Overview
The Employee Management System facilitates efficient management of employee records through the EmployeeManagementAPI. It incorporates role-based access controls and relies on a secure internal service for database interactions.

## Key Features
## 1. Employee Management API:

- Perform CRUD operations (Create, Retrieve, Update, Delete) on employee records.
- Use surname as the primary identifier (userName) for all user-related operations.
## 2. Authentication & Authorization:

- Secure endpoints using JWT tokens.
- Enforce role-based access controls to restrict actions based on user roles.
## 3. Internal Integration:

- EmployeeManagementAPI interacts with EmployeeDataAPI to handle all database-related operations.
- This design ensures a clear separation of responsibilities, improving scalability and maintainability.
## Request-Response Flow
### Authentication:

User will generate a JWT token encoding their roles.
### Role Validation:

- Every request is validated against the user’s role in Role filter.
### Retry Mechanism :
- EmployeeDataAPI is resilient and retries three times in case of any technical issues.
### Database Interaction:

The EmployeeManagementAPI forwards valid requests to the EmployeeDataAPI for data storage or retrieval.
### Role-Based Access Controls
Actions are restricted to authorized roles:

- Admin: Can create and delete a employee.
- User: Restricted to viewing  and update their own information.
### Internal Communication
- EmployeeManagementAPI serves as the public interface for clients.
- EmployeeDataAPI handles all database operations, ensuring data consistency and enforcing business rules.

### Tech Stack Used
1.	Java 17
2.	Spring Boot 3 (Spring Security, Spring JPA, Spring Retry)
3.  Maven
4.  Docker
5. Mapstruct

* ****

## Base URL
- `http://localhost:8080`
- Swagger-UI URL 'http://localhost:8080/swagger-ui/index.html'

## Security
The API uses Bearer Authentication (JWT). Include a valid token in the `Authorization` header of your requests to access the protected endpoints.

### Example:
```
Authorization: Bearer <JWT_TOKEN>
```

## Endpoints

### 1. Generate Authentication Token
- **Endpoint:** `POST /auth/token`
- **Description:** Generate a JWT token using for authenticated access to the API.
- **Request Body:**
```json
{
  "username": "admin"
}
```
- **Response:**
```json
"JWT_TOKEN"
``` 
### 2. Create Employee
- **Endpoint:** `POST /employees`
- **Description:** Create a new employee record in the system.
- **Request Header:**
    - `Role`: Role of the user  (e.g., `ADMIN`).
- **Request Body:**
```json
{
  "first_name": "John",
  "surname": "Doe"
}
```
- **Response:**
```json
{
  "id": 1,
  "first_name": "John",
  "surname": "Doe",
  "role_id": 1
}
```

### 3. Get Employee by ID
- **Endpoint:** `GET /employees/{id}`
- **Description:** Retrieve details of an employee by their unique ID.
- **Request Header:**
    - `Role`: Role of the user (e.g., `USER`).
- **Parameters:**
    - `id`: Unique identifier of the employee.
- **Response:**
```json
{
  "id": 1,
  "first_name": "John",
  "surname": "Doe",
  "role_id": 1
}
```

### 4. Update Employee
- **Endpoint:** `PUT /employees/{id}`
- **Description:** Update the details of an existing employee identified by their unique ID.
- **Request Header:**
    - `Role`: Role of the user (e.g., `USER`).
- **Request Body:**
```json
{
  "first_name": "Jane",
  "surname": "Doe"
}
```
- **Response:**
```json
{
  "id": 1,
  "first_name": "Jane",
  "surname": "Doe",
  "role_id": 2
}
```

### 5. Delete Employee
- **Endpoint:** `DELETE /employees/{id}`
- **Description:** Delete an employee from the system based on their unique ID.
- **Request Header:**
    - `Role`: Role of the user (e.g., `ADMIN`).
- **Response:**
```json
{
  "message": "Employee deleted successfully"
}
```

## Data Models

### EmployeeRequest
- **firstName**: The first name of the employee.
- **surName**: The surname of the employee.
- **roleId**: The role ID associated with the employee.

### EmployeeResponse
- **id**: Unique identifier of the employee.
- **firstName**: The first name of the employee.
- **surName**: The surname of the employee.
- **roleId**: The role ID associated with the employee.

### AuthRequest
- **username**: The username for authentication (surName consider as userName).

### ResponseMessage
- **message**: A success message related to the Delete employee operation.


## Usage Example
1. **Creating a JWT Token:**

    - Send a `POST` request to `/auth/token` with the userName in the body  (Please use admin as userName first to create token).

2. **Creating a new employee:**

    - Send a `POST` request to `/employees` with the employee details in the body and a `Role` in the header (e.g., `Role: admin` same role will be assigned to employee).

3. **Retrieving employee details by ID:**

    - Send a `GET` request to `/employees/{id}` with the employee ID in the URL and a `Role` in the header (e.g., `Role: ADMIN`).

4. **Updating an employee:**

    - Send a `PUT` request to `/employees/{id}` with the updated employee details and a `Role` in the header.

5. **Deleting an employee:**

    - Send a `DELETE` request to `/employees/{id}` with the employee ID in the URL and a `Role` in the header.


