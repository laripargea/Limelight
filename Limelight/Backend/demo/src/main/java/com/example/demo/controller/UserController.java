package com.example.demo.controller;

import com.example.demo.model.Play;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users/add")
    public User save(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping("/users/update/{email}/{firstName}/{lastName}/{mobileNumber}")
    public User update(@PathVariable("email") String email, @PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, @PathVariable("mobileNumber") String mobileNumber) {
        return userService.updateByEmail(email, firstName, lastName, mobileNumber);
    }

    @GetMapping("/users/update/{email}/{password}")
    public User updatePassword(@PathVariable("email") String email, @PathVariable("password") String password) {
        return userService.updatePasswordByEmail(email, password);
    }

    @GetMapping("/users/favourite/{email}/{title}")
    public void addFavourite(@PathVariable("email") String email, @PathVariable("title") String title) {
        userService.addFavourite(email, title);
    }

    @GetMapping("/users/favourite/delete/{email}/{title}")
    public void deleteFavourite(@PathVariable("email") String email, @PathVariable("title") String title) {
        userService.deleteFavourite(email, title);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        String tempEmail = user.getEmail();
        String tempPassword = user.getPassword();
        String encryptedpassword = null;

        //password encryption
        try {
            /* MessageDigest instance for MD5. */
            MessageDigest m = MessageDigest.getInstance("MD5");

            /* Add plain-text password bytes to digest using MD5 update() method. */
            m.update(tempPassword.getBytes());

            /* Convert the hash value into bytes */
            byte[] bytes = m.digest();

            /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            /* Complete hashed password in hexadecimal format */
            encryptedpassword = s.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        User userObj = null;
        if (tempEmail != null && encryptedpassword != null)
            userObj = userService.findByUsernameAndPassword(tempEmail, encryptedpassword);
        return userObj;
    }

    @GetMapping("/users/favourites/{email}")
    public List<Play> getFavourites(@PathVariable("email") String email) throws SQLException {
        return userService.getFavourites(email);
    }

    @GetMapping("/users/recommendations/{email}")
    public List<Play> getRecommendations(@PathVariable("email") String email) throws SQLException {
        return userService.getRecommendedPlayList(email);
    }

    @GetMapping("/users/{email}")
    public User findByEmail(@PathVariable("email") String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/users/isFavourite/{email}/{title}")
    public boolean checkIfFavourite(@PathVariable("email") String email, @PathVariable("title") String title) {
        return userService.checkIfFavourite(email, title);
    }
}