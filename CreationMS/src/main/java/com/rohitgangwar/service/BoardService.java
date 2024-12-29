package com.rohitgangwar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rohitgangwar.dto.BoardDTO;
import com.rohitgangwar.entity.Board;
import com.rohitgangwar.entity.Pin;
import com.rohitgangwar.repository.BoardRepository;
import com.rohitgangwar.repository.PinRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class BoardService {
    @Autowired
    BoardRepository boardRepository;

    @Autowired
    PinRepository pinRepository;

    public List<Board> getBoards(Long userId){
        List<Board> boards=boardRepository.findByCreatedBy(userId);
        return boards;
    }

    public BoardDTO getBoard(Long boardId) {
        List<Pin> pins=pinRepository.findByBoardId(boardId);
        Optional<Board> optional = boardRepository.findById(boardId);
        Board board = optional.get();
        BoardDTO boardDTO=BoardDTO.valueOf(board);
        boardDTO.setPins(pins);
        return boardDTO;
    }

    public void createBoard(BoardDTO boardDTO)throws Exception {
        Board board = boardDTO.createEntity();
        Optional<Board> optional=boardRepository.findByTitle(boardDTO.getTitle());
        if(optional.isPresent())throw new Exception("Board with same title is already exists!");
        boardRepository.save(board);
    }

    public void deleteBoard(Long boardId, Long userId) throws Exception {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
        if (board.getCreatedBy() == userId) {
            board.getPins().clear();
            boardRepository.delete(board);
        } else {
            throw new Exception("You are not authorized to do this!");
        }
    }

    public void removePinFromBoard(Long boardId, Long pinId, Long userId) throws Exception {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
        if (board.getCreatedBy() == userId) {
            board.getPins().removeIf(pin -> pin.getId().equals(pinId));
            boardRepository.save(board);
        } else {
            throw new Exception("You are not authorized to do this!");
        }
    }

    public void addPinToBoard(Long boardId, Long pinId, Long userId) throws Exception {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
        if (board.getCreatedBy() == userId) {
            Pin pin = pinRepository.findById(pinId).orElseThrow(() -> new EntityNotFoundException("Pin not found"));
            board.getPins().add(pin);
        } else {
            throw new Exception("You are not authorized to do this!");
        }

        boardRepository.save(board);
    }

    public List<BoardDTO> getBoardsByBoardIds(List<Long> boardIds) {
        List<Board> boards = boardRepository.findAllById(boardIds);
        return boards.stream().map(BoardDTO::valueOf).toList();
    }
}

