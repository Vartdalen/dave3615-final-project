package no.oslomet.gatewayservice.controller;

import no.oslomet.gatewayservice.model.Tweet;
import no.oslomet.gatewayservice.model.User;
import no.oslomet.gatewayservice.model.view.ViewTweet;
import no.oslomet.gatewayservice.service.TweetService;
import no.oslomet.gatewayservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private TweetService tweetService;

    @GetMapping("/")
    public String home(Model model){
        List<Tweet> tweetList = tweetService.getAllTweets();
        model.addAttribute("tweetList", tweetList);

        Optional<User> userSession = getUserSession(model, SecurityContextHolder.getContext().getAuthentication(), userService);
        if(userSession.isPresent()) {
            model.addAttribute("user", userSession.get());
        }

        List<User> userList = userService.getAllUsers();
        List<ViewTweet> viewTweetList = new ArrayList<>();
        for (Tweet tweet: tweetList) {
            for (User user: userList) {
                if(tweet.getIdUser() == user.getId()) {
                    viewTweetList.add(new ViewTweet(tweet.getId(), tweet.getIdParent(), user.getId(), user.getScreenName(), user.getFirstName(), user.getLastName(), tweet.getUrlImage(), tweet.getTimestamp(), tweet.getText()));
                }
            }
        }

        model.addAttribute("viewTweetList", viewTweetList);
        return "index";
    }

    private Optional<User> getUserSession(Model model, Authentication auth, UserService userService) {
        return userService.getUserByEmail(auth.getName());
    }
}
