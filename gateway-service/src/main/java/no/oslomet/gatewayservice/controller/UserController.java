package no.oslomet.gatewayservice.controller;

import no.oslomet.gatewayservice.model.User;
import no.oslomet.gatewayservice.model.request.UserRequest;
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

    //rest
    @PostMapping("/user")
    public String saveUser(@ModelAttribute("user") User newUser) {
        newUser.setRole("USER");
        userService.saveUser(newUser);
        return "redirect:/";
    }

    @GetMapping("/profile/{screenName}")
    public String profile(@PathVariable String screenName, Model model){
        Optional<User> user = getUserSession(model, SecurityContextHolder.getContext().getAuthentication(), userService);
        if(user.isPresent()) {
            model.addAttribute("user", user.get());
        }

        Optional<User> user2 = userService.getUserByScreenName(screenName);
        if(user2.isPresent()) {
            if(user.get().getId() != user2.get().getId()) {
                model.addAttribute("user2", user2.get());
            }
        }
        return "profile";
    }

    //non rest
    @PostMapping("/follow")
    public String followUser(@RequestParam(value="follower") Long idFollower, @RequestParam(value="followed") Long idFollowed) {
        System.out.println("follower: " + idFollower + "\nfollowed: " + idFollowed);
        List<User> userList = userService.getAllUsers();
        User userFollower = new User();
        User userFollowed = new User();

        for (User userInList: userList) {
            if(userInList.getId() == idFollower) {
                userFollower = userInList;
            }
            if (userInList.getId() == idFollowed) {
                userFollowed = userInList;
            }
        }
        userService.updateUser(userFollowed.getId(), new UserRequest(userFollowed, UserRequest.REQUEST_FOLLOW, userFollower));
        return "redirect:/profile/" + userFollowed.getScreenName();
    }

    private Optional<User> getUserSession(Model model, Authentication auth, UserService userService) {
        return userService.getUserByEmail(auth.getName());
    }
}