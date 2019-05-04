package no.oslomet.gatewayservice.controller;

import no.oslomet.gatewayservice.model.Follow;
import no.oslomet.gatewayservice.model.User;
import no.oslomet.gatewayservice.service.FollowService;
import no.oslomet.gatewayservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PostMapping("/user")
    public String saveUser(@ModelAttribute("user") User newUser) {
        newUser.setRole("USER");
        userService.saveUser(newUser);
        return "redirect:/";
    }

    @GetMapping("/profile/{screenName}")
    public String profile(@PathVariable String screenName, Model model){
        User user = getUserSession(model, SecurityContextHolder.getContext().getAuthentication(), userService).get();
        User user2 = userService.getUserByScreenName(screenName).get();
        List<Follow> followList = followService.getAllFollows();

        model.addAttribute("user", user);
        setModelUser2AndFollow(user, user2, followList, model);
        setModelFollowed(user, user2, followList, model);

        return "profile";
    }


    //private helper methods

    private Optional<User> getUserSession(Model model, Authentication auth, UserService userService) {
        return userService.getUserByEmail(auth.getName());
    }

    //already following other person
    private void setModelFollowed (User user, User user2, List<Follow> followList, Model model) {
        for(Follow follow: followList) {
            if(follow.getIdFollower() == user.getId() && follow.getIdFollowed() == user2.getId()) {
                model.addAttribute("followed", new Object());
            }
        }
    }

    //on other person's profile, adds second profile to profile page
    //also adds correct amount of followers depending on page
    private void setModelUser2AndFollow (User user, User user2, List<Follow> followList, Model model) {
        int followers = 0;
        int following = 0;
        if(user.getId() != user2.getId()) {
            //on user2 page
            followers = getFollowers(user2, followList);
            following = getFollowing(user2, followList);
            model.addAttribute("user2", user2);
        } else {
            followers = getFollowers(user, followList);
            following = getFollowing(user, followList);
        }
        model.addAttribute("followers", followers);
        model.addAttribute("following", following);
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

}