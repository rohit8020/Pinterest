package com.rohitgangwar.repository;

import com.rohitgangwar.entity.Follow;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollowerAndStatus(Long followerId, String status);
    List<Follow> findByFolloweeAndStatus(Long followeeId, String status);
    List<Follow> findAllByFolloweeAndStatus(Long followee,String status);
    Optional<Follow> findByFolloweeAndFollowerAndStatus(Long followee, Long follower, String status);
}
