package com.attendance.system.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
    private String role; // "STUDENT" or "FACULTY"
}
