
# 🏫 Course Registration  System

A Java-based terminal application designed to streamline the process of **managing courses, students, and enrollments** using **MySQL** as the backend. Built for simplicity and clarity, this system showcases real-world usage of **JDBC** in a modular, menu-driven structure.

---

## 🔍 Overview

This system allows administrative users to:
- Create and manage course data
- Register new students
- Enroll students into available courses
- View all courses and enrollments
- Perform updates and deletions efficiently

---

## 🔧 Built With

- **Java** – Core logic and console interface
- **MySQL** – Data storage and relationship management
- **JDBC** – To interact with the MySQL database securely

---

## 📌 Main Functionalities

### 🧑‍🏫 Courses
- Add courses with name, code, and optional prerequisites
- Edit course details (update course name/code)
- Delete existing courses
- Display all stored courses

### 🎓 Students
- Add/register student by name
- List or validate students before enrolling

### 🧾 Enrollment
- Link students with selected courses
- View all student-course pairs
- Foreign key validation ensures data accuracy

---

## 🧬 Database Design

```sql
CREATE TABLE courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    code VARCHAR(20),
    prerequisites VARCHAR(255)
);

CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE enrollments (
    student_id INT,
    course_id INT,
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);
```

---

## 📦 Folder Structure

```
project/
├── CourseRegistrationSystem.java
├── lib/
│   └── mysql-connector-j-9.3.0.jar
└── .vscode/
    └── settings.json
                       # SQL script to create required tables
```

---

## 🚀 Running the Application

### Requirements
- Java JDK 8 or later
- MySQL Server installed and running
- `mysql-connector-j-9.3.0.jar` added to classpath

### Steps

1. **Import Database**
   Use `coursedb.sql` in your MySQL to create the schema and tables.

2. **Update JDBC Config** in your `.java` file:
   ```java
   static final String DB_URL = "jdbc:mysql://localhost:3306/coursedb";
   static final String USER = "root";
   static final String PASS = "your_mysql_password";
   ```

3. **Compile & Run**

For Windows:
```bash
javac -cp "lib/mysql-connector-j-9.3.0.jar" src/CourseRegistrationSystem.java
java -cp ".;lib/mysql-connector-j-9.3.0.jar" CourseRegistrationSystem
```

For Mac/Linux:
```bash
javac -cp "lib/mysql-connector-j-9.3.0.jar" src/CourseRegistrationSystem.java
java -cp ".:lib/mysql-connector-j-9.3.0.jar" CourseRegistrationSystem
```

---

## 📜 Sample Menu Output

```
========= Course Registration Menu =========
1. Add Course
2. Register Student
3. Enroll in Course
4. View Courses
5. View Enrollments
6. Update Course
7. Delete Course
8. Exit
```

---

## 📣 Author

Created by **Snehal Satre** – Built as a part of academic project work to demonstrate core Java and database integration skills.

---

## 📘 License

Released under the [MIT License](https://opensource.org/licenses/MIT).
