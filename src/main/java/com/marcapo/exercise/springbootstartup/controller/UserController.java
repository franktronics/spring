package com.marcapo.exercise.springbootstartup.controller;

import com.marcapo.exercise.springbootstartup.model.User;
import com.marcapo.exercise.springbootstartup.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping(consumes = "application/octet-stream")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        Optional<User> searchedUser = userService.findByUsername(user.getUsername());
        if(searchedUser.isEmpty()){
            return ResponseEntity.ok(userService.createUser(user));
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Dieser Nutzer existiert bereits");
        }
    }

    @PutMapping(value = "/{username}", consumes = "application/octet-stream")
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

    @GetMapping(consumes = "application/octet-stream")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/search/findByUsername/{username}", consumes = "application/octet-stream")
    public ResponseEntity<Object> findByUsername(@PathVariable("username") String username) {
        Optional<User> searchedUser = userService.findByUsername(username);
        if(searchedUser.isPresent()){
            return ResponseEntity.ok(searchedUser);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nutzer wurde nicht gefunden");
        }
    }

    @GetMapping(value = "/search/deleteByUsername/{username}", consumes = "application/octet-stream")
    ResponseEntity<Object> deleteByUsername(@PathVariable("username") String username) {
        userService.deleteByUsername(username);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Gelöschter Nutzer: "+ username);
    }
}
