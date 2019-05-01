package no.oslomet.userservice.controller;

import no.oslomet.userservice.model.User;
import no.oslomet.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    Environment env;

    @RequestMapping
    public String home(){
        return "Hello from User Service running at port: " + env.getProperty("local.server.port");
    }

    @GetMapping("/users")
    public List<User> getAllUsers() { return userService.getAllUsers(); }

    @GetMapping("/users/{id}")
    @ResponseBody
    public ResponseEntity<User> getUser(@PathVariable String id) {
        try {
            long parsedId = Long.parseLong(id);
            return new ResponseEntity<>(userService.getUserById(parsedId), HttpStatus.OK);
        } catch (NumberFormatException e) {
            try {
                return new ResponseEntity<>(userService.getUserByEmail(id), HttpStatus.OK);
            } catch (NoSuchElementException e2) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUserById(id);
    }

    @PostMapping("/users")
    @ResponseBody
    public ResponseEntity<User> saveUser(@RequestBody  User newUser) {
        return new ResponseEntity<>(userService.saveUser(newUser), HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable long id,  @RequestBody  User newUser) {
        newUser.setId(id);
        return userService.saveUser(newUser);
    }
}

