package com.book.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainBookDto {

    private Long id;

    private String title;

    private String imageSrc;        //첵 표지
    private String author;          //작가
    private String isbn;            //isbn
    private int price;        //가격
    private String publisher;       // 출판사
    private String description;     //상세 설명

    @QueryProjection
    public MainBookDto(Long id, String title, String description, String imageSrc, String author, String isbn, String publisher, int price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageSrc = imageSrc;
        this.isbn = isbn;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
    }
}
