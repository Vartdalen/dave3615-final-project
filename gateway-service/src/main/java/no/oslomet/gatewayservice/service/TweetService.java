package no.oslomet.gatewayservice.service;

import no.oslomet.gatewayservice.model.Tweet;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TweetService {

    String BASE_URL = "http://localhost:8083/tweets";
    private RestTemplate restTemplate = new RestTemplate();

    public List<Tweet> getAllTweets() {
        return Arrays.stream(restTemplate.getForObject(BASE_URL, Tweet[].class)).collect(Collectors.toList());
    }

    public Tweet getTweetById(long id) {
        return restTemplate.getForObject(BASE_URL+"/"+id, Tweet.class);
    }

    public Tweet saveTweet(Tweet newTweet) {
        ResponseEntity<Tweet> response;
        response = restTemplate.postForEntity(BASE_URL, newTweet, Tweet.class);
        return response.getBody();
    }

    public void updateTweet(long id, Tweet updatedTweet) { restTemplate.put(BASE_URL+"/"+id, updatedTweet); }

    public void deleteTweetById(long id) {
        restTemplate.delete(BASE_URL+"/"+id);
    }
}
