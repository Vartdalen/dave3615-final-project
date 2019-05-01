package no.oslomet.tweetservice.service;

import no.oslomet.tweetservice.model.Tweet;
import no.oslomet.tweetservice.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    public List<Tweet> getAllTweets() {
        return tweetRepository.findAll();
    }

    public Tweet getTweetById(long id) {
        return tweetRepository.findById(id).get();
    }

    public Tweet saveTweet(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    public void deleteTweetById(long id) {
        tweetRepository.deleteById(id);
    }
}

