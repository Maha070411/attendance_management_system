package com.example.backend.controller;

import com.example.backend.entity.Attendance;
import com.example.backend.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/create")
    public ResponseEntity<Attendance> create(@RequestBody Attendance attendance) {
        return ResponseEntity.ok(attendanceService.createAttendance(attendance));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attendance> getById(@PathVariable Long id) {
        return ResponseEntity.ok(attendanceService.getAttendanceById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Attendance>> getAll() {
        return ResponseEntity.ok(attendanceService.getAllAttendance());
    }

    @PutMapping("/update")
    public ResponseEntity<Attendance> update(@RequestBody Attendance attendance) {
        return ResponseEntity.ok(attendanceService.updateAttendance(attendance));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    @GetMapping("/faculty/{facultyId}")
    public ResponseEntity<List<Attendance>> getByFaculty(@PathVariable Long facultyId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByFaculty(facultyId));
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<Attendance>> getBySubject(@PathVariable Long subjectId) {
        return ResponseEntity.ok(attendanceService.getAttendanceBySubject(subjectId));
    }
}


//package com.example.backend.controller;
//
//import com.example.backend.entity.Attendance;
//import com.example.backend.service.AttendanceService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/attendance")
//@RequiredArgsConstructor
//public class AttendanceController {
//
//    private final AttendanceService attendanceService;
//
//    // Mark attendance
//    @PostMapping("/mark")
//    public ResponseEntity<Attendance> markAttendance(@RequestBody Attendance attendance) {
//        Attendance savedAttendance = attendanceService.markAttendance(attendance);
//        return ResponseEntity.ok(savedAttendance);
//    }
//
//    // Get attendance by faculty
//    @GetMapping("/faculty/{facultyId}")
//    public ResponseEntity<List<Attendance>> getAttendanceByFaculty(@PathVariable Long facultyId) {
//        List<Attendance> attendanceList = attendanceService.getAttendanceByFaculty(facultyId);
//        return ResponseEntity.ok(attendanceList);
//    }
//
//    // Get attendance by subject
//    @GetMapping("/subject/{subjectId}")
//    public ResponseEntity<List<Attendance>> getAttendanceBySubject(@PathVariable Long subjectId) {
//        List<Attendance> attendanceList = attendanceService.getAttendanceBySubject(subjectId);
//        return ResponseEntity.ok(attendanceList);
//    }
//}