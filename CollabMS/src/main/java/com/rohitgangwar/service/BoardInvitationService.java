package com.rohitgangwar.service;

import com.rohitgangwar.dto.BoardInvitationDTO;
import com.rohitgangwar.entity.BoardInvitation;
import com.rohitgangwar.repository.BoardInvitationRepository;
import com.rohitgangwar.dto.InvitationDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardInvitationService {

    @Autowired
    private BoardInvitationRepository boardInvitationRepository;

    public void createBoardInvitation(InvitationDTO invitationDTO,Long invitee,Long inviter)throws Exception {

        Optional<BoardInvitation> optional=boardInvitationRepository.findByInviteeAndBoardId(invitee, invitationDTO.getBoardId());

        if(optional.isPresent())throw new Exception("Invitation is already present!");
        BoardInvitation boardInvitation = new BoardInvitation();
        boardInvitation.setDescription(invitationDTO.getDescription());
        boardInvitation.setInvitee(invitee);
        boardInvitation.setInviterId(inviter);
        boardInvitation.setBoardId(invitationDTO.getBoardId());
        boardInvitation.setStatus("pending");
        boardInvitation.setUpdatedAt(LocalDateTime.now());
        boardInvitationRepository.save(boardInvitation);
    }

    public void acceptBoardInvitation(Long invitee,Long boardId)throws Exception {
        BoardInvitation boardInvitation = boardInvitationRepository.findByInviteeAndBoardIdAndStatus(invitee,boardId,"pending").orElseThrow(()->new Exception("Invite already accepted!"));
        boardInvitation.setStatus("accepted");
        boardInvitationRepository.save(boardInvitation);
    }

    public void rejectBoardInvitation(Long invitee,Long boardId)throws Exception {
        BoardInvitation boardInvitation = boardInvitationRepository.findByInviteeAndBoardIdAndStatus(invitee,boardId,"pending").orElseThrow(()->new Exception("Invite already accepted or rejected!"));
        boardInvitationRepository.delete(boardInvitation);
    }

    public String isCollaborator(Long boardId, Long invitee)throws Exception {
        BoardInvitation boardInvitation = boardInvitationRepository.findByInviteeAndBoardIdAndStatus(invitee,boardId,"accepted").orElseThrow(()->new Exception("Invite already accepted or rejected!"));
        return boardInvitation.getStatus();
    }

    public List<BoardInvitation> getInvitations(Long invitee){
        return boardInvitationRepository.findByInviteeAndStatus(invitee, "pending");
    }
}

