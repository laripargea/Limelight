package com.example.demo.service;

import com.example.demo.model.Play;
import com.example.demo.model.Timetable;
import com.example.demo.repository.PlayRepository;
import com.example.demo.repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Time;
import java.util.*;

@Service
public class TimetableService {
    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private PlayRepository playRepository;

    @Transactional
    public Timetable save(Timetable timetable, Long idPlay) {
        for (Play play : playRepository.findAll())
            if (Objects.equals(play.getIdPlay(), idPlay)) {
                timetable.setPlay(play);
                timetable.setName(play.getTitle());
                break;
            }
        return timetableRepository.save(timetable);
    }

    @Transactional
    public Timetable saveByTitle(String title, Date datePlay, Time timePlay) {
        Play play = playRepository.findPlayByTitle(title);
        Timetable timetable = new Timetable(null, null, null, null, null, null);
        timetable.setPlay(play);
        timetable.setName(play.getTitle());
        timetable.setTimeOfPlay(timePlay);
        timetable.setDateOfPlay(datePlay);
        return timetableRepository.save(timetable);
    }

    @Transactional
    public void deleteById(Long id) {
        timetableRepository.deleteById(id);
    }

    @Transactional
    public Timetable updateById(Long id, Timetable timetable) {
        Timetable t = timetableRepository.findTimetableByIdTimetable(id);
        t.setDateOfPlay(timetable.getDateOfPlay());
        t.setTimeOfPlay(timetable.getTimeOfPlay());
        return timetableRepository.save(t);
    }

    public List<Timetable> findAll() {
        return new ArrayList<>(timetableRepository.findAll());
    }
}