package com.diary.diaryproject.domain.aggregate.entity;

import com.diary.diaryproject.domain.aggregate.enumtype.EmojiEnum;
import com.diary.diaryproject.domain.dto.BoardDTO;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Table(name = "Board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long boradNo;

    @Column
    private String title;

    @Column
    private String body;

    @Column
    private String phrase;

    @Column
    private LocalDate date;

    @Column
    private EmojiEnum emojiEnum;

    public Board() {

    }

    public Board(BoardDTO boardDTO) {
        this.title = boardDTO.getTitle();
        this.body = boardDTO.getBody();
        this.emojiEnum = boardDTO.getEmojiEnum();
        this.phrase = boardDTO.getPhrase();
        this.date = boardDTO.getDate();
    }

    public Board(Long boradNo, String title, String body, String phrase, LocalDate date, EmojiEnum emojiEnum) {
        this.boradNo = boradNo;
        this.title = title;
        this.body = body;
        this.phrase = phrase;
        this.date = date;
        this.emojiEnum = emojiEnum;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
