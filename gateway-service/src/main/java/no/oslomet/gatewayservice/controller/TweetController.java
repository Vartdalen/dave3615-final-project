package no.oslomet.gatewayservice.controller;

import no.oslomet.gatewayservice.model.Tweet;
import no.oslomet.gatewayservice.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tweets")
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @PostMapping("/save")
    public String saveTweet(@ModelAttribute("tweet") Tweet newTweet) {
        tweetService.saveTweet(newTweet);
        return "redirect:/";
    }
}