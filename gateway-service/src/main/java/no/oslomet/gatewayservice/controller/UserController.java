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
        Optional<User> user = getUserSession(model, SecurityContextHolder.getContext().getAuthentication(), userService);
        Optional<User> user2 = userService.getUserByScreenName(screenName);

        if(user.isPresent()) {
            model.addAttribute("user", user.get());
        }

        //on other person's profile
        if(user2.isPresent()) {
            if(user.get().getId() != user2.get().getId()) {
                model.addAttribute("user2", user2.get());
            }
        }

        //already following other person
        List<Follow> followList = followService.getAllFollows();
        for(Follow follow: followList) {
            if(follow.getIdFollower() == user.get().getId() && follow.getIdFollowed() == user2.get().getId()) {
                model.addAttribute("followed", new Object());
            }
        }

        return "profile";
    }

    private Optional<User> getUserSession(Model model, Authentication auth, UserService userService) {
        return userService.getUserByEmail(auth.getName());
    }
}