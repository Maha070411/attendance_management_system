package com.attendance.system.repository;

import com.attendance.system.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudentIdOrderByDateDesc(Long id);

    boolean existsByStudentIdAndDate(Long studentId, LocalDate date);

    java.util.Optional<Attendance> findByStudentIdAndDate(Long studentId, LocalDate date);
}
