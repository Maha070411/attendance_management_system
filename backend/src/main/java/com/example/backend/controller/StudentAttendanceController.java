package com.example.backend.controller;

import com.example.backend.dto.CalendarDTO;
import com.example.backend.dto.SummaryDTO;
import com.example.backend.entity.Attendance;
import com.example.backend.service.StudentAttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/attendance")
@RequiredArgsConstructor
public class StudentAttendanceController {

    private final StudentAttendanceService service;

    @PostMapping("/checkin")
    public Attendance checkIn() {
        return service.checkIn();
    }

    @PostMapping("/checkout")
    public Attendance checkOut() {
        return service.checkOut();
    }

    @GetMapping("/today")
    public Attendance today() {
        return service.today();
    }

    @GetMapping("/history")
    public List<Attendance> history() {
        return service.history();
    }

    @GetMapping("/summary")
    public SummaryDTO summary(
            @RequestParam int month,
            @RequestParam int year
    ) {
        return service.summary(month, year);
    }

    @GetMapping("/calendar")
    public List<CalendarDTO> calendar(
            @RequestParam int month,
            @RequestParam int year
    ) {
        return service.calendar(month, year);
    }
}