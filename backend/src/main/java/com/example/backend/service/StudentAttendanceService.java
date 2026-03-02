package com.example.backend.service;

import com.example.backend.dto.CalendarDTO;
import com.example.backend.dto.SummaryDTO;
import com.example.backend.entity.Attendance;
import com.example.backend.entity.Student;
import com.example.backend.repository.AttendanceRepository;
import com.example.backend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentAttendanceService {

    private final StudentRepository studentRepo;
    private final AttendanceRepository attendanceRepo;

    private Student getCurrentStudent() {
        String email = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return studentRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Attendance checkIn() {

        Student student = getCurrentStudent();
        LocalDate today = LocalDate.now();

        if (attendanceRepo.findByStudentAndDate(student, today).isPresent()) {
            throw new RuntimeException("Already checked in today");
        }

        LocalTime now = LocalTime.now();

        Attendance attendance = Attendance.builder()
                .student(student)
                .date(today)
                .checkInTime(now)
                .status(now.isAfter(LocalTime.of(9, 30)) ? "LATE" : "PRESENT")
                .totalHours(0.0)
                .build();

        return attendanceRepo.save(attendance);
    }

    public Attendance checkOut() {

        Student student = getCurrentStudent();
        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepo
                .findByStudentAndDate(student, today)
                .orElseThrow(() -> new RuntimeException("Check-in not found"));

        if (attendance.getCheckOutTime() != null) {
            throw new RuntimeException("Already checked out");
        }

        LocalTime now = LocalTime.now();
        attendance.setCheckOutTime(now);

        double hours = Duration.between(
                attendance.getCheckInTime(),
                now
        ).toMinutes() / 60.0;

        attendance.setTotalHours(hours);

        if (hours < 4) {
            attendance.setStatus("HALF_DAY");
        }

        return attendanceRepo.save(attendance);
    }

    public Attendance today() {
        return attendanceRepo
                .findByStudentAndDate(getCurrentStudent(), LocalDate.now())
                .orElse(null);
    }

    public List<Attendance> history() {
        return attendanceRepo.findByStudent(getCurrentStudent());
    }

    public SummaryDTO summary(int month, int year) {

        Student student = getCurrentStudent();

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<Attendance> records =
                attendanceRepo.findByStudentAndDateBetween(student, start, end);

        long present = records.stream()
                .filter(a -> "PRESENT".equals(a.getStatus()))
                .count();

        long late = records.stream()
                .filter(a -> "LATE".equals(a.getStatus()))
                .count();

        long halfDay = records.stream()
                .filter(a -> "HALF_DAY".equals(a.getStatus()))
                .count();

        double totalHours = records.stream()
                .mapToDouble(a -> a.getTotalHours() == null ? 0.0 : a.getTotalHours())
                .sum();

        return new SummaryDTO(present, late, halfDay, totalHours);
    }

    public List<CalendarDTO> calendar(int month, int year) {

        Student student = getCurrentStudent();

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        return attendanceRepo
                .findByStudentAndDateBetween(student, start, end)
                .stream()
                .map(a -> new CalendarDTO(a.getDate(), a.getStatus()))
                .collect(Collectors.toList());
    }
}