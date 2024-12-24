package com.rohitgangwar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rohitgangwar.entity.Follow;
import com.rohitgangwar.service.FollowService;

@RestController
@RequestMapping("/collab/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping("/request")
    public ResponseEntity<String> addFollowRequest(@RequestParam Long followee,
                                                   @RequestHeader("X_userId") Long follower) {
        try {
            followService.addFollowRequest(followee, follower);
            return new ResponseEntity<>("Follow request added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(@RequestParam Long followee,
                                               @RequestHeader("X_userId") Long follower) {
        try {
            followService.unfollowUser(followee, follower);
            return new ResponseEntity<>("Unfollowed successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to unfollow user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/requests")
    public ResponseEntity<List<Long>> followRequests(@RequestHeader("X_userId") Long userId) {
        List<Follow> followRequests = followService.getFollowRequests(userId);
        List<Long> followers=followRequests.stream().map(e->e.getFollower()).toList();
        return new ResponseEntity<List<Long>>(followers, HttpStatus.OK);
    }

    @PostMapping("/confirm-request")
    public ResponseEntity<String> confirmFollowRequest(@RequestHeader("X_userId") Long followee,@RequestParam Long follower) {
        try {
            followService.confirmFollowRequest(followee,follower);
            return new ResponseEntity<>("Follow request confirmed successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to confirm follow request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-request")
    public ResponseEntity<String> deleteFollowRequest(@RequestParam Long follower,@RequestHeader("X_userId") Long followee) {
        try {
            followService.deleteFollowRequest(followee,follower);
            return new ResponseEntity<>("Follow request deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete follow request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/followers")
    public ResponseEntity<List<Long>> getFollowers(@RequestParam Long followee){
        List<Follow> followers=followService.getFollowers(followee);
        List<Long> followerList=followers.stream().map(e->e.getFollower()).toList();
        return new ResponseEntity<List<Long>>(followerList,HttpStatus.OK);
    }

    @GetMapping("/followings")
    public ResponseEntity<List<Long>> getFollowings(@RequestParam Long followee){
        List<Follow> followings=followService.getFollowings(followee);
        List<Long> followingList=followings.stream().map(e->e.getFollowee()).toList();
        return new ResponseEntity<List<Long>>(followingList,HttpStatus.OK);
    }
}

