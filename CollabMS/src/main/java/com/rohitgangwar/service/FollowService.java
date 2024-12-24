package com.rohitgangwar.service;

import com.rohitgangwar.entity.Follow;
import com.rohitgangwar.repository.FollowRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

@Service
public class FollowService {

    @Autowired
    FollowRepository followRepository;

    public void addFollowRequest(Long followee,Long follower) {
        Follow follow = new Follow();
        follow.setFollowee(followee);
        follow.setFollower(follower);
        follow.setStatus("pending");
        follow.setUpdatedAt(LocalDateTime.now());
        followRepository.save(follow);
    }

    public void unfollowUser(Long followee, Long follower) {
        Follow follow = followRepository.findByFolloweeAndFollowerAndStatus(followee,follower,"accepted").orElseThrow(EntityNotFoundException::new);
        if (follow != null) {
            followRepository.delete(follow);
        } else {
            throw new EntityNotFoundException("Follow relationship not found");
        }
    }

    public List<Follow> getFollowRequests(Long userId) {
        return followRepository.findAllByFolloweeAndStatus(userId, "pending");
    }

    public void confirmFollowRequest(Long followee,Long follower) {
        Follow follow = followRepository.findByFolloweeAndFollowerAndStatus(followee,follower,"pending").orElseThrow(EntityNotFoundException::new);
        follow.setStatus("accepted");
        followRepository.save(follow);
    }

    public void deleteFollowRequest(Long followee,Long follower) {
        Follow follow = followRepository.findByFolloweeAndFollowerAndStatus(followee,follower,"pending").orElseThrow(EntityNotFoundException::new);
        followRepository.delete(follow);
    }

    public List<Follow> getFollowers(Long followee) {
        return followRepository.findByFolloweeAndStatus(followee, "accepted");
    }

    public List<Follow> getFollowings(Long followee) {
        return followRepository.findByFollowerAndStatus(followee, "accepted");
    }
}

