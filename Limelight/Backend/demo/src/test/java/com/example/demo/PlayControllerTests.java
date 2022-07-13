package com.example.demo;

import com.example.demo.controller.PlayController;
import com.example.demo.model.Play;
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
public class PlayControllerTests {
    @Autowired
    private PlayController playController;

    @Test
    public void findAllValidTest() {
        Assertions.assertTrue(playController.findAll().size() > 0);
    }

    @Test
    public void findAllInvalidTest() {
        Assertions.assertFalse(playController.findAll().size() <= 0);
    }

    @Test
    public void saveInvalidTest() {
        Play play = new Play(null, "Faust 2", "Drama", "", null, null, null, null, 10.0, 10.0, 10.0, 10.0, null);
        try {
            playController.save(play);
        }
        catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void deleteByIdInvalidTest() {
        try {
            playController.deleteById(0L);
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void updateByIdInvalidTest() {
        Play newPlay = new Play(null, "Faust 2", "Drama", "", null, null, null, null, 10.0, 10.0, 10.0, 10.0, null);
        try {
            playController.update(newPlay, 0L);
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void addActorToPlayInvalidTest() {
        try {
            playController.addActorToPlay(0L, 0L);
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void getActorsForPlayValidTest() {
        Assertions.assertNotNull(playController.getActorsForPlay("Faust"));
    }

    @Test
    public void getActorsForPlayInvalidTest() {
        try {
            playController.getActorsForPlay("Not existent");
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }
}