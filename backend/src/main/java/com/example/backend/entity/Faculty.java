package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "faculty")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faculty_id")
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String department;
}