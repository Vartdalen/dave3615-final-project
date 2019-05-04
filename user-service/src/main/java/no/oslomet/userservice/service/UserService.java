package no.oslomet.userservice.service;

import no.oslomet.userservice.exception.InvalidInputException;
import no.oslomet.userservice.exception.UserExistsException;
import no.oslomet.userservice.model.User;
import no.oslomet.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return userRepository.findById(id).get();
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email).get();
    }

    public User getUserByScreenName(String screenName) {
        return userRepository.getUserByScreenName(screenName).get();
    }

    public User saveUser(User user, boolean override) {
        Matcher m = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])").matcher(user.getEmail());
        Matcher m2 = Pattern.compile("^[^\\W_]+$").matcher(user.getScreenName());
        if(!m.matches() || !m2.matches()) {
            throw new InvalidInputException();
        }
        if((userRepository.getUserByEmail(user.getEmail()).isPresent() || userRepository.getUserByScreenName(user.getScreenName()).isPresent()) && !override) {
            throw new UserExistsException();
        }
        System.out.println(user.toString());
        return userRepository.save(user);
    }

    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }
}

