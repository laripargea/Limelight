package com.example.demo;

import com.example.demo.model.Timetable;
import com.example.demo.service.TimetableService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = LimelightApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.properties")
public class TimetableServiceTests {
    @Autowired
    private TimetableService timetableService;

    @Test
    public void findAllValidTest() {
        Assertions.assertTrue(timetableService.findAll().size() > 0);
    }

    @Test
    public void findAllInvalidTest() {
        Assertions.assertFalse(timetableService.findAll().size() <= 0);
    }

    @Test
    public void saveValidTest() {
        Timetable timetable = new Timetable(null, null, null, null, null, null);
        Timetable addedTimetable = timetableService.save(timetable, 4L);
        Assertions.assertNotNull(addedTimetable);
        timetableService.deleteById(addedTimetable.getIdTimetable());
    }

    @Test
    public void deleteByIdValidTest() {
        Timetable timetable = new Timetable(null, null, null, null, null, null);
        Timetable addedTimetable = timetableService.save(timetable, 4L);
        timetableService.deleteById(addedTimetable.getIdTimetable());
        Assertions.assertTrue(true);
    }

    @Test
    public void deleteByIdInvalidTest() {
        try {
            timetableService.deleteById(0L);
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void updateByIdValidTest() {
        Timetable timetable = new Timetable(null, null, null, null, null, null);
        Timetable newTimetable = new Timetable(null, null, null, "Name", null, null);
        Timetable addedTimetable = timetableService.save(timetable, 4L);
        Assertions.assertNotNull(timetableService.updateById(addedTimetable.getIdTimetable(), newTimetable));
        timetableService.deleteById(addedTimetable.getIdTimetable());
    }

    @Test
    public void updateByIdInvalidTest() {
        Timetable newTimetable = new Timetable(null, null, null, "Name", null, null);
        try {
            timetableService.updateById(0L, newTimetable);
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }
}