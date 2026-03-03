package com.example.backend.service;

import com.example.backend.entity.Attendance;
import com.example.backend.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public Attendance createAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    public Attendance updateAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }

    public Attendance getAttendanceById(Long id) {
        return attendanceRepository.findById(id).orElse(null);
    }

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public List<Attendance> getAttendanceByFaculty(Long facultyId) {
        return attendanceRepository.findByFacultyId(facultyId);
    }

    public List<Attendance> getAttendanceBySubject(Long subjectId) {
        return attendanceRepository.findBySubjectId(subjectId);
    }
}


//
//package com.example.backend.service;
//
//import com.example.backend.dto.AttendanceDTO;
//import com.example.backend.entity.Attendance;
//import com.example.backend.entity.Faculty;
//import com.example.backend.entity.Subject;
//import com.example.backend.repository.AttendanceRepository;
//import com.example.backend.repository.FacultyRepository;
//import com.example.backend.repository.SubjectRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class AttendanceService {
//
//    private final AttendanceRepository attendanceRepository;
//    private final FacultyRepository facultyRepository;
//    private final SubjectRepository subjectRepository;
//
//    public Attendance markAttendance(AttendanceDTO dto) {
//
//        Faculty faculty = facultyRepository.findById(dto.getFacultyId())
//                .orElseThrow(() -> new RuntimeException("Faculty not found"));
//
//        Subject subject = subjectRepository.findById(dto.getSubjectId())
//                .orElseThrow(() -> new RuntimeException("Subject not found"));
//
//        Attendance attendance = Attendance.builder()
//                .studentId(dto.getStudentId())
//                .faculty(faculty)
//                .subject(subject)
//                .date(dto.getDate())
//                .status(dto.getStatus())
//                .build();
//
//        return attendanceRepository.save(attendance);
//    }
//
//    public List<Attendance> getAttendanceByFaculty(Long facultyId) {
//        return attendanceRepository.findByFacultyId(facultyId);
//    }
//
//    public List<Attendance> getAttendanceBySubject(Long subjectId) {
//        return attendanceRepository.findBySubjectId(subjectId);
//    }
//}



//package com.example.backend.service;
//
//import com.example.backend.entity.Attendance;
//import com.example.backend.repository.AttendanceRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class AttendanceService {
//    private final AttendanceRepository attendanceRepository;
//
//    public Attendance markAttendance(Attendance attendance) {
//        return attendanceRepository.save(attendance);
//    }
//
//    public List<Attendance> getAttendanceByFaculty(Long facultyId) {
//        return attendanceRepository.findByFacultyId(facultyId);
//    }
//
//    public List<Attendance> getAttendanceBySubject(Long subjectId) {
//        return attendanceRepository.findBySubjectId(subjectId);
//    }
//}

