package no.oslomet.gatewayservice.service;

import no.oslomet.gatewayservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    String BASE_URL = "http://localhost:8082/users";
    private RestTemplate restTemplate = new RestTemplate();

    public List<User> getAllUsers() {
        return  Arrays.stream(restTemplate.getForObject(BASE_URL, User[].class)).collect(Collectors.toList());
    }

    public User getUserById(long id) {
        return restTemplate.getForObject(BASE_URL+"/"+id, User.class);
    }

    public Optional<User> getUserByEmail(String email) {
        ResponseEntity<User> response;
        try {
            response = restTemplate.getForEntity(BASE_URL+"/"+email, User.class);
            return Optional.ofNullable(response.getBody());
        } catch (RestClientException e) {
            return Optional.empty();
        }
    }

    public Optional<User> getUserByScreenName(String screenName) {
        ResponseEntity<User> response;
        try {
            response = restTemplate.getForEntity(BASE_URL+"/"+screenName, User.class);
            return Optional.ofNullable(response.getBody());
        } catch (RestClientException e) {
            return Optional.empty();
        }
    }

    public User saveUser(User newUser) {
        ResponseEntity<User> response;
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        response = restTemplate.postForEntity(BASE_URL, newUser, User.class);
        return response.getBody();
    }

    public void updateUser(long id, User updatedUser) {restTemplate.put(BASE_URL+"/"+id, updatedUser);}

    public void deleteUserById(long id) {
        restTemplate.delete(BASE_URL+"/"+id);
    }
}
