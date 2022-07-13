package com.example.demo.controller;

import com.example.demo.model.Timetable;
import com.example.demo.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class TimetableController {
    @Autowired
    private TimetableService timetableService;

    @GetMapping("/timetable")
    public List<Timetable> findAll() {
        return timetableService.findAll();
    }

    @PostMapping("/timetable/add/{id}")
    public Timetable save(@RequestBody Timetable timetable, @PathVariable Long id) {
        return timetableService.save(timetable, id);
    }

    @GetMapping("/timetable/addByTitle/{title}/{datePlay}/{timePlay}")
    public Timetable saveByTitle(@PathVariable String title, @PathVariable Date datePlay, @PathVariable Time timePlay) {
        return timetableService.saveByTitle(title, datePlay, timePlay);
    }

    @DeleteMapping("/timetable/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        timetableService.deleteById(id);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PatchMapping("/timetable/update/{id}")
    public Timetable update(@RequestBody Timetable timetable, @PathVariable("id") Long id) {
        return timetableService.updateById(id, timetable);
    }
}