package no.oslomet.gatewayservice.controller;

import no.oslomet.gatewayservice.model.User;
import no.oslomet.gatewayservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    //rest
    @PostMapping("/user")
    public String saveUser(@ModelAttribute("user") User newUser) {
        newUser.setRole("USER");
        userService.saveUser(newUser);
        return "redirect:/";
    }

    //non rest
    @PostMapping("/follow")
    public String followUser(@RequestParam(value="follower",required=true) Long follower, @RequestParam(value="followed",required=true) Long followed) {
        List<User> userList = userService.getAllUsers();
        User user = new User();
        for (User userInList: userList) {
            if(userInList.getFollowers().isEmpty()) {
                userInList.getFollowers().add(follower);
                user = userInList;
            } else if (userInList.getId() == followed) {
                Collection<Long> followerList = userInList.getFollowers();
                for (long followerInCollection : followerList) {
                    if (followerInCollection == follower) {
                        return "redirect:/";
                    }
                    userInList.getFollowers().add(follower);
                    user = userInList;
                }
            }
        }
        userService.updateUser(user.getId(), user);
        return "redirect:/profile";
    }
}