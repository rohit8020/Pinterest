package com.rohitgangwar.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.rohitgangwar.dto.BoardDTO;
import com.rohitgangwar.dto.BoardInvitationDTO;
import com.rohitgangwar.dto.InvitationDTO;
import com.rohitgangwar.dto.UserDTO;
import com.rohitgangwar.dto.UserResponseDTO;
import com.rohitgangwar.entity.BoardInvitation;
import com.rohitgangwar.service.BoardInvitationService;

@RestController
@RequestMapping("/collab/invite")
public class BoardInviteController {

    @Autowired
    private BoardInvitationService boardInvitationService;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/create")
    public ResponseEntity<String> createBoardInvitation(@RequestBody InvitationDTO invitationDTO,
                                                        @RequestHeader("X_userId") Long inviter) throws Exception {
        try {
            BoardDTO boardDTO = restTemplate.getForObject(
                    "http://CreationMS/content/board/get?boardId=" + invitationDTO.getBoardId(), BoardDTO.class);
            UserDTO invitee = restTemplate.getForObject("http://UserMS/auth/userbyemail?email=" + invitationDTO.getInvitee(),
                    UserDTO.class);
            if (inviter == boardDTO.getCreatedBy()) {
                boardInvitationService.createBoardInvitation(invitationDTO, invitee.getUserId(), inviter);
                return new ResponseEntity<>("Board invitation created successfully", HttpStatus.CREATED);
            } else {
                throw new Exception("You are not authorized to do this!");
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/accept")
    public ResponseEntity<String> acceptBoardInvitation(@RequestHeader("X_userId") Long invitee,
                                                        @RequestParam Long boardId) {
        try {
            boardInvitationService.acceptBoardInvitation(invitee, boardId);
            return new ResponseEntity<>("Board invitation accepted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/reject")
    public ResponseEntity<String> rejectBoardInvitation(@RequestHeader("X_userId") Long invitee,
                                                        @RequestParam Long boardId) {
        try {
            boardInvitationService.rejectBoardInvitation(invitee, boardId);
            return new ResponseEntity<>("Board invitation rejected successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/collaborator")
    public ResponseEntity<String> checkCollaborator(@RequestParam Long invitee, @RequestParam Long boardId) {
        try {
            String isCollaborator = boardInvitationService.isCollaborator(boardId, invitee);
            return new ResponseEntity<String>(isCollaborator, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Failed to check collaborator status", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/invitations")
    public ResponseEntity<List<InvitationDTO>> getAllInvitations(@RequestHeader("X_userId") Long invitee) {

        List<BoardInvitation> invitationsList = boardInvitationService.getInvitations(invitee);
        List<Long> inviterIds = invitationsList.stream().map(e -> e.getInviter()).toList();
        List<Long> boardIds = invitationsList.stream().map(e -> e.getBoardId()).toList();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://UserMS/auth/fetchByUserIds")
                .queryParam("userIds", inviterIds.toArray());
        ResponseEntity<List<UserResponseDTO>> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<UserResponseDTO>>() {
                });
        List<UserResponseDTO> users = response.getBody();


        UriComponentsBuilder builder2 = UriComponentsBuilder
                .fromUriString("http://CreationMS/content/board/fetchByBoardIds")
                .queryParam("boardIds", boardIds.toArray());
        ResponseEntity<List<String>> response2 = restTemplate.exchange(builder2.toUriString(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<String>>() {
                });
        List<String> boardNames = response2.getBody();
        System.out.println(boardNames.toString());

        List<InvitationDTO> invitations = new ArrayList<>();
        for (int i = 0; i < boardIds.size(); i++) {
            InvitationDTO invitationDTO = new InvitationDTO();
            invitationDTO.setBoardId(invitationsList.get(i).getBoardId());
            invitationDTO.setInviterId(invitationsList.get(i).getInviter());
            invitationDTO.setDescription(invitationsList.get(i).getDescription());

            if (i < boardNames.size()) {
                invitationDTO.setBoardName(boardNames.get(i));
            }

            if (i < users.size()) {
                invitationDTO.setInviter(users.get(i).getUsername());
            }

            invitations.add(invitationDTO);
        }
        return new ResponseEntity<List<InvitationDTO>>(invitations, HttpStatus.OK);
    }

}

