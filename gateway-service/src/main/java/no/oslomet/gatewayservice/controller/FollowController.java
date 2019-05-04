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
        followService.saveFollow(new Follow(idFollower, idFollowed));
        return "redirect:/profile/" + userService.getUserById(idFollowed).getScreenName();
    }

    @PostMapping("/unfollow")
    public String unfollow(@RequestParam(value="follower") Long idUnfollower, @RequestParam(value="followed") Long idUnfollowed) {
        List<Follow> followList = followService.getAllFollows();
        for(Follow follow : followList) {
            if(follow.getIdFollower() == idUnfollower && follow.getIdFollowed() == idUnfollowed) {
                followService.deleteFollowById(follow.getId());
            }
        }
        return "redirect:/profile/" + userService.getUserById(idUnfollowed).getScreenName();
    }
}
