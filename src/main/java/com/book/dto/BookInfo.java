package com.book.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookInfo {
    private int id;
    private String title;
    private String link;
    private String imageSrc;
    private String author;
}

