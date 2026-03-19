package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConnection;

import model.Student;

public class StudentDAO {

    // Add Student
    public void addStudent(Student s) {
        String query = "INSERT INTO students(name, age, course) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, s.getName());
            ps.setInt(2, s.getAge());
            ps.setString(3, s.getCourse());

            ps.executeUpdate();
            System.out.println("✅ Student Added Successfully");

        } catch (Exception e) {
            System.out.println("❌ Error adding student");
        }
    }

    // View Students
    public List<Student> getStudents() {
        List<Student> list = new ArrayList<>();
        String query = "SELECT * FROM students";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Student s = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("course")
                );
                list.add(s);
            }

        } catch (Exception e) {
            System.out.println("❌ Error fetching students");
        }

        return list;
    }

    // Delete Student
    public void deleteStudent(int id) {
        String query = "DELETE FROM students WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Student Deleted");
            } else {
                System.out.println("⚠️ Student not found");
            }

        } catch (Exception e) {
            System.out.println("❌ Error deleting student");
        }
    }
}