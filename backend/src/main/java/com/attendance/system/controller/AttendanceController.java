package com.attendance.system.controller;

import com.attendance.system.dto.AttendanceRequest;
import com.attendance.system.entity.Attendance;
import com.attendance.system.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/mark")
    @PreAuthorize("hasRole('FACULTY')")
    public ResponseEntity<?> markAttendance(@RequestBody AttendanceRequest request) {
        try {
            Attendance attendance = attendanceService.markAttendance(request);
            return ResponseEntity.ok(attendance);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/history/student/{id}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('FACULTY')")
    public ResponseEntity<List<Attendance>> getStudentHistory(@PathVariable("id") Long id) {
        return ResponseEntity.ok(attendanceService.getStudentAttendanceHistory(id));
    }
}
