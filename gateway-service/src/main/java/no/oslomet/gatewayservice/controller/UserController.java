package no.oslomet.gatewayservice.controller;

import no.oslomet.gatewayservice.model.Follow;
import no.oslomet.gatewayservice.model.Tweet;
import no.oslomet.gatewayservice.model.User;
import no.oslomet.gatewayservice.model.view.ViewTweet;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FollowService followService;
    @Autowired
    private TweetService tweetService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User newUser) {
        newUser.setRole("USER");
        userService.saveUser(newUser);
        return "redirect:/";
    }

    @GetMapping("/{screenName}")
    public String profile(@PathVariable String screenName, Model model){
        Optional<User> user = getUserSession(userService, SecurityContextHolder.getContext().getAuthentication());
        Optional<User> user2 = userService.getUserByScreenName(screenName);

        List<Follow> followList = followService.getAllFollows();
        List<Tweet> tweetList = tweetService.getAllTweets();
        List<User> userList = userService.getAllUsers();
        List<ViewTweet> viewTweetList = new ArrayList<>();

        for(Tweet tweetInList: tweetList) {
            for (User userInList: userList) {
                if(tweetInList.getIdUser() == userInList.getId()) {
                    viewTweetList.add(new ViewTweet(tweetInList.getId(), tweetInList.getIdParent(), userInList.getId(), userInList.getScreenName(), userInList.getFirstName(), userInList.getLastName(), tweetInList.getUrlImage(), tweetInList.getTimestamp(), tweetInList.getText()));
                }
            }
        }

        if(user.isPresent()) {
            model.addAttribute("user", user.get());
        }
        setModelUser2FollowTweet(user, user2, followList, tweetList, model);
        setModelFollowed(user, user2, followList, model);

        model.addAttribute("viewTweetList", viewTweetList);
        model.addAttribute("userList", userList);

        return "profile";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user) {
        //clear session if user changes email
        if(!user.getEmail().equals(userService.getUserById(user.getId()).getEmail())) {
            SecurityContextHolder.clearContext();
        }
        if(!passwordEncoder.matches(user.getPassword(), userService.getUserById(user.getId()).getPassword())) {
            return "redirect:/errors/credentialError";
        }
        userService.updateUser(user.getId(), user);
        return "redirect:/users/" + userService.getUserById(user.getId()).getScreenName();
    }

    @PostMapping("/update/admin")
    public String updateUserAdmin(@ModelAttribute("user") User user) {
        if(!getUserSession(userService, SecurityContextHolder.getContext().getAuthentication()).get().getRole().equals("ADMIN")) {
            return "redirect:/errors/forbiddenError";
        }
        userService.updateUser(user.getId(), user);
        return "redirect:/users/" + userService.getUserById(user.getId()).getScreenName();
    }

    @PostMapping("/delete")
    public String deleteUserById(@RequestParam(value="id") Long idUser) {
        userService.deleteUserById(idUser);
        SecurityContextHolder.clearContext();
        return "redirect:/";
    }

    @PostMapping("/delete/admin")
    public String deleteUserByIdAdmin(@RequestParam(value="id") Long idUser) {
        if(!getUserSession(userService, SecurityContextHolder.getContext().getAuthentication()).get().getRole().equals("ADMIN")) {
            return "redirect:/errors/forbiddenError";
        }
        userService.deleteUserById(idUser);
        return "redirect:/";
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
            List<User> followedUserList = getFollowedUserList(user2.get(), followList);
            followers = getFollowers(user2.get(), followList);
            following = getFollowing(user2.get(), followList);
            tweets = getTweets(user2.get(), tweetList);
            model.addAttribute("user2", user2.get());
            model.addAttribute("followTweetList", getFollowedTweetsList(tweetList, followedUserList));
        } else if (user.isPresent()) {
            List<User> followedUserList = getFollowedUserList(user.get(), followList);
            followers = getFollowers(user.get(), followList);
            following = getFollowing(user.get(), followList);
            tweets = getTweets(user.get(), tweetList);
            model.addAttribute("followTweetList", getFollowedTweetsList(tweetList, followedUserList));
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

            private List<User> getFollowedUserList(User user, List<Follow> followList) {
                List<User> followUserList = new ArrayList<>();
                for(Follow follow: followList) {
                    if(follow.getIdFollower() == user.getId()) {
                        followUserList.add(userService.getUserById(follow.getIdFollowed()));
                    }
                }
                return followUserList;
            }

            private List<ViewTweet> getFollowedTweetsList(List<Tweet> tweetList, List<User> followedUserList) {
                List<ViewTweet> followTweetList = new ArrayList<>();
                for(Tweet tweet: tweetList) {
                    for(User followUser: followedUserList) {
                        if(tweet.getIdUser() == followUser.getId()) {
                            followTweetList.add(new ViewTweet(tweet.getId(), tweet.getIdParent(), followUser.getId(), followUser.getScreenName(), followUser.getFirstName(), followUser.getLastName(), tweet.getUrlImage(), tweet.getTimestamp(), tweet.getText()));
                        }
                    }
                }
                return followTweetList;
            }

}