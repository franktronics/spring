package com.marcapo.exercise.springbootstartup.controller;

import com.marcapo.exercise.springbootstartup.model.User;
import com.marcapo.exercise.springbootstartup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        Optional<User> searchedUser = userService.findByUsername(user.getUsername());
        if(searchedUser.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Dieser Nutzer existiert bereits");
        }else{
            User createdUser = userService.createUser(user);
            return ResponseEntity.ok(createdUser);
        }
    }

    @PutMapping(value = "/{username}", consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> updateUser(@PathVariable String username, @RequestBody User user) {
        Optional<User> searchedUser = userService.findByUsername(username);
        if(searchedUser.isPresent()){
            User currentUser = searchedUser.get();

            String newUsername = user.getUsername();
            if(newUsername != null) {
                currentUser.setUsername(newUsername);
            }
            String newPassword = user.getPassword();
            if(newPassword != null) {
                currentUser.setPassword(newPassword);;
            }
            User updatedUser = userService.updateUser(username, currentUser);
            return ResponseEntity.ok(updatedUser);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nutzer wurde nicht gefunden");
        }
    }

    @GetMapping(value = "", consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/search/findByUsername/{username}", consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> findByUsername(@PathVariable("username") String username) {
        Optional<User> searchedUser = userService.findByUsername(username);
        if(searchedUser.isPresent()){
            return ResponseEntity.status(HttpStatus.FOUND).body(searchedUser.get());
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nutzer wurde nicht gefunden");
        }
    }

    @GetMapping(value = "/search/deleteByUsername/{username}", consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<Object> deleteByUsername(@PathVariable("username") String username) {
        Optional<User> searchedUser = userService.findByUsername(username);
        if(searchedUser.isPresent()){
            userService.deleteByUsername(username);
            return ResponseEntity.ok("Gelöschter Nutzer: "+ username);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nutzer wurde nicht gefunden");
        }
    }
}
