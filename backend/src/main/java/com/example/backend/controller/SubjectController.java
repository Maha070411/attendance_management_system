package com.example.backend.controller;

import com.example.backend.entity.Subject;
import com.example.backend.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping("/create")
    public ResponseEntity<Subject> createSubject(@RequestBody Subject subject) {
        Subject savedSubject = subjectService.createSubject(subject);
        return ResponseEntity.ok(savedSubject);
    }

    @GetMapping("/faculty/{facultyId}")
    public ResponseEntity<List<Subject>> getSubjectsByFaculty(@PathVariable Long facultyId) {
        List<Subject> subjects = subjectService.getSubjectsByFaculty(facultyId);
        return ResponseEntity.ok(subjects);
    }
}