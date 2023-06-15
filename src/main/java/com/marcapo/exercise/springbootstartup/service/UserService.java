package com.marcapo.exercise.springbootstartup.service;

import com.marcapo.exercise.springbootstartup.model.User;
import com.marcapo.exercise.springbootstartup.repository.UserRepository;
import lombok.Data;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public User createUser(User user) {
        String encodedPassword = encodeMD5(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public User updateUser(String username, User user) {
        user.setUsername(user.getUsername());
        String encodedPassword = encodeMD5(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void deleteByUsername(String username){
        userRepository.deleteByUsername(username);
    }

    private String encodeMD5(final String value) {
        try {
            if (value != null) {
                byte[] bytesOfMessage = value.getBytes(StandardCharsets.UTF_8);
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] thedigest = md.digest(bytesOfMessage);
                return MD5Encoder.encode(thedigest);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
