package com.example.backend.repository;

import com.example.backend.entity.Attendance;
import com.example.backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByStudentAndDate(Student student, LocalDate date);

    List<Attendance> findByStudent(Student student);

    List<Attendance> findByStudentAndDateBetween(
            Student student,
            LocalDate start,
            LocalDate end
    );
}