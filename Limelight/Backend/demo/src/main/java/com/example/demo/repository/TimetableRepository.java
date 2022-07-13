package com.example.demo.repository;

import com.example.demo.model.Timetable;
import com.sun.istack.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long> {
    Timetable findTimetableByIdTimetable(@NotNull Long idTimetable);
}