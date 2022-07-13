package com.example.demo.service;

import com.example.demo.Role;
import com.example.demo.model.Play;
import com.example.demo.model.User;
import com.example.demo.repository.PlayRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;

@Service
public class UserService {
    //class for the deviation for the recommendation's algorithm
    static class Deviation {
        Play play;
        Double score;

        public Deviation(Play play, Double score) {
            this.play = play;
            this.score = score;
        }
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlayRepository playRepository;

    @Transactional
    public User save(User user) {
        user.setRole(Role.user);
        String password = user.getPassword();
        String encryptedpassword = null;

        //password encryption
        try {
            /* MessageDigest instance for MD5. */
            MessageDigest m = MessageDigest.getInstance("MD5");

            /* Add plain-text password bytes to digest using MD5 update() method. */
            m.update(password.getBytes());

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

        user.setPassword(encryptedpassword);
        return userRepository.save(user);
    }

    @Transactional
    public User updateByEmail(String email, String firstName, String lastName, String mobileNumber) {
        User user = userRepository.findUserByEmail(email);
        if (!Objects.equals(firstName, "empty"))
            user.setFirstName(firstName);
        if (!Objects.equals(lastName, "empty"))
            user.setLastName(lastName);
        if (!Objects.equals(mobileNumber, "empty"))
            user.setMobileNumber(mobileNumber);
        return userRepository.save(user);
    }

    @Transactional
    public User updatePasswordByEmail(String email, String password) {
        User user = userRepository.findUserByEmail(email);
        String encryptedpassword = null;

        //password encryption
        try {
            /* MessageDigest instance for MD5. */
            MessageDigest m = MessageDigest.getInstance("MD5");

            /* Add plain-text password bytes to digest using MD5 update() method. */
            m.update(password.getBytes());

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

        user.setPassword(encryptedpassword);
        return userRepository.save(user);
    }

    public User findByUsernameAndPassword(String email, String password) {
        return userRepository.findUserByEmailAndPassword(email, password);
    }

    public List<Play> getFavourites(String email) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", "postgres", "nissanskyline123");
        Statement statement = connection.createStatement();
        User user = userRepository.findUserByEmail(email);
        String query = "SELECT id_play FROM public.favourites WHERE id_user = " + user.getIdUser();
        ResultSet resultSet = statement.executeQuery(query);
        long columnValue;
        resultSet.getMetaData();
        List<Long> favouritesIds = new ArrayList<>();
        while (resultSet.next()) {
            columnValue = resultSet.getLong(1);
            favouritesIds.add(columnValue);
        }

        //get list of favourite plays
        List<Play> favourites = new ArrayList<>();
        for (Play play : playRepository.findAll())
            if (favouritesIds.contains(play.getIdPlay()))
                favourites.add(play);
        return favourites;
    }

    public List<Play> getRecommendedPlayList(String email) throws SQLException {
        List<Play> allPlays = playRepository.findAll();
        List<Play> recommended = new ArrayList<>();

        //if the user has no favourites, recommend the first 5 plays in the database
        if (getFavourites(email).size() == 0)
            return playRepository.findAll().subList(0, 5);

        List<Play> likedPlays = getFavourites(email);
        double userScore = 0.0;

        //calculate the score of the user
        for (Play play : likedPlays) {
            double score = play.getScore();
            userScore += score;
        }
        userScore /= likedPlays.size();

        List<Deviation> deviations = new ArrayList<>();

        //form the list of deviations based on plays' scores
        for (Play play : allPlays) {
            double score = play.getScore();
            double diff = Math.abs(score - userScore);
            if (!likedPlays.contains(play))
                deviations.add(new Deviation(play, diff));
        }

        //sort the list of deviations ascending
        for (int i = 0; i < deviations.size() - 1; i++)
            for (int j = i + 1; j < deviations.size(); j++)
                if (deviations.get(i).score < deviations.get(j).score) {
                    Deviation aux = deviations.get(i);
                    deviations.set(i, deviations.get(j));
                    deviations.set(j, aux);
                }

        //form the list of recommendations
        for (Deviation deviation : deviations) {
            recommended.add(deviation.play);
        }

        //get the list of recommendations or the first 5 plays of it, if it has more than 5 plays
        if (recommended.size() < 5)
            return recommended;
        else
            return recommended.subList(0, 5);
    }

    @Transactional
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public void addFavourite(String email, String title) {
        User user = userRepository.findUserByEmail(email);
        Play play = playRepository.findPlayByTitle(title);
        Set<Play> favourites = user.getFavourites();
        if (!favourites.contains(play)) {
            favourites.add(play);
            user.setFavourites(favourites);
            userRepository.save(user);
        }
    }

    public void deleteFavourite(String email, String title) {
        User user = userRepository.findUserByEmail(email);
        Set<Play> favourites = user.getFavourites();
        for (Play fav : favourites)
            if (Objects.equals(fav.getTitle(), title)) {
                favourites.remove(fav);
                user.setFavourites(favourites);
                break;
            }
        userRepository.save(user);
    }

    public boolean checkIfFavourite(String email, String title) {
        User user = userRepository.findUserByEmail(email);
        Set<Play> favourites = user.getFavourites();
        for (Play fav : favourites)
            if (Objects.equals(fav.getTitle(), title)) {
                return true;
            }
        return false;
    }
}