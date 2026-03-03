package com.example.backend.controller;

import com.example.backend.dto.*;
import com.example.backend.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculty")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class FacultyController {

    private final FacultyService facultyService;


    @PostMapping("/register")
    public ResponseEntity<FacultyResponseDTO> registerFaculty(@RequestBody FacultyRegisterDTO dto) {
        FacultyResponseDTO response = facultyService.registerFaculty(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> loginFaculty(@RequestBody FacultyLoginDTO dto) {
        JwtResponseDTO response = facultyService.loginFaculty(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<FacultyResponseDTO>> getAllFaculties() {
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }


    @GetMapping("/{id}")
    public ResponseEntity<FacultyResponseDTO> getFacultyById(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.getFacultyById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacultyResponseDTO> updateFaculty(
            @PathVariable Long id,
            @RequestBody FacultyRegisterDTO dto) {

        return ResponseEntity.ok(facultyService.updateFaculty(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok("Faculty deleted successfully");
    }
}