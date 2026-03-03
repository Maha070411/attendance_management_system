package com.attendance.system.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "faculty")
@Data
@NoArgsConstructor
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "faculty_id", unique = true, nullable = false)
    private String facultyId;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;
    private String department;
    private String role;
}
