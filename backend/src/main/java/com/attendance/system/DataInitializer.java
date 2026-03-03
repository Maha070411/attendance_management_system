package com.attendance.system;

import com.attendance.system.entity.Faculty;
import com.attendance.system.entity.Student;
import com.attendance.system.repository.FacultyRepository;
import com.attendance.system.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        if (studentRepository.count() == 0 && facultyRepository.count() == 0) {
            // Create a student
            Student student = new Student();
            student.setStudentId("S101");
            student.setName("John Doe");
            student.setEmail("student@test.com");
            student.setPassword(encoder.encode("password"));
            student.setDepartment("Computer Science");
            student.setClassName("CS 101");
            studentRepository.save(student);

            Student student2 = new Student();
            student2.setStudentId("S102");
            student2.setName("Jane Smith");
            student2.setEmail("jane@test.com");
            student2.setPassword(encoder.encode("password"));
            student2.setDepartment("Computer Science");
            student2.setClassName("CS 101");
            studentRepository.save(student2);

            // Create a faculty
            Faculty faculty = new Faculty();
            faculty.setFacultyId("F101");
            faculty.setName("Prof. Alan Turing");
            faculty.setEmail("faculty@test.com");
            faculty.setPassword(encoder.encode("password"));
            faculty.setDepartment("Computer Science");
            faculty.setRole("FACULTY");
            facultyRepository.save(faculty);
        }
    }
}
