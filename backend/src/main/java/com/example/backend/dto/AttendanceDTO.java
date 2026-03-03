package com.example.backend.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceDTO {
    private Long studentId;
    private Long facultyId;
    private Long subjectId;
    private LocalDate date;
    private String status;
}