package com;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dao.StudentDAO;
import model.Student;

@Controller
public class StudentController {

    private final StudentDAO studentDAO = new StudentDAO();

    @GetMapping({"/", "/students"})
    public String students(Model model) {
        List<Student> list = studentDAO.getStudents();
        model.addAttribute("students", list);
        return "index";
    }

    @PostMapping("/students/add")
    public String addStudent(
        @RequestParam("name") String name,
        @RequestParam("age") int age,
        @RequestParam("course") String course
    ) {
        studentDAO.addStudent(new Student(name, age, course));
        return "redirect:/students";
    }

    @PostMapping("/students/delete")
    public String deleteStudent(@RequestParam("id") int id) {
        studentDAO.deleteStudent(id);
        return "redirect:/students";
    }
}
