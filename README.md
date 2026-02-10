# Endterm Project — Spring Boot REST API (Students / Courses / Enrollments)

## Project Overview
This project is a Spring Boot REST API connected to PostgreSQL using JDBC.
It provides endpoints to manage Students, Courses, and Enrollments.

Layered architecture:
Controller → Service → Repository → PostgreSQL

---

## Technologies
- Java 17
- Spring Boot
- Maven
- PostgreSQL
- JDBC (DataSource)

---

## Database Schema

### students
- id (SERIAL, PRIMARY KEY)
- name (VARCHAR)
- email (VARCHAR)

### courses
- id (SERIAL, PRIMARY KEY)
- name (VARCHAR)
- credits (INT)

### enrollments
- id (SERIAL, PRIMARY KEY)
- student_id (INT, FK → students.id)
- course_id (INT, FK → courses.id)
- UNIQUE(student_id, course_id)

---

## REST API Endpoints

### Students
- GET    /students
- POST   /students
- DELETE /students/{id}

POST /students JSON example:
```json
{
  "name": "Test Student",
  "email": "test@student.com"
}
Courses
GET /courses

POST /courses

DELETE /courses/{id}

POST /courses JSON example:

json
Копировать код
{
  "name": "Java OOP",
  "credits": 5
}
Enrollments
GET /enrollments

POST /enrollments

DELETE /enrollments/{id}

POST /enrollments JSON example:

json
Копировать код
{
  "studentId": 4,
  "courseId": 3
}
How to Run
Create PostgreSQL database (example: asik3)

Create tables: students, courses, enrollments

Update application.properties:

spring.datasource.url

spring.datasource.username

spring.datasource.password

Run the project:

EndtermApplication.java

Server runs on:
http://localhost:8080

Exception Handling
The project uses custom exceptions:

InvalidInputException

DuplicateResourceException

ResourceNotFoundException

DatabaseOperationException
