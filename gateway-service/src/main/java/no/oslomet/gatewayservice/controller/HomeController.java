package no.oslomet.gatewayservice.controller;

import no.oslomet.gatewayservice.model.Tweet;
import no.oslomet.gatewayservice.model.User;
import no.oslomet.gatewayservice.service.TweetService;
import no.oslomet.gatewayservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private TweetService tweetService;

    @GetMapping("/")
    public String home(Model model){
        List<Tweet> tweetList = tweetService.getAllTweets();
        model.addAttribute("tweetList", tweetList);

        Optional<User> user = getUserSession(model, SecurityContextHolder.getContext().getAuthentication(), userService);
        if(user.isPresent()) {
            System.out.println(user.toString());
            model.addAttribute("user", user.get());
        }
        return "index";
    }

    private Optional<User> getUserSession(Model model, Authentication auth, UserService userService) {
        return userService.getUserByEmail(auth.getName());
    }
}
