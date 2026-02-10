# Endterm Project — Spring Boot REST API (Students / Courses / Enrollments)

## Overview
This project is a Spring Boot REST API connected to PostgreSQL using JDBC (DataSource).
It provides endpoints to manage Students, Courses, and Enrollments (student-course registration).

Layered architecture:
**Controller → Service → Repository → PostgreSQL**

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
| Column | Type | Notes |
|--------|------|------|
| id | SERIAL | PRIMARY KEY |
| name | VARCHAR | NOT NULL |
| email | VARCHAR | UNIQUE, NOT NULL |

### courses
| Column | Type | Notes |
|--------|------|------|
| id | SERIAL | PRIMARY KEY |
| name | VARCHAR | UNIQUE, NOT NULL |
| credits | INT | NOT NULL |

### enrollments
| Column | Type | Notes |
|--------|------|------|
| id | SERIAL | PRIMARY KEY |
| student_id | INT | FK → students(id) |
| course_id | INT | FK → courses(id) |
| (student_id, course_id) | UNIQUE | prevents duplicates |

---

## REST API Endpoints

### Students
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/students` | Get all students |
| POST | `/students` | Create student |
| DELETE | `/students/{id}` | Delete student |

**POST /students example**
Request JSON:
```json
{
  "name": "Test Student",
  "email": "test@student.com"
}


Response JSON example:
{
  "id": 1,
  "name": "Test Student",
  "email": "test@student.com"
}


Courses
Method	  Endpoint     	Description
GET     	/courses	    Get all courses
POST    	/courses	    Create course
DELETE  	/courses/{id}	Delete course


POST /courses example
Request JSON:
{
  "name": "Java OOP",
  "credits": 5
}



Response JSON example:
{
  "id": 1,
  "name": "Java OOP",
  "credits": 5
}


Enrollments
Method	   Endpoint    	    Description
GET	      /enrollments	    Get all enrollments
POST	    /enrollments	    Create enrollment
DELETE  	/enrollments/{id}	Delete enrollment



POST /enrollments example
Request JSON:
{
  "studentId": 4,
  "courseId": 3
}



Response JSON example:
{
  "id": 1,
  "studentId": 4,
  "courseId": 3
}


Validation & Exception Handling

The project includes custom exceptions:

InvalidInputException

DuplicateResourceException

ResourceNotFoundException

DatabaseOperationException

These exceptions are used in the Service layer and Repository layer.

How to Run
1) Create PostgreSQL database

Example database name:

asik3

2) Create tables

Tables required:

students

courses

enrollments

3) Configure application.properties

Update these properties:

spring.datasource.url

spring.datasource.username

spring.datasource.password

4) Run Spring Boot

Run:

EndtermApplication.java

Server will start on:

http://localhost:8080

Testing

You can test:

GET requests in the browser

POST/DELETE requests using Postman or Thunder Client

Example URLs:

http://localhost:8080/students

http://localhost:8080/courses

http://localhost:8080/enrollments

Project Structure
kz.astana.endterm
 ├── controller
 ├── service
 ├── repository
 ├── model
 ├── dto
 └── exception
