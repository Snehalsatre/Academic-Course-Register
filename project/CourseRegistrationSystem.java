import java.sql.*;
import java.util.Scanner;

public class CourseRegistrationSystem {
    static final String DB_URL = "jdbc:mysql://localhost:3306/coursedb"; // Change DB name as needed
    static final String USER = "root"; // Your MySQL username
    static final String PASS = "root"; // Your MySQL password

    static Connection conn;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            int choice;

            do {
                System.out.println("\n===== Course Registration System =====");
                System.out.println("1. Add Course");
                System.out.println("2. Register Student");
                System.out.println("3. Enroll in Course");
                System.out.println("4. View Courses");
                System.out.println("5. View Enrollments");
                System.out.println("6. Update Course");
                System.out.println("7. Delete Course");
                System.out.println("8. View Students");
                System.out.println("9. Exit");
                System.out.print("Enter your choice: ");
                
                while (!sc.hasNextInt()) {
                    System.out.print("Please enter a valid number: ");
                    sc.next();
                }
                choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        addCourse();
                        break;
                    case 2:
                        registerStudent();
                        break;
                    case 3:
                        enrollCourse();
                        break;
                    case 4:
                        viewCourses();
                        break;
                    case 5:
                        viewEnrollments();
                        break;
                    case 6:
                        updateCourse();
                        break;
                    case 7:
                        deleteCourse();
                        break;
                    case 8: 
                        viewStudents(); 
                        break;
                    case 9: 
                        System.out.println("Exiting the system.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 9);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    static void addCourse() throws SQLException {
        System.out.print("Enter course name: ");
        String name = sc.nextLine();
        System.out.print("Enter course code: ");
        String code = sc.nextLine();
        System.out.print("Enter prerequisites (comma separated): ");
        String prereq = sc.nextLine();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO courses (name, code, prerequisites) VALUES (?, ?, ?)");
        ps.setString(1, name);
        ps.setString(2, code);
        ps.setString(3, prereq);
        ps.executeUpdate();
        System.out.println("Course added successfully.");
    }

    static void registerStudent() throws SQLException {
        System.out.print("Enter student name: ");
        String name = sc.nextLine();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO students (name) VALUES (?)");
        ps.setString(1, name);
        ps.executeUpdate();
        System.out.println("Student registered successfully.");
    }

    static void enrollCourse() throws SQLException {
        System.out.print("Enter student ID: ");
        int sid = sc.nextInt();
        System.out.print("Enter course ID: ");
        int cid = sc.nextInt();

        if (!exists("students", sid) || !exists("courses", cid)) {
            System.out.println("Either Student ID or Course ID not found. Try again.");
            return;
        }

        PreparedStatement ps = conn.prepareStatement("INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)");
        ps.setInt(1, sid);
        ps.setInt(2, cid);
        ps.executeUpdate();
        System.out.println("Enrolled successfully.");
    }

    static void viewCourses() throws SQLException {
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM courses");
        System.out.println("\n--- Available Courses ---");
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("id") + " | Name: " + rs.getString("name") +
                    " | Code: " + rs.getString("code") + " | Prerequisites: " + rs.getString("prerequisites"));
        }
    }

    static void viewEnrollments() throws SQLException {
        ResultSet rs = conn.createStatement().executeQuery(
            "SELECT s.name AS student, c.name AS course FROM enrollments e " +
            "JOIN students s ON e.student_id = s.id " +
            "JOIN courses c ON e.course_id = c.id");
        System.out.println("\n--- Enrollments ---");
        while (rs.next()) {
            System.out.println("Student: " + rs.getString("student") + " | Course: " + rs.getString("course"));
        }
    }

    static void updateCourse() throws SQLException {
        System.out.print("Enter course ID to update: ");
        int id = sc.nextInt(); sc.nextLine();
        System.out.print("New course name: ");
        String name = sc.nextLine();
        System.out.print("New course code: ");
        String code = sc.nextLine();
        PreparedStatement ps = conn.prepareStatement("UPDATE courses SET name=?, code=? WHERE id=?");
        ps.setString(1, name);
        ps.setString(2, code);
        ps.setInt(3, id);
        ps.executeUpdate();
        System.out.println("Course updated.");
    }

    static void deleteCourse() throws SQLException {
        System.out.print("Enter course ID to delete: ");
        int id = sc.nextInt();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM courses WHERE id=?");
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("Course deleted.");
    }

    static void viewStudents() throws SQLException {
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM students");
        System.out.println("\n--- Registered Students ---");
        while (rs.next()) {
        System.out.println("ID: " + rs.getInt("id") + " | Name: " + rs.getString("name"));
       }
    }


    static boolean exists(String table, int id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM " + table + " WHERE id=?");
        ps.setInt(1, id);
        return ps.executeQuery().next();
    }
}

