package com.attendance.system.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AttendanceRequest {
    private Long studentId;
    private LocalDate date;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
}
