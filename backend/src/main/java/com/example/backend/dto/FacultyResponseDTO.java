package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FacultyResponseDTO {

    private Long id;
    private String facultyId;
    private String name;
    private String email;
    private String department;
    private String role;
}