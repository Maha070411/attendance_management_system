package com.attendance.system.controller;

import com.attendance.system.dto.JwtResponse;
import com.attendance.system.dto.LoginRequest;
import com.attendance.system.entity.Faculty;
import com.attendance.system.entity.Student;
import com.attendance.system.repository.FacultyRepository;
import com.attendance.system.repository.StudentRepository;
import com.attendance.system.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    FacultyRepository facultyRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        if ("STUDENT".equalsIgnoreCase(loginRequest.getRole())) {
            Optional<Student> studentOpt = studentRepository.findByEmail(loginRequest.getEmail());
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                if (encoder.matches(loginRequest.getPassword(), student.getPassword())) {
                    String jwt = jwtUtils.generateJwtToken(student.getEmail(), "STUDENT", student.getId());
                    return ResponseEntity.ok(new JwtResponse(jwt, String.valueOf(student.getId()), student.getName(),
                            student.getEmail(), "STUDENT"));
                }
            }
        } else if ("FACULTY".equalsIgnoreCase(loginRequest.getRole())) {
            Optional<Faculty> facultyOpt = facultyRepository.findByEmail(loginRequest.getEmail());
            if (facultyOpt.isPresent()) {
                Faculty faculty = facultyOpt.get();
                if (encoder.matches(loginRequest.getPassword(), faculty.getPassword())) {
                    String jwt = jwtUtils.generateJwtToken(faculty.getEmail(), faculty.getRole(), faculty.getId());
                    return ResponseEntity.ok(new JwtResponse(jwt, String.valueOf(faculty.getId()), faculty.getName(),
                            faculty.getEmail(), faculty.getRole()));
                }
            }
        }

        return ResponseEntity.badRequest().body("Error: Invalid credentials or role!");
    }
}
