package com.book.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDto {

    private String title;           //제목
    private String imageSrc;        //첵 표지
    private String author;          //작가
    private String isbn;            //isbn
    private int price;        //가격
    private String publisher;       // 출판사
    private String description;     //상세 설명

}

