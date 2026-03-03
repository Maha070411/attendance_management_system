package com.attendance.system.service;

import com.attendance.system.dto.AttendanceRequest;
import com.attendance.system.entity.Attendance;
import com.attendance.system.entity.AttendanceStatus;
import com.attendance.system.entity.Student;
import com.attendance.system.repository.AttendanceRepository;
import com.attendance.system.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AttendanceServiceTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private AttendanceService attendanceService;

    private Student mockStudent;
    private AttendanceRequest mockRequest;

    @BeforeEach
    void setUp() {
        mockStudent = new Student();
        mockStudent.setId(1L);
        mockStudent.setName("Test Student");

        mockRequest = new AttendanceRequest();
        mockRequest.setStudentId(1L);
        mockRequest.setDate(LocalDate.now());
    }

    @Test
    void markAttendance_Success_Present() {

        mockRequest.setCheckInTime(LocalTime.of(9, 0));
        mockRequest.setCheckOutTime(LocalTime.of(17, 30));

        when(studentRepository.findById(1L)).thenReturn(Optional.of(mockStudent));
        when(attendanceRepository.findByStudentIdAndDate(1L, mockRequest.getDate())).thenReturn(Optional.empty());

        Attendance savedAttendance = new Attendance();
        savedAttendance.setStatus(AttendanceStatus.PRESENT);
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(savedAttendance);

        Attendance result = attendanceService.markAttendance(mockRequest);

        assertNotNull(result);
        assertEquals(AttendanceStatus.PRESENT, result.getStatus());
        verify(attendanceRepository, times(1)).save(any(Attendance.class));
    }

    @Test
    void markAttendance_Success_HalfDay() {
        // Arrange
        mockRequest.setCheckInTime(LocalTime.of(9, 0));
        mockRequest.setCheckOutTime(LocalTime.of(14, 0)); // 5 hours

        when(studentRepository.findById(1L)).thenReturn(Optional.of(mockStudent));
        when(attendanceRepository.findByStudentIdAndDate(1L, mockRequest.getDate())).thenReturn(Optional.empty());

        Attendance savedAttendance = new Attendance();
        savedAttendance.setStatus(AttendanceStatus.HALF_DAY);
        when(attendanceRepository.save(any(Attendance.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Attendance result = attendanceService.markAttendance(mockRequest);

        // Assert
        assertNotNull(result);
        assertEquals(AttendanceStatus.HALF_DAY, result.getStatus());
        assertEquals(5.0, result.getTotalHours());
    }

    @Test
    void markAttendance_Success_Late() {
        // Arrange
        mockRequest.setCheckInTime(LocalTime.of(9, 0));
        mockRequest.setCheckOutTime(LocalTime.of(11, 0)); // 2 hours

        when(studentRepository.findById(1L)).thenReturn(Optional.of(mockStudent));
        when(attendanceRepository.findByStudentIdAndDate(1L, mockRequest.getDate())).thenReturn(Optional.empty());

        when(attendanceRepository.save(any(Attendance.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Attendance result = attendanceService.markAttendance(mockRequest);

        // Assert
        assertNotNull(result);
        assertEquals(AttendanceStatus.LATE, result.getStatus());
        assertEquals(2.0, result.getTotalHours());
    }

    @Test
    void markAttendance_StudentNotFound_ThrowsException() {
        // Arrange
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            attendanceService.markAttendance(mockRequest);
        });

        assertEquals("Student not found", exception.getMessage());
        verify(attendanceRepository, never()).save(any(Attendance.class));
    }

    @Test
    void markAttendance_AlreadyMarked_UpdatesExistingRecord() {
        // Arrange
        Attendance existing = new Attendance();
        existing.setId(5L);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(mockStudent));
        when(attendanceRepository.findByStudentIdAndDate(1L, mockRequest.getDate())).thenReturn(Optional.of(existing));
        when(attendanceRepository.save(any(Attendance.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Attendance result = attendanceService.markAttendance(mockRequest);

        // Assert
        assertNotNull(result);
        assertEquals(5L, result.getId());
        verify(attendanceRepository, times(1)).save(any(Attendance.class));
    }

    @Test
    void markAttendance_MissingCheckOutTime_MarkedLateWithZeroHours() {
        // Arrange
        mockRequest.setCheckInTime(LocalTime.of(9, 0));
        mockRequest.setCheckOutTime(null); // Missing check-out time

        when(studentRepository.findById(1L)).thenReturn(Optional.of(mockStudent));
        when(attendanceRepository.findByStudentIdAndDate(1L, mockRequest.getDate())).thenReturn(Optional.empty());

        when(attendanceRepository.save(any(Attendance.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Attendance result = attendanceService.markAttendance(mockRequest);

        // Assert
        assertNotNull(result);
        assertEquals(AttendanceStatus.LATE, result.getStatus());
        assertEquals(0.0, result.getTotalHours());
    }
}
