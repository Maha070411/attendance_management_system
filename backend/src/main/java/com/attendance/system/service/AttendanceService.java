package com.attendance.system.service;

import com.attendance.system.dto.AttendanceRequest;
import com.attendance.system.entity.Attendance;
import com.attendance.system.entity.AttendanceStatus;
import com.attendance.system.entity.Student;
import com.attendance.system.repository.AttendanceRepository;
import com.attendance.system.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public Attendance markAttendance(AttendanceRequest request) {

        Optional<Student> studentOpt = studentRepository.findById(request.getStudentId());
        if (studentOpt.isEmpty()) {
            throw new RuntimeException("Student not found");
        }

        Attendance attendance = attendanceRepository.findByStudentIdAndDate(request.getStudentId(), request.getDate())
                .orElse(new Attendance());

        attendance.setStudent(studentOpt.get());
        attendance.setDate(request.getDate());
        attendance.setCheckInTime(request.getCheckInTime());
        attendance.setCheckOutTime(request.getCheckOutTime());

        // Calculate hours
        if (request.getCheckInTime() != null && request.getCheckOutTime() != null) {
            Duration duration = Duration.between(request.getCheckInTime(), request.getCheckOutTime());
            double totalHours = duration.toMinutes() / 60.0;
            attendance.setTotalHours(Math.round(totalHours * 100.0) / 100.0);

            // Determine status
            if (totalHours >= 8.0) {
                attendance.setStatus(AttendanceStatus.PRESENT);
            } else if (totalHours >= 4.0) {
                attendance.setStatus(AttendanceStatus.HALF_DAY);
            } else {
                attendance.setStatus(AttendanceStatus.LATE);
            }
        } else {
            attendance.setStatus(AttendanceStatus.LATE);
            attendance.setTotalHours(0.0);
        }

        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getStudentAttendanceHistory(Long studentId) {
        return attendanceRepository.findByStudentIdOrderByDateDesc(studentId);
    }
}
