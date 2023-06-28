package com.diary.diaryproject.domain.service;

import com.diary.diaryproject.domain.aggregate.entity.Board;
import com.diary.diaryproject.domain.aggregate.entity.Phrases;
import com.diary.diaryproject.domain.dto.BoardDTO;
import com.diary.diaryproject.domain.dto.PhraseDTO;
import com.diary.diaryproject.domain.dto.ResBoardDTO;
import com.diary.diaryproject.domain.repository.BoardRepository;
import com.diary.diaryproject.domain.repository.PhrasesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class PopUpService {
    private final CheckTitle checkTitle;
    private final CheckBody checkBody;
    private final BoardRepository boardRepository;
    private final PhrasesRepository phrasesRepository;
    private final ModelMapper modelMapper;

    @Autowired

    public PopUpService(CheckTitle checkTitle, CheckBody checkBody, BoardRepository boardRepository,
                        PhrasesRepository phrasesRepository, ModelMapper modelMapper) {

        this.checkTitle = checkTitle;
        this.checkBody = checkBody;
        this.boardRepository = boardRepository;
        this.phrasesRepository = phrasesRepository;
        this.modelMapper = modelMapper;
    }

    public void checkBoardLength(BoardDTO boardDTO) {
        checkTitle.checkTitleLength(boardDTO.getTitle());
        checkBody.checkBodyLength(boardDTO.getBody());
    }

    // 다이어리 저장
    @Transactional
    public void saveBoard(BoardDTO boardDTO, Integer phraseNo) {

        Optional<Phrases> phrases = phrasesRepository.findById(phraseNo);

        if(phrases.isPresent()) {
            PhraseDTO phraseDTO = modelMapper.map(phrases.get(), PhraseDTO.class);
            boardDTO.setPhrase(phraseDTO);
        }

        Board board =  modelMapper.map(boardDTO, Board.class);
        boardRepository.save(board);
    }

    // 다이어리 수정
    @Transactional
    public BoardDTO updateBoard(BoardDTO boardDTO) {
        Board board = boardRepository.findById(boardDTO.getBoradNo()).get();

        if (board != null) {
            board.setBody(boardDTO.getBody());
            board.setTitle(boardDTO.getTitle());
            board.setEmoji(boardDTO.getEmoji());
        }

        boardRepository.save(board);

        BoardDTO res = modelMapper.map(board, BoardDTO.class);

        return res;
    }

    // 다이어리 id로 조회
    @Transactional
    public ResBoardDTO findBoardById(Long boardId) {
        Board findBoard = boardRepository.findById(boardId).get();

        ResBoardDTO boardDTO = new ResBoardDTO();

        if(findBoard != null) {
            boardDTO.setBoradNo(findBoard.getBoardNo());
            boardDTO.setBody(findBoard.getBody());
            boardDTO.setTitle(findBoard.getTitle());
            boardDTO.setDate(findBoard.getDate().toString());
            boardDTO.setEmoji(findBoard.getEmoji());
            boardDTO.setPhrase(findBoard.getPhrase().getPhrase());
        }

        return boardDTO;
    }
}
