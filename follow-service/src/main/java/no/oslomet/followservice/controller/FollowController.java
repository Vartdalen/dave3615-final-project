package no.oslomet.followservice.controller;

import no.oslomet.followservice.model.database.Follow;
import no.oslomet.followservice.model.exception.FollowExistsException;
import no.oslomet.followservice.model.exception.FollowSelfException;
import no.oslomet.followservice.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FollowController {
    @Autowired
    FollowService followService;
    @Autowired
    Environment env;

    @RequestMapping
    public String home(){
        return "Hello from Follow Service running at port: " + env.getProperty("local.server.port");
    }

    @GetMapping("/follows")
    public List<Follow> getAllFollows() { return followService.getAllFollows(); }

    @GetMapping("/follows/{id}")
    @ResponseBody
    public ResponseEntity<Follow> getFollow(@PathVariable long id) {
        return new ResponseEntity<>(followService.getFollowById(id), HttpStatus.OK);
    }

    @DeleteMapping("/follows/{id}")
    public void deleteFollow(@PathVariable long id) {
        followService.deleteFollowById(id);
    }

    @PostMapping("/follows")
    @ResponseBody
    public ResponseEntity<Follow> saveFollow(@RequestBody Follow newFollow) {
        List<Follow> followList = followService.getAllFollows();
        for (Follow follow : followList) {
            if(follow.getIdFollower() == newFollow.getIdFollower() && follow.getIdFollowed() == newFollow.getIdFollowed()) {
                throw new FollowExistsException();
            } else if (newFollow.getIdFollower() == newFollow.getIdFollowed()) {
                throw new FollowSelfException();
            }
        }
        return new ResponseEntity<>(followService.saveFollow(newFollow), HttpStatus.OK);
    }

    @PutMapping("/follows/{id}")
    public Follow updateFollow(@PathVariable long newId, @RequestBody Follow follow) {
        follow.setId(newId);
        return followService.saveFollow(follow);
    }
}

