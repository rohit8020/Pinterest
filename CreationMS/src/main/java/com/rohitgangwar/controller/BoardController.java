package com.rohitgangwar.controller;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rohitgangwar.dto.BoardDTO;
import com.rohitgangwar.entity.Board;
import com.rohitgangwar.service.BoardService;

import jakarta.validation.Valid;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/content/board")
@Validated
public class BoardController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/getboards")
    public ResponseEntity<List<BoardDTO>> getBoards(
            @RequestParam Long userId
    ){
        List<Board> boards = boardService.getBoards(userId);
        List<BoardDTO> boardDTOs=boards.stream().map(BoardDTO::valueOf).toList();
        System.out.println("Check 1");
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://CollabMS/collab/invite/getBoards");
        System.out.println("Check 2");
        HttpHeaders headers = new HttpHeaders();
        headers.set("X_userId", userId.toString()); // Pass userId as a header if required
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<List<Long>> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,entity,new ParameterizedTypeReference<List<Long>>() {});
        System.out.println("Check 3");
        List<Long> boardIds = response.getBody();
        System.out.println("Check 4");
        System.out.println(boardIds);
        List<BoardDTO> boardDTOs2=boardService.getBoardsByBoardIds(boardIds);
        List<BoardDTO> mergedBoardDTOs = Stream.concat(boardDTOs.stream(), boardDTOs2.stream())
                .distinct() // Avoid duplicates
                .toList();
        return new ResponseEntity<List<BoardDTO>>(mergedBoardDTOs,HttpStatus.OK);
    }

    @GetMapping("/fetchByBoardIds")
    public ResponseEntity<List<String>> fetchBoardsByBoardIds(@RequestParam List<Long> boardIds) {
        List<String> boardNameList = boardService.getBoardsByBoardIds(boardIds).stream().map(BoardDTO::getTitle).toList();
        return new ResponseEntity<>(boardNameList, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<BoardDTO> getBoard(@RequestParam Long boardId) {
        BoardDTO board = boardService.getBoard(boardId);
        return new ResponseEntity<BoardDTO>(board, HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<String> createBoard(@RequestBody @Valid BoardDTO boardDTO) {
        try {
            boardService.createBoard(boardDTO);
            return new ResponseEntity<String>("Board created successfuly!",HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteBoard(@RequestParam Long boardId, @RequestHeader("X_userId") Long userId)
            throws Exception {
        boardService.deleteBoard(boardId, userId);
        return new ResponseEntity<String>("Board deleted successfully!", HttpStatus.OK);
    }

    @PutMapping("/addpin")
    public ResponseEntity<String> addPin(@RequestParam Long boardId, @RequestParam Long pinId,
                                         @RequestHeader("X_userId") Long userId) throws Exception {
        boardService.addPinToBoard(boardId, pinId, userId);
        return new ResponseEntity<String>("Pin added to the board!", HttpStatus.OK);
    }

    @PutMapping("/removepin")
    public ResponseEntity<String> removePin(@RequestParam Long boardId, @RequestParam Long pinId,
                                            @RequestHeader("X_userId") Long userId) throws Exception {
        boardService.removePinFromBoard(boardId, pinId, userId);
        return new ResponseEntity<String>("Pin removed from the board!", HttpStatus.OK);
    }

}
