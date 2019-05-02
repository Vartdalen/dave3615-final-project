package no.oslomet.gatewayservice.controller;

import no.oslomet.gatewayservice.model.Tweet;
import no.oslomet.gatewayservice.service.TweetService;
import no.oslomet.gatewayservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TweetController {

    @Autowired
    private UserService userService;
    @Autowired
    private TweetService tweetService;

    @PostMapping("/tweet")
    public String saveTicket(@ModelAttribute("tweet") Tweet newTweet) {
        tweetService.saveTweet(newTweet);
        return "redirect:/";
    }
}