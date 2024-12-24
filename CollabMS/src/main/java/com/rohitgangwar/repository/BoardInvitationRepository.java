package com.rohitgangwar.repository;

import com.rohitgangwar.entity.BoardInvitation;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface BoardInvitationRepository extends JpaRepository<BoardInvitation, Long> {
    Optional<BoardInvitation> findByInviteeAndBoardId(Long invitee, Long boardId);
    Optional<BoardInvitation> findByInviteeAndBoardIdAndStatus(Long invitee, Long boardId, String status);
    List<BoardInvitation> findByInviteeAndStatus(Long invitee, String status);
}
