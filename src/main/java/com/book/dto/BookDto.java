package com.book.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDto {

    private String title;           //제목
    private String link;            //네이버스토어 링크
    private String imageSrc;        //첵 표지
    private String author;          //작가
    private String isbn;            //isbn
    private String discount;        //가격
    private Integer stockNumber;        //재고
    private String publisher;       // 출판사
    private String description;     //상세 설명

}

