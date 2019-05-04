package no.oslomet.gatewayservice.service;

import no.oslomet.gatewayservice.model.Follow;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FollowService {

    String BASE_URL = "http://localhost:8084/follows";
    private RestTemplate restTemplate = new RestTemplate();

    public List<Follow> getAllFollows() {
        return  Arrays.stream(restTemplate.getForObject(BASE_URL, Follow[].class)).collect(Collectors.toList());
    }

    public Follow getFollowById(long id) {
        return restTemplate.getForObject(BASE_URL+"/"+id, Follow.class);
    }

    public Optional<Follow> getFollowByEmail(String email) {
        ResponseEntity<Follow> response;
        try {
            response = restTemplate.getForEntity(BASE_URL+"/"+email, Follow.class);
            return Optional.ofNullable(response.getBody());
        } catch (RestClientException e) {
            return Optional.empty();
        }
    }

    public Optional<Follow> getFollowByScreenName(String screenName) {
        ResponseEntity<Follow> response;
        try {
            response = restTemplate.getForEntity(BASE_URL+"/"+screenName, Follow.class);
            return Optional.ofNullable(response.getBody());
        } catch (RestClientException e) {
            return Optional.empty();
        }
    }

    public Follow saveFollow(Follow newFollow) {
        ResponseEntity<Follow> response;
        response = restTemplate.postForEntity(BASE_URL, newFollow, Follow.class);
        return response.getBody();
    }

    public void updateFollow(long id, Follow updatedFollow) {restTemplate.put(BASE_URL+"/"+id, updatedFollow);}

    public void deleteFollowById(long id) {
        restTemplate.delete(BASE_URL+"/"+id);
    }
}
