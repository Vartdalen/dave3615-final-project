package no.oslomet.gatewayservice.controller;

import no.oslomet.gatewayservice.model.Follow;
import no.oslomet.gatewayservice.model.Tweet;
import no.oslomet.gatewayservice.model.User;
import no.oslomet.gatewayservice.service.FollowService;
import no.oslomet.gatewayservice.service.TweetService;
import no.oslomet.gatewayservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FollowService followService;
    @Autowired
    private TweetService tweetService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/user")
    public String saveUser(@ModelAttribute("user") User newUser) {
        newUser.setRole("USER");
        userService.saveUser(newUser);
        return "redirect:/";
    }

    @PostMapping("/user/update")
    public String updateUser(@ModelAttribute("user") User user) {
        if(!passwordEncoder.matches(user.getPassword(), userService.getUserById(user.getId()).getPassword())) {
            return "redirect:/credentialError";
        }
        userService.updateUser(user.getId(), user, false);
        return "redirect:/profile/" + userService.getUserById(user.getId()).getScreenName();
    }

    @PostMapping("/user/update/admin")
    public String updateUserAdmin(@ModelAttribute("user") User user) {
        if(!getUserSession(userService, SecurityContextHolder.getContext().getAuthentication()).get().getRole().equals("ADMIN")) {
            return "redirect:/forbiddenError";
        }
        userService.updateUser(user.getId(), user, true);
        return "redirect:/profile/" + userService.getUserById(user.getId()).getScreenName();
    }

    @GetMapping("/profile/{screenName}")
    public String profile(@PathVariable String screenName, Model model){
        Optional<User> user = getUserSession(userService, SecurityContextHolder.getContext().getAuthentication());
        Optional<User> user2 = userService.getUserByScreenName(screenName);

        List<Follow> followList = followService.getAllFollows();
        List<Tweet> tweetList = tweetService.getAllTweets();

        if(user.isPresent()) {
            model.addAttribute("user", user.get());
        }
        setModelUser2FollowTweet(user, user2, followList, tweetList, model);
        setModelFollowed(user, user2, followList, model);

        return "profile";
    }


    //private helper methods

    private Optional<User> getUserSession(UserService userService, Authentication auth) {
        return userService.getUserByEmail(auth.getName());
    }

    //already following other person
    private void setModelFollowed (Optional<User> user, Optional<User> user2, List<Follow> followList, Model model) {
        for(Follow follow: followList) {
            if(user.isPresent() && user2.isPresent() && follow.getIdFollower() == user.get().getId() && follow.getIdFollowed() == user2.get().getId()) {
                model.addAttribute("followed", new Object());
            }
        }
    }

    //on other person's profile, adds second profile to profile page
    //also adds correct amount of followers depending on page
    private void setModelUser2FollowTweet (Optional<User> user, Optional<User> user2, List<Follow> followList, List<Tweet> tweetList, Model model) {
        int followers = 0;
        int following = 0;
        int tweets = 0;
        if(user.isPresent() && user2.isPresent() && user.get().getId() != user2.get().getId()) {
            //on user2 page
            followers = getFollowers(user2.get(), followList);
            following = getFollowing(user2.get(), followList);
            tweets = getTweets(user2.get(), tweetList);
            model.addAttribute("user2", user2.get());
        } else if (user.isPresent()) {
            followers = getFollowers(user.get(), followList);
            following = getFollowing(user.get(), followList);
            tweets = getTweets(user.get(), tweetList);
        }
        model.addAttribute("followers", followers);
        model.addAttribute("following", following);
        model.addAttribute("tweets", tweets);
    }
            //private helper methods for previous method
            private int getFollowers (User user, List<Follow> followList) {
                int followers = 0;
                for(Follow follow : followList) {
                    if (follow.getIdFollowed() == user.getId()) {
                        followers++;
                    }
                }
                return followers;
            }

            private int getFollowing (User user, List<Follow> followList) {
                int following = 0;
                for(Follow follow : followList) {
                    if (follow.getIdFollower() == user.getId()) {
                        following++;
                    }
                }
                return following;
            }

            private int getTweets (User user, List<Tweet> tweetList) {
                int tweets = 0;
                for (Tweet tweet: tweetList) {
                    if(tweet.getIdUser() == user.getId()) {
                        tweets++;
                    }
                }
                return tweets;
            }

}