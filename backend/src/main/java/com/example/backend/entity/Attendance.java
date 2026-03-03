package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;
    private LocalDate date;
    private String status;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
}