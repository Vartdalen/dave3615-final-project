package no.oslomet.tweetservice.repository;

import no.oslomet.tweetservice.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
}