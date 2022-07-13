package com.example.demo;

import com.example.demo.controller.TimetableController;
import com.example.demo.model.Timetable;
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
public class TimetableControllerTests {
    @Autowired
    private TimetableController timetableController;

    @Test
    public void findAllValidTest() {
        Assertions.assertTrue(timetableController.findAll().size() > 0);
    }

    @Test
    public void findAllInvalidTest() {
        Assertions.assertFalse(timetableController.findAll().size() <= 0);
    }

    @Test
    public void saveValidTest() {
        Timetable timetable = new Timetable(null, null, null, null, null, null);
        Timetable addedTimetable = timetableController.save(timetable, 4L);
        Assertions.assertNotNull(addedTimetable);
        timetableController.deleteById(addedTimetable.getIdTimetable());
    }

    @Test
    public void deleteByIdValidTest() {
        Timetable timetable = new Timetable(null, null, null, null, null, null);
        Timetable addedTimetable = timetableController.save(timetable, 4L);
        timetableController.deleteById(addedTimetable.getIdTimetable());
        Assertions.assertTrue(true);
    }

    @Test
    public void deleteByIdInvalidTest() {
        try {
            timetableController.deleteById(0L);
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void updateByIdValidTest() {
        Timetable timetable = new Timetable(null, null, null, null, null, null);
        Timetable newTimetable = new Timetable(null, null, null, "Name", null, null);
        Timetable addedTimetable = timetableController.save(timetable, 4L);
        Assertions.assertNotNull(timetableController.update(newTimetable, addedTimetable.getIdTimetable()));
        timetableController.deleteById(addedTimetable.getIdTimetable());
    }

    @Test
    public void updateByIdInvalidTest() {
        Timetable newTimetable = new Timetable(null, null, null, "Name", null, null);
        try {
            timetableController.update(newTimetable, 0L);
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }
}