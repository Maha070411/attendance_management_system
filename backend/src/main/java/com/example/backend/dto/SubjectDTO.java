package com.example.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectDTO {
    private String name;
    private String department;
    private Long facultyId;
}