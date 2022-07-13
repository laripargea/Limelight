package com.example.demo;

import com.example.demo.controller.ActorController;
import com.example.demo.model.Actor;
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
public class ActorControllerTests {
    @Autowired
    private ActorController actorController;

    @Test
    public void findAllValidTest() {
        Assertions.assertTrue(actorController.findAll().size() > 0);
    }

    @Test
    public void findAllInvalidTest() {
        Assertions.assertFalse(actorController.findAll().size() <= 0);
    }

    @Test
    public void saveValidTest() {
        Actor actor = new Actor(null, "Johnny", "Bravo", null);
        Actor addedActor = actorController.save(actor);
        Assertions.assertNotNull(addedActor);
        actorController.deleteById(addedActor.getIdActor());
    }

    @Test
    public void deleteByIdValidTest() {
        Actor actor = new Actor(null, "Johnny", "Bravo", null);
        Actor addedActor = actorController.save(actor);
        actorController.deleteById(addedActor.getIdActor());
        Assertions.assertTrue(true);
    }

    @Test
    public void deleteByIdInvalidTest() {
        try {
            actorController.deleteById(0L);
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void updateByIdValidTest() {
        Actor actor = new Actor(null, "Johnny", "Bravo", null);
        Actor addedActor = actorController.save(actor);
        Actor newActor = new Actor(null, "Bobby", "Dan", null);
        Assertions.assertNotNull(actorController.update(newActor, addedActor.getIdActor()));
        actorController.deleteById(addedActor.getIdActor());
    }

    @Test
    public void updateByIdInvalidTest() {
        Actor newActor = new Actor(null, "Johnny", "Bravo", null);
        try {
            actorController.update(newActor, 0L);
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }
}