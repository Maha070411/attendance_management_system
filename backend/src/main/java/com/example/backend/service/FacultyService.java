package com.example.backend.service;

import com.example.backend.dto.*;
import com.example.backend.entity.Faculty;
import com.example.backend.repository.FacultyRepository;
import com.example.backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public FacultyResponseDTO registerFaculty(FacultyRegisterDTO dto) {

        Faculty faculty = Faculty.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .department(dto.getDepartment())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        Faculty saved = facultyRepository.save(faculty);

        return mapToResponseDTO(saved);
    }


    public JwtResponseDTO loginFaculty(FacultyLoginDTO dto) {

        Faculty faculty = facultyRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        if (!passwordEncoder.matches(dto.getPassword(), faculty.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(faculty.getEmail());

        FacultyResponseDTO facultyDTO = mapToResponseDTO(faculty);

        return new JwtResponseDTO(token, facultyDTO);
    }

    public List<FacultyResponseDTO> getAllFaculties() {
        return facultyRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public FacultyResponseDTO getFacultyById(Long id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Faculty not found with id " + id));

        return mapToResponseDTO(faculty);
    }

    public FacultyResponseDTO updateFaculty(Long id, FacultyRegisterDTO dto) {

        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Faculty not found with id " + id));

        faculty.setName(dto.getName());
        faculty.setEmail(dto.getEmail());
        faculty.setDepartment(dto.getDepartment());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            faculty.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        Faculty updated = facultyRepository.save(faculty);

        return mapToResponseDTO(updated);
    }

    public void deleteFaculty(Long id) {
        if (!facultyRepository.existsById(id)) {
            throw new RuntimeException("Faculty not found with id " + id);
        }
        facultyRepository.deleteById(id);
    }

    private FacultyResponseDTO mapToResponseDTO(Faculty faculty) {

        FacultyResponseDTO dto = new FacultyResponseDTO();
        dto.setId(faculty.getId()); // matches your entity
        dto.setName(faculty.getName());
        dto.setEmail(faculty.getEmail());
        dto.setDepartment(faculty.getDepartment());

        return dto;
    }
}