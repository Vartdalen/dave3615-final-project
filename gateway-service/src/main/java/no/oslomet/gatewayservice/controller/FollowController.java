package no.oslomet.gatewayservice.controller;

import no.oslomet.gatewayservice.model.Follow;
import no.oslomet.gatewayservice.model.User;
import no.oslomet.gatewayservice.service.FollowService;
import no.oslomet.gatewayservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FollowController {

    @Autowired
    FollowService followService;
    @Autowired
    UserService userService;

    @PostMapping("/follow")
    public String saveFollow(@RequestParam(value="follower") Long idFollower, @RequestParam(value="followed") Long idFollowed) {
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

        followService.saveFollow(new Follow(userFollower.getId(), userFollowed.getId()));
        return "redirect:/profile/" + userFollowed.getScreenName();
    }
}
