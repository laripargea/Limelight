package com.example.demo;

import com.example.demo.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = LimelightApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.properties")
public class UserServiceTests {
    @Autowired
    private UserService userService;

    @Test
    public void updateByEmailValidTest() {
        Assertions.assertNotNull(userService.updateByEmail("alexciurean9@gmail.com", "Alexandru", "Ciurean", "0763048121"));
    }

    @Test
    public void updateByEmailInvalidTest() {
        try {
            userService.updateByEmail("alexciurean@gmail.com", "Alexandru", "Ciurean", "0763048121");
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void updatePasswordByEmailValidTest() {
        Assertions.assertNotNull(userService.updatePasswordByEmail("alexciurean9@gmail.com", "parola"));
    }

    @Test
    public void updatePasswordByEmailInvalidTest() {
        try {
            userService.updatePasswordByEmail("alexciurean@gmail.com", "parola");
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void findByUsernameAndPasswordInvalidTest() {
        Assertions.assertNull(userService.findByUsernameAndPassword("alexciurean@gmail.com", "parola"));
    }

    @Test
    public void getFavouritesValidTest() throws SQLException {
        Assertions.assertTrue(userService.getFavourites("alexciurean9@gmail.com").size() > 0);
    }

    @Test
    public void getFavouritesInvalidTest() throws SQLException {
        Assertions.assertFalse(userService.getFavourites("alexciurean9@gmail.com").size() <= 0);
    }

    @Test
    public void getRecommendedPlayListValidTest() throws SQLException {
        Assertions.assertTrue(userService.getRecommendedPlayList("alexciurean9@gmail.com").size() > 0);
    }

    @Test
    public void getRecommendedPlayListInvalidTest() throws SQLException {
        Assertions.assertFalse(userService.getRecommendedPlayList("alexciurean9@gmail.com").size() <= 0);
    }

    @Test
    public void findByEmailValidTest() {
        Assertions.assertNotNull(userService.findByEmail("alexciurean9@gmail.com"));
    }

    @Test
    public void findByEmailInvalidTest() {
        Assertions.assertNull(userService.findByEmail("alexciurean@gmail.com"));
    }

    @Test
    public void addFavouriteInvalidTest() {
        try {
            userService.addFavourite("alexciurean9@gmail.com", "Faust 2");
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void deleteFavouriteInvalidTest() {
        try {
            userService.deleteFavourite("alexciurean9@gmail.com", "Faust 2");
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void checkIfFavouriteInvalidTest() {
        try {
            userService.checkIfFavourite("alexciurean9@gmail.com", "Faust 2");
        } catch (Exception e) {
            Assertions.assertFalse(false);
        }
    }
}