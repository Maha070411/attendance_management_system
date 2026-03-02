package com.example.backend.config;

import com.example.backend.entity.Attendance;
import com.example.backend.entity.Student;
import com.example.backend.repository.AttendanceRepository;
import com.example.backend.repository.StudentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.*;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final StudentRepository studentRepo;
    private final AttendanceRepository attendanceRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (studentRepo.count() > 0) return;

        Student student = Student.builder()
                .studentId("STU001")
                .name("Maha Lakshmi")
                .email("maha@student.com")
                .password(passwordEncoder.encode("password123"))
                .department("CSE")
                .className("CSE-A")
                .createdAt(LocalDateTime.now())
                .build();

        studentRepo.save(student);

        attendanceRepo.save(
                Attendance.builder()
                        .student(student)
                        .date(LocalDate.of(2026, 3, 1))
                        .checkInTime(LocalTime.of(9, 5))
                        .checkOutTime(LocalTime.of(16, 30))
                        .status("PRESENT")
                        .totalHours(7.5)
                        .build()
        );

        attendanceRepo.save(
                Attendance.builder()
                        .student(student)
                        .date(LocalDate.of(2026, 3, 2))
                        .checkInTime(LocalTime.of(9, 45))
                        .checkOutTime(LocalTime.of(16, 0))
                        .status("LATE")
                        .totalHours(6.25)
                        .build()
        );

        attendanceRepo.save(
                Attendance.builder()
                        .student(student)
                        .date(LocalDate.of(2026, 3, 3))
                        .checkInTime(LocalTime.of(9, 10))
                        .checkOutTime(LocalTime.of(12, 0))
                        .status("HALF_DAY")
                        .totalHours(2.8)
                        .build()
        );

        attendanceRepo.save(
                Attendance.builder()
                        .student(student)
                        .date(LocalDate.of(2026, 3, 4))
                        .checkInTime(LocalTime.of(9, 0))
                        .checkOutTime(LocalTime.of(16, 45))
                        .status("PRESENT")
                        .totalHours(7.75)
                        .build()
        );

        System.out.println("Sample Data Loaded Successfully");
    }
}