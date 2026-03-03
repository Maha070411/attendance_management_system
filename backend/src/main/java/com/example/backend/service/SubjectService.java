package com.example.backend.service;

import com.example.backend.entity.Subject;
import com.example.backend.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public Subject createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public List<Subject> getSubjectsByFaculty(Long facultyId) {
        return subjectRepository.findByFacultyId(facultyId);
    }
}