# Student-M Java Project

## Overview
`student-m` is a Java-based student management application that demonstrates a full-stack flow using Spring Boot, JDBC, MySQL, and Thymeleaf.

Features:
- Add student (name, age, course)
- View all students
- Delete student by ID
- Dashboard UI in browser with Bootstrap styling
- Data persistence in MySQL `students` table

This project started as a console app (App.java) and was extended to a Spring Boot web app.

---

## Project structure

- `pom.xml`: Maven project definition + dependencies (Spring Boot, MySQL connector, Thymeleaf, JUnit)
- `src/main/java/com/App.java`: Spring Boot entrypoint (`@SpringBootApplication`)
- `src/main/java/com/StudentController.java`: web routes for `/`, `/students`, `/students/add`, `/students/delete`
- `src/main/java/dao/StudentDAO.java`: data access methods (`addStudent`, `getStudents`, `deleteStudent`)
- `src/main/java/model/Student.java`: model object representing student row
- `src/main/java/com/util/DBConnection.java`: JDBC connection helper `getConnection()`
- `src/main/resources/templates/index.html`: Thymeleaf template for dashboard form and table
- `src/main/resources/application.properties`: Spring Boot config + datasource settings

---

## Database setup (MySQL)

1. Start MySQL server.
2. Create database and table:

```sql
CREATE DATABASE IF NOT EXISTS studentdb;
USE studentdb;
CREATE TABLE IF NOT EXISTS students (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  age INT NOT NULL,
  course VARCHAR(100) NOT NULL
);
```

3. Confirm credentials in `src/main/resources/application.properties` and `src/main/java/com/util/DBConnection.java`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/studentdb?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=jagadish28
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

(Adjust user/password as needed.)

---

## How backend and frontend attach

1. Request enters in `App.java` (Spring Boot app).
2. `StudentController` handles endpoints:
   - `GET /` (index page with add/view)
   - `POST /students/add` (form post) calls `StudentDAO.addStudent`.
   - `POST /students/delete` calls `StudentDAO.deleteStudent`.
3. `StudentDAO` executes SQL using JDBC connection from `DBConnection.getConnection()`.
4. `getStudents()` returns list of `Student` objects.
5. Thymeleaf template `index.html` receives `students` model variable and renders the table.
6. Add/delete actions post back to controller, then perform redirect to `/`.

---

## Running the project

From project root:

```bash
# clean build and tests
mvn clean test

# run web app
mvn spring-boot:run
```

Then open browser:
- `http://localhost:8080/`

If port conflict appears:

```bash
# check which process uses 8080
netstat -aon | findstr ":8080"
# stop process (replace PID)
taskkill /PID <pid> /F
# or run on another port
mvn spring-boot:run -Dserver.port=8081
```

---

## Validate & debug commands

- Run unit tests:
  - `mvn test`
- Check effective config:
  - `mvn help:effective-pom`
- Get debug-level output:
  - `mvn -X spring-boot:run`

---

## Main methods

`StudentDAO`:
- `addStudent(Student s)` -> `INSERT INTO students(name, age, course)`
- `getStudents()` -> `SELECT * FROM students`
- `deleteStudent(int id)` -> `DELETE FROM students WHERE id = ?`

`StudentController`:
- `students()` (GET) loads student list and renders view
- `addStudent(...)` handles form data and redirects
- `deleteStudent(id)` deletes and redirects

`DBConnection.getConnection()`:
- uses DriverManager to create JDBC connection

---

## Notes

- Backend and frontend are attached solely through controller templating; no REST API layer is necessary in this project.
- For production, externalize DB credentials, improve error reporting, add validation, and use connection pooling (HikariCP).

---

## Versioning rollback

If you need to restore the previous working state, use Git branches (already created as `backup-before-portfix`).

```bash
git checkout backup-before-portfix
```
