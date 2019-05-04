package no.oslomet.followservice.service;

import no.oslomet.followservice.model.database.Follow;
import no.oslomet.followservice.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    public List<Follow> getAllFollows() {
        return followRepository.findAll();
    }

    public Follow getFollowById(long id) {
        return followRepository.findById(id).get();
    }

    public Follow saveFollow(Follow follow) {
        return followRepository.save(follow);
    }

    public void deleteFollowById(long id) {
        followRepository.deleteById(id);
    }
}

