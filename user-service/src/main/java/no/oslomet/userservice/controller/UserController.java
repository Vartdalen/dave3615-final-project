package no.oslomet.userservice.controller;

import no.oslomet.userservice.model.database.User;
import no.oslomet.userservice.model.exception.InvalidInputException;
import no.oslomet.userservice.model.exception.UserExistsException;
import no.oslomet.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private Environment env;

    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final String SCREEN_NAME_REGEX = "^[^\\W_]+$";

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
                Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(id);
                while (m.find()) {
                    return new ResponseEntity<>(userService.getUserByEmail(id).get(), HttpStatus.OK);
                }
                return new ResponseEntity<>(userService.getUserByScreenName(id).get(), HttpStatus.OK);
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
    public ResponseEntity<User> saveUser(@RequestBody User newUser) {
        Matcher m = Pattern.compile(EMAIL_REGEX).matcher(newUser.getEmail());
        Matcher m2 = Pattern.compile(SCREEN_NAME_REGEX).matcher(newUser.getScreenName());
        if(!m.matches() || !m2.matches()) {
            throw new InvalidInputException();
        }

        if((userService.getUserByEmail(newUser.getEmail()).isPresent() || userService.getUserByScreenName(newUser.getScreenName()).isPresent())) {
            throw new UserExistsException();
        }
        return new ResponseEntity<>(userService.saveUser(newUser), HttpStatus.OK);
    }

    @PutMapping("/users/{newId}")
    public User updateUser(@PathVariable long newId, @RequestBody User user) {
        user.setId(newId);
        return userService.saveUser(user);
    }
}

