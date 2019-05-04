package no.oslomet.tweetservice.controller;

import no.oslomet.tweetservice.model.Tweet;
import no.oslomet.tweetservice.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TweetController {

    @Autowired
    TweetService tweetService;
    @Autowired
    Environment env;

    @RequestMapping
    public String home(){
        return "Hello from Tweet Service running at port: " + env.getProperty("local.server.port");
    }

    @GetMapping("/tweets")
    public List<Tweet> getAllTweets() { return tweetService.getAllTweets(); }

    @GetMapping("/tweets/{id}")
    @ResponseBody
    public ResponseEntity<Tweet> getTweet(@PathVariable String id) {
        return new ResponseEntity<>(tweetService.getTweetById(Long.parseLong(id)), HttpStatus.OK);
    }

    @DeleteMapping("/tweets/{id}")
    public void deleteTweet(@PathVariable long id) {
        tweetService.deleteTweetById(id);
    }

    @PostMapping("/tweets")
    @ResponseBody
    public ResponseEntity<Tweet> saveTweet(@RequestBody Tweet newTweet) {
        return new ResponseEntity<>(tweetService.saveTweet(newTweet), HttpStatus.OK);
    }

    @PutMapping("/tweets/{newId}")
    public Tweet updateTweet(@PathVariable long newId,  @RequestBody  Tweet newTweet) {
        newTweet.setId(newId);
        return tweetService.saveTweet(newTweet);
    }
}

