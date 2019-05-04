package no.oslomet.userservice.service;

import no.oslomet.userservice.exception.UserExistsException;
import no.oslomet.userservice.model.User;
import no.oslomet.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

